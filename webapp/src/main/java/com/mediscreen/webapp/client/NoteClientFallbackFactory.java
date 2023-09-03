package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.exception.UnavailableNoteClientException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class NoteClientFallbackFactory implements FallbackFactory<NoteClient> {

    Logger logger = LoggerFactory.getLogger(NoteClientFallbackFactory.class);

    @Override
    public NoteClient create(Throwable cause) {
        logger.error("An exception occurred when calling the NoteClient", cause);

        return new NoteClient() {

            @Override
            public Page<NoteRead> findAll(long patientId, int pageNumber, int itemPerPage) {
                throwNoteException("\u26a0 \u2007 Error while retrieving note list.");
                return null;
            }

            @Override
            public NoteRead findById(String id) {
                throwNoteException("\u26a0 \u2007 Error while retrieving note with id: " + id + ".");
                return null;
            }

            @Override
            public void createNote(NoteCreate note) {
                throwNoteException("\u26a0 \u2007 Error while saving new note with content: " + note.getContent() + ".");
            }

            @Override
            public void save(NoteUpdate note) {
                throwNoteException("\u26a0 \u2007 Error while updating note with content: " + note.getContent() + " with id: " + note.getId() + ".");
            }

            @Override
            public void deleteById(String id) {
                throwNoteException("\u26a0 \u2007 Error while deleting note with id: " + id + ".");
            }

            @Override
            public void deleteByPatientId(long id) {
                throwNoteException("\u26a0 \u2007 Error while deleting notes for patient with id: " + id + ".");
            }

            private void throwNoteException(String message) {

                if (cause instanceof FeignException.BadRequest) {
                    throw new NoteClientException(message + " (Bad request)");
                }
                if (cause instanceof FeignException.NotFound) {
                    throw new NoteClientException(message + " (Not Found)");
                }
                if (cause instanceof RetryableException) {
                    throw new UnavailableNoteClientException(message + " Service is unavailable.");
                }
                throw new NoteClientException(message);
            }
        };
    }

}
