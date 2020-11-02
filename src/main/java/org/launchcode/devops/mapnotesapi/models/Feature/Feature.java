package org.launchcode.devops.mapnotesapi.models.Feature;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import lombok.Data;

@Data
public class Feature {
  private long id;
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(using = GeometryDeserializer.class)
  private Geometry geometry;
  private final String type = "Feature";

  private Feature() {
  }

  private Feature(long id, Geometry geometry) {
    this.id = id;
    this.geometry = geometry;
  }

  public Feature(Geometry geometry) {
    this.geometry = geometry;
  }

  public static Feature fromNoteFeatureEntity(NoteFeatureEntity noteFeatureEntity) {
    return new Feature(noteFeatureEntity.getId(), noteFeatureEntity.getGeometry());
  }

  public NoteFeatureEntity toNoteFeatureEntity() {
    return new NoteFeatureEntity(this.geometry);
  }
}
