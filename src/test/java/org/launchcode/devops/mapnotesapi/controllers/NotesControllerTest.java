package org.launchcode.devops.mapnotesapi.controllers;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.NoteDataTestUtil;
import org.launchcode.devops.mapnotesapi.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class NotesControllerTest {
  @Autowired
  MockMvc mockRequest;

  @Autowired
  NoteDataTestUtil noteDataTestUtil;

  @BeforeAll
  @AfterEach
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
    Note testNote = noteDataTestUtil.createTestNote("some title", "some body");

    mockRequest.perform(MockMvcRequestBuilders.get(NotesController.getRootPath()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testNote.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].body").value(testNote.getBody()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(testNote.getTitle()));
  }

  @Test
  @DisplayName("POST /notes (valid NewNote JSON payload): a JSON Note entity, 201 status and Location header")
  public void createNoteValid() throws Exception {
    String title = "this is the title";
    String body = "this is the body";

    HashMap<String, String> newNoteData = new HashMap<String, String>();
    newNoteData.put("title", title);
    newNoteData.put("body", body);

    String newNoteJsonPayload = new ObjectMapper().writeValueAsString(newNoteData);

    mockRequest
        .perform(MockMvcRequestBuilders.post(NotesController.getRootPath()).content(newNoteJsonPayload)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().exists("Location"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(body))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title));
  }

  @Test
  @DisplayName("POST /notes (invalid NewNote JSON payload): 400 status")
  public void createNoteInvalid() throws Exception {
    String emptyTitle = "";
    String body = "this is the body";

    HashMap<String, String> newNoteData = new HashMap<String, String>();
    newNoteData.put("title", emptyTitle);
    newNoteData.put("body", body);

    String newNoteJsonPayload = new ObjectMapper().writeValueAsString(newNoteData);

    mockRequest.perform(MockMvcRequestBuilders.post(NotesController.getRootPath()).content(newNoteJsonPayload)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
