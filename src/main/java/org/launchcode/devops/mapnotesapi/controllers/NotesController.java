package org.launchcode.devops.mapnotesapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Note.OutboundNoteRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
}