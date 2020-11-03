package org.launchcode.devops.mapnotesapi.NotesController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.devops.mapnotesapi.TestUtils.IntegrationTestConfig;
import org.launchcode.devops.mapnotesapi.TestUtils.NoteDataTestUtil;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class GetNoteTests {
  @Autowired
  private NoteDataTestUtil noteDataTestUtil;

  @Autowired
  private MockMvc mockRequest;

  @BeforeEach
  public void resetData() {
    noteDataTestUtil.clearData();
  }

  @Test
  @DisplayName("[empty state] GET /notes/{noteId}: 404 status")
  public void getNoteEmpty() throws Exception {
    String noteEntityPath = "/notes/1";
    mockRequest.perform(MockMvcRequestBuilders.get(noteEntityPath))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("[populated state] GET /notes/{noteId}: the corresponding Note entity")
  public void getNotePopulated() throws Exception {
    NoteEntity existingNote = noteDataTestUtil.createTestNote("the title", "the body");
    String noteEntityPath = "/notes/" + existingNote.getId();

    mockRequest.perform(MockMvcRequestBuilders.get(noteEntityPath)).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingNote.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(existingNote.getBody()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(existingNote.getTitle()));
  }
}
