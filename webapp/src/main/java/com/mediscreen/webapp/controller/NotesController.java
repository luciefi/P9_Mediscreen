package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.NoteNotFoundException;
import com.mediscreen.webapp.exception.PatientNotFoundException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import com.mediscreen.webapp.service.INoteService;
import com.mediscreen.webapp.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
        try {
            NoteCreate noteCreate = new NoteCreate();
            model.addAttribute("noteCreate", noteCreate);
            addPatientToModel(patientId, model);
            return "addNote";
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/{patientId}/add")
    public String saveNewNote(@PathVariable("patientId") long patientId, @Valid NoteCreate noteCreate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Cannot add note : invalid form");
            addPatientToModel(patientId, model);
            return "addNote";
        }
        noteService.saveNewNote(noteCreate);
        logger.info("New note added");
        return "redirect:/notes/" + patientId;
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
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }


    @GetMapping("/{patientId}/{id}")
    public String getNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model) {
        try {
            addPatientToModel(patientId, model);
            NoteRead note = noteService.getNote(id);
            model.addAttribute("note", note);
            return "note";
        } catch (NoteNotFoundException e) {
            logger.info("Cannot get note details : " + e.getMessage());
            return "redirect:/notes/" + patientId;
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @GetMapping("/{patientId}/update/{id}")
    public String updateNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, Model model) {
        try {
            addPatientToModel(patientId, model);
            NoteUpdate noteUpdate = noteService.getNoteUpdate(id);
            model.addAttribute("noteUpdate", noteUpdate);
            String cancelUrl = referrer != null && referrer.contains("/notes/" + patientId + "/" + id) ? "/notes/" + patientId + "/" + id : "/notes/" + patientId;
            model.addAttribute("cancelUrl", cancelUrl);

            return "updateNote";
        } catch (NoteNotFoundException e) {
            logger.info("Cannot update note : " + e.getMessage());
            return "redirect:/notes/" + patientId;
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/{patientId}/update/{id}")
    public String updateNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, @Valid NoteUpdate noteUpdate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Cannot update note : invalid form");
            addPatientToModel(patientId, model);
            return "updateNote";
        }
        noteService.updateNote(noteUpdate);
        logger.info("Note with id: " + id + " updated");
        return "redirect:/notes/" + patientId;
    }

    @GetMapping("/{patientId}/delete/{id}")
    public String deleteNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model) {
        try {
            addPatientToModel(patientId, model);
            NoteRead note = noteService.getNote(id);
            model.addAttribute("note", note);
            return "deleteNote";
        } catch (NoteNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/notes/" + patientId;
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete note : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/{patientId}/delete/{id}")
    public String deleteNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id) {
        noteService.deleteNote(id);
        logger.info("Note with id: " + id + " deleted");
        return "redirect:/notes/" + patientId;
    }

    private void addPatientToModel(long patientId, Model model) {
        Patient patient = patientService.getPatient(patientId);
        model.addAttribute("patient", patient);
    }

}
