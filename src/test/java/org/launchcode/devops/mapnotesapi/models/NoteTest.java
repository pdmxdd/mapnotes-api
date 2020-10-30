package org.launchcode.devops.mapnotesapi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.models.Note.InboundNoteData;
import org.launchcode.devops.mapnotesapi.models.Note.Note;
import org.launchcode.devops.mapnotesapi.models.Note.OutboundNoteData;

public class NoteTest {
  @Test
  @DisplayName("[instance] toOutBoundDto(): converts a Note entity to its outbound DTO object representation")
  public void toOutboundDto() {
    Note noteEntity = new Note("the title", "the body");
    OutboundNoteData outboundNoteData = noteEntity.toOutboundDto();

    assertEquals(noteEntity.getId(), outboundNoteData.getId());
    assertEquals(noteEntity.getBody(), outboundNoteData.getBody());
    assertEquals(noteEntity.getTitle(), outboundNoteData.getTitle());
  }

  @Test
  @DisplayName("[static] fromInboundDto(): creates a Note entity object from data in its inbound DTO")
  public void fromInboundDto() {
    InboundNoteData newNoteData = new InboundNoteData();
    newNoteData.setTitle("the title");
    newNoteData.setBody("the body");

    Note noteEntity = Note.fromInboundDto(newNoteData);

    assertEquals(newNoteData.getBody(), noteEntity.getBody());
    assertEquals(newNoteData.getTitle(), noteEntity.getTitle());
  }
}
