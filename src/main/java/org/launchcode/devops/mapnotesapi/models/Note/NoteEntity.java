package org.launchcode.devops.mapnotesapi.models.Note;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.launchcode.devops.mapnotesapi.models.Feature.NoteFeatureEntity;

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

  // https://www.baeldung.com/jpa-annotation-postgresql-text-type#column-annotation
  @Lob
  private String body;

  private String title;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "note")
  private Collection<NoteFeatureEntity> features;

  public NoteEntity(String title, String body) {
    this.body = body;
    this.title = title;
  }
}
