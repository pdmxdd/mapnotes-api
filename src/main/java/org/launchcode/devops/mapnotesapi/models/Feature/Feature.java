package org.launchcode.devops.mapnotesapi.models.Feature;

import com.vividsolutions.jts.geom.Geometry;

import lombok.Data;

@Data
public class Feature {
  private long id;
  private Geometry geometry;
  private final String type = "Feature";

  private Feature() {
  }

  private Feature(long id, Geometry geometry) {
    this.id = id;
    this.geometry = geometry;
  }

  public static Feature fromNoteFeatureEntity(NoteFeatureEntity noteFeatureEntity) {
    return new Feature(noteFeatureEntity.getId(), noteFeatureEntity.getGeometry());
  }
}
