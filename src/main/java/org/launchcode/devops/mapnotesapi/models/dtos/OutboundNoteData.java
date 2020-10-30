package org.launchcode.devops.mapnotesapi.models.dtos;

import org.launchcode.devops.mapnotesapi.models.Note;

import lombok.Data;

@Data
public class OutboundNoteData {
  private long id;
  private String body;
  private String title;

  public OutboundNoteData(Note noteEntity) {
    this.id = noteEntity.getId();
    this.body = noteEntity.getBody();
    this.title = noteEntity.getTitle();
  }
}
