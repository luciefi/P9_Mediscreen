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
                throwNoteException("Exception while retrieving note list from note service.");
                return null;
            }

            @Override
            public NoteRead findById(String id) {
                throwNoteException("Exception while retrieving note with id: " + id + " from note service.");
                return null;
            }

            @Override
            public void createNote(NoteCreate note) {
                throwNoteException("Exception while saving new note with content: " + note.getContent() + " in note service.");
            }

            @Override
            public void save(NoteUpdate note) {
                throwNoteException("Exception while updating note with content: " + note.getContent() + " with id: " + note.getId() + " in note service.");
            }

            @Override
            public void deleteById(String id) {
                throwNoteException("Exception while deleting note with id: " + id + " from note service.");
            }

            @Override
            public void deleteByPatientId(long id) {
                throwNoteException("Exception while deleting notes for patient with id: " + id + " from note service.");
            }

            private void throwNoteException(String message) {

                if (cause instanceof FeignException.BadRequest) {
                    throw new NoteClientException("Bad request: " + message);
                }
                if (cause instanceof FeignException.NotFound) {
                    throw new NoteClientException("Not Found: " + message);
                }
                if (cause instanceof RetryableException) {
                    throw new UnavailableNoteClientException("Service unavailable: " + message);
                }
                throw new NoteClientException(message);
            }
        };
    }

}
