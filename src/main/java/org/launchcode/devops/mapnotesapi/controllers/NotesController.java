package org.launchcode.devops.mapnotesapi.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Note.InboundNoteRepresentation;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.launchcode.devops.mapnotesapi.models.Note.OutboundNoteRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotesController {
  @Autowired
  private NoteData noteData;

  @GetMapping
  public List<OutboundNoteRepresentation> getNotes() {
    return noteData.findAll().stream().map(OutboundNoteRepresentation::fromNoteEntity).collect(Collectors.toList());
  }

  @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Object> createNote(@RequestBody @Valid InboundNoteRepresentation newNoteData,
      Errors validationErrors) {
    if (validationErrors.hasErrors()) {
      // can be abstracted using ControllerAdvice
      // see https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
      return ResponseEntity.badRequest().body(new ValidationErrorsDto(validationErrors));
    }

    NoteEntity newNote = newNoteData.toNoteEntity();
    noteData.save(newNote);

    return ResponseEntity.status(201).body(OutboundNoteRepresentation.fromNoteEntity(newNote));
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<OutboundNoteRepresentation> getNote(@PathVariable long noteId) {
    Optional<NoteEntity> maybeNote = noteData.findById(noteId);
    if (maybeNote.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    NoteEntity foundNote = maybeNote.get();

    return ResponseEntity.ok(OutboundNoteRepresentation.fromNoteEntity(foundNote));
  }
}