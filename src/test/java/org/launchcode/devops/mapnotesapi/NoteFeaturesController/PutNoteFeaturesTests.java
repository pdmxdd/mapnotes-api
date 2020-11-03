package org.launchcode.devops.mapnotesapi.NoteFeaturesController;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.TestUtils.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Feature.Feature;
import org.launchcode.devops.mapnotesapi.models.Feature.FeatureCollection;
import org.launchcode.devops.mapnotesapi.models.Feature.NoteFeatureEntity;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class PutNoteFeaturesTests {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    NoteData noteData;

    @Test
    @DisplayName("PUT /notes/{noteId}/features: 404")
    public void putNonNoteFeatures() throws Exception {
        List<NoteFeatureEntity> featureEntities = new ArrayList<>();
        Coordinate[] coordinatesArray = {
            new Coordinate(-9438256.383064654,2648494.7591272676),
            new Coordinate(-9040547.558033329,2728036.524133533),
            new Coordinate(-8265015.34922224,2409869.4641084713),
            new Coordinate(-8205359.025467541,2220957.772218591),
            new Coordinate(-8642838.733002001,2151358.727838109),
            new Coordinate(-8961005.793027062,2320384.978476423),
            new Coordinate(-9398485.500561522,2439697.625985821),
            new Coordinate(-9438256.383064654,2648494.7591272676)
        };
        GeometryFactory geometryFactory = new GeometryFactory();
        featureEntities.add(new NoteFeatureEntity(geometryFactory.createPolygon(coordinatesArray)));
        List<Feature> features = new ArrayList<>();
        for(NoteFeatureEntity featureEntity : featureEntities) {
            features.add(Feature.fromNoteFeatureEntity(featureEntity));
        }
        FeatureCollection featureCollection = new FeatureCollection(features);
        String featureCollectionJson = new ObjectMapper().writeValueAsString(featureCollection);
        mockMvc.perform(MockMvcRequestBuilders.put("/notes/-1/features").contentType(MediaType.APPLICATION_JSON).content(featureCollectionJson))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("PUT /notes/{noteId}/features: 200")
    public void putNoteFeatures() throws Exception {
        List<NoteFeatureEntity> featureEntities = new ArrayList<>();
        Coordinate[] coordinatesArray = {
            new Coordinate(-9438256.383064654,2648494.7591272676),
            new Coordinate(-9040547.558033329,2728036.524133533),
            new Coordinate(-8265015.34922224,2409869.4641084713),
            new Coordinate(-8205359.025467541,2220957.772218591),
            new Coordinate(-8642838.733002001,2151358.727838109),
            new Coordinate(-8961005.793027062,2320384.978476423),
            new Coordinate(-9398485.500561522,2439697.625985821),
            new Coordinate(-9438256.383064654,2648494.7591272676)
        };
        GeometryFactory geometryFactory = new GeometryFactory();
        featureEntities.add(new NoteFeatureEntity(geometryFactory.createPolygon(coordinatesArray)));
        List<Feature> features = new ArrayList<>();
        for(NoteFeatureEntity featureEntity : featureEntities) {
            features.add(Feature.fromNoteFeatureEntity(featureEntity));
        }
        FeatureCollection featureCollection = new FeatureCollection(features);
        String featureCollectionJson = new ObjectMapper().writeValueAsString(featureCollection);
        NoteEntity testNote = noteData.save(new NoteEntity("test note title", "test note body"));
        mockMvc.perform(MockMvcRequestBuilders.put("/notes/" + testNote.getId() + "/features").contentType(MediaType.APPLICATION_JSON).content(featureCollectionJson))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
