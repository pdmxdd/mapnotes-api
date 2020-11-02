package org.launchcode.devops.mapnotesapi.models.Note;

import lombok.Data;

@Data
public class OutboundNoteRepresentation {
  private long id;
  private String body;
  private String title;

  private OutboundNoteRepresentation(long id, String title, String body) {
    this.id = id;
    this.body = body;
    this.title = title;
  }

  public static OutboundNoteRepresentation fromNoteEntity(NoteEntity noteEntity) {
    return new OutboundNoteRepresentation(noteEntity.getId(), noteEntity.getTitle(), noteEntity.getBody());
  }
}
