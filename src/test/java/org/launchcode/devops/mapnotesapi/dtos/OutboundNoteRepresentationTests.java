package org.launchcode.devops.mapnotesapi.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.launchcode.devops.mapnotesapi.models.Note.OutboundNoteRepresentation;

public class OutboundNoteRepresentationTests {
  @Test
  @DisplayName("[static] fromNoteEntity(): converts a NoteEntity representation to its outbound representation")
  public void fromNoteEntity() {
    NoteEntity noteEntity = new NoteEntity("the title", "the body");
    noteEntity.setId(1);

    OutboundNoteRepresentation outboundNote = OutboundNoteRepresentation.fromNoteEntity(noteEntity);

    assertEquals(outboundNote.getId(), noteEntity.getId());
    assertEquals(outboundNote.getBody(), noteEntity.getBody());
    assertEquals(outboundNote.getTitle(), noteEntity.getTitle());
  }
}
