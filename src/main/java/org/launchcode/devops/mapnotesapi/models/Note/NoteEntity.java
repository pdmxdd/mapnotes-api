package org.launchcode.devops.mapnotesapi.models.Note;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notes")
public class NoteEntity {
  private long id;
  private String body;
  private String title;

  public NoteEntity(String title, String body) {
    this.body = body;
    this.title = title;
  }
}
