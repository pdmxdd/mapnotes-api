package org.launchcode.devops.mapnotesapi.controllers.Notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.NoteDataTestUtil;
import org.launchcode.devops.mapnotesapi.controllers.NotesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class GetNotesTests {
  @Autowired
  MockMvc mockRequest;

  @Autowired
  NoteDataTestUtil noteDataTestUtil;

  @BeforeEach
  public void resetData() {
    noteDataTestUtil.clearData();
  }

  @Test
  @DisplayName("[empty state] GET /notes: an empty JSON list")
  public void getNotesEmpty() throws Exception {
    mockRequest.perform(MockMvcRequestBuilders.get(NotesController.getRootPath()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("[populated state] GET /notes: a JSON list of Note entities")
  public void getNotesPopulated() throws Exception {
    noteDataTestUtil.createTestNote("some title", "some body");
    noteDataTestUtil.createTestNote("some other title", "some other body");

    mockRequest.perform(MockMvcRequestBuilders.get(NotesController.getRootPath()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].body").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].title").isString());
  }
}
