package com.mediscreen.notes.controller;

import com.mediscreen.notes.exception.NotFoundException;
import com.mediscreen.notes.mapper.NoteMapper;
import com.mediscreen.notes.model.NoteCreate;
import com.mediscreen.notes.model.NoteRead;
import com.mediscreen.notes.model.NoteUpdate;
import com.mediscreen.notes.service.INoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@Validated
public class NoteController {

    @Autowired
    private INoteService service;

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    @GetMapping("/{id}")
    public NoteRead getNoteById(@PathVariable("id") final String id) {
        return NoteMapper.convertToNoteRead(service.getNote(id));
    }

    @GetMapping
    public Page<NoteRead> getPaginatedNotes(
            @RequestParam(value = "patientId", required = true) @Valid @Min(value = 1, message = "Patient Id must be valid") long patientId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) @Min(value = 0, message = "Page index must not be less than zero") Integer pageNumber,
            @RequestParam(value = "itemPerPage", defaultValue = "10", required = false) @Min(value = 1, message = "Page size must not be less than one") Integer itemPerPage) {
        return service.getAllNotesPaginated(patientId, pageNumber, itemPerPage).map(NoteMapper::convertToNoteRead);
    }

    @GetMapping("/list/{id}")
    public List<NoteRead> getNotesList(
            @PathVariable("id") @Valid @Min(value = 1, message = "Patient Id must be valid") long patientId) {
        return service.getAllNotesList(patientId).stream().map(NoteMapper::convertToNoteRead).collect(Collectors.toList());
    }

    @PostMapping
    public void addNote(@Valid @RequestBody NoteCreate note) {
        service.saveNewNote(NoteMapper.convertToNoteEntity(note));
        logger.info("New note added");
    }

    @PutMapping
    public void updateMedicalRecord(@Valid @RequestBody NoteUpdate note) {
        service.updateNote(NoteMapper.convertToNoteEntity(note));
        logger.info("Note with id: " + note.getId() + " updated");
    }

    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable("id") final String id) {
        service.deleteNote(id);
        logger.info("Note with id: " + id + " deleted");
    }

    @DeleteMapping("/patient/{patientId}")
    public void deleteMedicalRecordsForPatient(@PathVariable("patientId") final long patientId) {
        service.deleteNotesForPatient(patientId);
        logger.info("Notes for patient with id: " + patientId + " deleted");
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        logger.error("Not found exception: {}", e.getMessage());
        return new ResponseEntity<>("Not found exception: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleConstraintException(Exception e) {
        logger.error("Invalid parameter: {}", e.getMessage());
        return new ResponseEntity<>("Invalid parameter: {}" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
