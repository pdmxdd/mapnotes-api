package org.launchcode.devops.mapnotesapi.models.Note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String body;
  private String title;

  public NoteEntity(String title, String body) {
    this.body = body;
    this.title = title;
  }
}
