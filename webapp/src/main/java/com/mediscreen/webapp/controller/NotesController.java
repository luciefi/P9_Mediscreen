package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.service.INoteService;
import com.mediscreen.webapp.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/notes")
public class NotesController {
    Logger logger = LoggerFactory.getLogger(NotesController.class);

    private static final int NOTE_PER_PAGE = 3;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private INoteService noteService;


    @GetMapping("/{patientId}/add")
    public String createNote(@PathVariable("patientId") long patientId, Model model) {
        NoteCreate noteCreate = new NoteCreate();
        model.addAttribute("noteCreate", noteCreate);
        addPatientToModel(patientId, model);
        return "addNote";

    }

    @PostMapping("/{patientId}/add")
    public String saveNewNote(@PathVariable("patientId") long patientId, @Valid NoteCreate noteCreate, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                logger.info("Cannot add note : invalid form");
                addPatientToModel(patientId, model);
                return "addNote";
            }
            noteService.saveNewNote(noteCreate);
            logger.info("New note added");
            return "redirect:/notes/" + patientId;
        } catch (NoteClientException e) {
            logger.info("Cannot create note : " + e.getMessage());
            return "redirect:/notes/" + patientId; // TODO ajout param redirection vers note list
        }
    }

    @GetMapping("/{patientId}")
    public String getNoteList(@PathVariable("patientId") long patientId, Model model, @RequestParam(name = "page", required = false) Optional<Integer> pageNumber) {
        try {
            int currentPage = Math.max(pageNumber.orElse(1), 1);
            addPatientToModel(patientId, model);

            Page<NoteRead> notePage = noteService.getAllNotesPaginated(patientId, currentPage - 1, NOTE_PER_PAGE);

            model.addAttribute("notePage", notePage);
            int totalPages = notePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            return "noteList";
        } catch (NoteClientException e) {
            logger.info("Cannot get note list : " + e.getMessage());
            return "redirect:/notes/" + patientId; // TODO ajout param redirection vers note list
        }
    }

    @GetMapping("/{patientId}/details/{id}")
    public String getNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("note", note);
            return "note";
        } catch (NoteClientException e) {
            logger.info("Cannot get note : " + e.getMessage());
            return "redirect:/notes/" + patientId; // TODO ajout param
        }
    }

    @GetMapping("/{patientId}/update/{id}")
    public String updateNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("noteRead", note);
            String cancelUrl = "/notes/" + note.getPatientId() + "/details/" + id;
            model.addAttribute("cancelUrl", cancelUrl);
            return "updateNote";
        } catch (NoteClientException e) {
            logger.info("Cannot update note : " + e.getMessage());
            return "redirect:/notes/" + patientId; // TODO ajout param
        }
    }

    @PostMapping("/{patientId}/update/{id}")
    public String updateNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, @Valid NoteRead noteRead, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {

                logger.info("Cannot update note : invalid form");
                addPatientToModel(noteRead.getPatientId(), model);
                return "updateNote";
            }
            noteService.updateNote(noteRead);
            logger.info("Note with id: " + id + " updated");
            return "redirect:/notes/" + patientId;
        } catch (NoteClientException e) {
            logger.info("Cannot update note : " + e.getMessage());
            return "redirect:/notes/" + patientId; // TODO ajout param
        }
    }

    @GetMapping("/{patientId}/delete/{id}")
    public String deleteNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("note", note);
            return "deleteNote";
        } catch (NoteClientException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/notes/" + patientId;
        }
    }

    @PostMapping("/{patientId}/delete/{id}")
    public String deleteNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id) {
        try {
            noteService.deleteNote(id);
            logger.info("Note with id: " + id + " deleted");
            return "redirect:/notes/" + patientId;
        } catch (NoteClientException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/notes/" + patientId;
        }
    }

    private void addPatientToModel(long patientId, Model model) {
        Patient patient = patientService.getPatient(patientId);
        model.addAttribute("patient", patient);
    }


    @ExceptionHandler({PatientClientException.class})
    public String handlePatientClientException(Exception e) {
        logger.error("Patient client exception: {}", e.getMessage());
        return "redirect:/patient/list"; // TODO add redirect param
    }

}
