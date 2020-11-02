package org.launchcode.devops.mapnotesapi.NoteFeaturesController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.TestUtils.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.data.NoteData;
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

    }
}
