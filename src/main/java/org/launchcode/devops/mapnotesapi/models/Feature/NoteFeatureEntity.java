package org.launchcode.devops.mapnotesapi.models.Feature;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Geometry;

import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "note_features")
public class NoteFeatureEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private Geometry geometry;

  @ManyToOne
  @JoinColumn(name = "note_id", nullable = false)
  private NoteEntity note;
}
