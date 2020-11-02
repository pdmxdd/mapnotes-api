package org.launchcode.devops.mapnotesapi.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.models.Note.InboundNoteRepresentation;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;

public class InboundNoteRepresentationTests {
  @Test
  @DisplayName("[instance] toNote(): converts the inbound representation to a NoteEntity representation")
  public void toNoteEntity() {
    InboundNoteRepresentation newNoteData = new InboundNoteRepresentation();
    newNoteData.setBody("the body");
    newNoteData.setTitle("the title");

    NoteEntity newNoteEntity = newNoteData.toNoteEntity();

    assertEquals(newNoteData.getBody(), newNoteEntity.getBody());
    assertEquals(newNoteData.getTitle(), newNoteEntity.getTitle());
  }
}
