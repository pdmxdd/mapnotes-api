package org.launchcode.devops.mapnotesapi.NoteFeaturesController;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.TestUtils.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Feature.NoteFeatureEntity;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class GetNoteFeaturesTests {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    NoteData noteData;

    @Test
    @DisplayName("[empty state] GET /notes/{noteId}/features: 404 status")
    public void getNonNoteFeatures() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/-1/features"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("[empty state] GET /notes/{noteId}/features: empty Feature Collection")
    public void getNoteFeaturesEmpty() throws Exception {
        NoteEntity testNoteEntity = new NoteEntity("test title", "test body");
        NoteEntity updatedNoteEntity = noteData.save(testNoteEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + updatedNoteEntity.getId() + "/features"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("FeatureCollection"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.features.length()").value(0));
    }

    @Test
    @DisplayName("[FeatureCollection] GET /notes/{noteId}/features: Feature Collection with 1 feature")
    public void getNoteFeaturesPopulated() throws Exception {
        //{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[-9438256.383064654,2648494.7591272676],[-9040547.558033329,2728036.524133533],[-8265015.34922224,2409869.4641084713],[-8205359.025467541,2220957.772218591],[-8642838.733002001,2151358.727838109],[-8961005.793027062,2320384.978476423],[-9398485.500561522,2439697.625985821],[-9438256.383064654,2648494.7591272676]]]},"properties":null}]}
        NoteEntity testNoteEntity = new NoteEntity("test title", "test body");
        NoteEntity updatedNoteEntity = noteData.save(testNoteEntity);
        List<NoteFeatureEntity> features = new ArrayList<>();
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
        features.add(new NoteFeatureEntity(geometryFactory.createPolygon(coordinatesArray)));
        for(NoteFeatureEntity noteFeature : features) {
            noteFeature.setNote(updatedNoteEntity);
        }
        updatedNoteEntity.setFeatures(features);
        noteData.save(updatedNoteEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/" + updatedNoteEntity.getId() + "/features"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("FeatureCollection"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.features.length()").value(1));

    }
}
