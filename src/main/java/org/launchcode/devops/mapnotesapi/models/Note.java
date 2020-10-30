package org.launchcode.devops.mapnotesapi.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.launchcode.devops.mapnotesapi.models.dtos.InboundNoteData;
import org.launchcode.devops.mapnotesapi.models.dtos.OutboundNoteData;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notes")
public class Note {
  private long id;
  private String body;
  private String title;

  public Note(String title, String body) {
    this.body = body;
    this.title = title;
  }

  public OutboundNoteData toOutboundDto() {
    return new OutboundNoteData(this);
  }

  public static Note fromInboundDto(InboundNoteData noteData) {
    return new Note(noteData.getTitle(), noteData.getBody());
  }
}
