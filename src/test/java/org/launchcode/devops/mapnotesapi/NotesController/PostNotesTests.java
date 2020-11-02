package org.launchcode.devops.mapnotesapi.NotesController;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.TestUtils.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.TestUtils.NoteDataTestUtil;
import org.launchcode.devops.mapnotesapi.controllers.NotesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class PostNotesTests {
  @Autowired
  MockMvc mockRequest;

  @Autowired
  NoteDataTestUtil noteDataTestUtil;

  @BeforeEach
  public void resetData() {
    noteDataTestUtil.clearData();
  }

  @Test
  @DisplayName("[valid data] POST /notes with body (NewNote JSON): 201 status and body (JSON Note entity)")
  public void createNoteValid() throws Exception {
    String title = "this is the title";
    String body = "this is the body";

    HashMap<String, String> newNoteData = new HashMap<String, String>();
    newNoteData.put("title", title);
    newNoteData.put("body", body);

    String newNoteJson = new ObjectMapper().writeValueAsString(newNoteData);

    mockRequest
        .perform(MockMvcRequestBuilders.post("/notes").content(newNoteJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(body))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title));
  }

  @Test
  @DisplayName("[invalid data] POST /notes (NewNote JSON): 400 status")
  public void createNoteInvalid() throws Exception {
    String emptyTitle = "";
    String body = "this is the body";

    HashMap<String, String> newNoteData = new HashMap<String, String>();
    newNoteData.put("body", body);
    newNoteData.put("title", emptyTitle);

    String newNoteJsonPayload = new ObjectMapper().writeValueAsString(newNoteData);

    mockRequest
        .perform(
            MockMvcRequestBuilders.post("/notes").content(newNoteJsonPayload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
