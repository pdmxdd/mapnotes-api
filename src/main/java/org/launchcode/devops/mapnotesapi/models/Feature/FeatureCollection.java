package org.launchcode.devops.mapnotesapi.models.Feature;

import java.util.Collection;
import java.util.stream.Collectors;

import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;

import lombok.Data;

@Data
public class FeatureCollection {
  private final String type = "FeatureCollection";
  private Collection<Feature> features;

  private FeatureCollection() {
  }

  private FeatureCollection(Collection<Feature> features) {
    this.features = features;
  }

  public static FeatureCollection fromNote(NoteEntity note) {
    Collection<Feature> features = note.getFeatures().stream().map(Feature::fromNoteFeatureEntity)
        .collect(Collectors.toList());

    return new FeatureCollection(features);
  }
}
