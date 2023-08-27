package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.exception.UnavailableNoteClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.service.INoteService;
import com.mediscreen.webapp.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveNewNote(@PathVariable("patientId") long patientId, @Valid NoteCreate noteCreate, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
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
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @GetMapping("/{patientId}")
    public String getNoteList(@PathVariable("patientId") long patientId, Model model, @RequestParam(name = "page", required = false) Optional<Integer> pageNumber, RedirectAttributes redirectAttributes) {
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
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @GetMapping("/{patientId}/unavailable")
    public String getNoteListUnavailable(@PathVariable("patientId") long patientId, Model model) {
        addPatientToModel(patientId, model);
        return "noteListUnavailable";
    }

    @GetMapping("/{patientId}/details/{id}")
    public String getNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("note", note);
            return "note";
        } catch (NoteClientException e) {
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @GetMapping("/{patientId}/update/{id}")
    public String updateNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("noteRead", note);
            String cancelUrl = "/notes/" + note.getPatientId() + "/details/" + id;
            model.addAttribute("cancelUrl", cancelUrl);
            return "updateNote";
        } catch (NoteClientException e) {
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @PostMapping("/{patientId}/update/{id}")
    public String updateNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, @Valid NoteRead noteRead, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
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
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @GetMapping("/{patientId}/delete/{id}")
    public String deleteNoteForm(@PathVariable("patientId") long patientId, @PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            NoteRead note = noteService.getNote(id);
            addPatientToModel(note.getPatientId(), model);
            model.addAttribute("note", note);
            return "deleteNote";
        } catch (NoteClientException e) {
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    @PostMapping("/{patientId}/delete/{id}")
    public String deleteNote(@PathVariable("patientId") long patientId, @PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(id);
            logger.info("Note with id: " + id + " deleted");
            return "redirect:/notes/" + patientId;
        } catch (NoteClientException e) {
            return handleNoteClientException(e, patientId, redirectAttributes);
        } catch (UnavailableNoteClientException e) {
            return handleUnavailableNoteClientException(e, patientId);
        }
    }

    private void addPatientToModel(long patientId, Model model) {
        Patient patient = patientService.getPatient(patientId);
        model.addAttribute("patient", patient);
    }

    public String handleNoteClientException(Exception e, long patientId, RedirectAttributes redirectAttributes) {
        logger.error("Note client exception: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/notes/" + patientId;
    }

    public String handleUnavailableNoteClientException(Exception e, long patientId) {
        logger.error(e.getMessage());
        return "redirect:/notes/" + patientId + "/unavailable";
    }
}
