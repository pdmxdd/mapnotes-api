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
public class DeleteNoteTests {
  @Autowired
  private NoteDataTestUtil noteDataTestUtil;

  @Autowired
  private MockMvc mockRequest;

  @BeforeEach
  public void resetData() {
    noteDataTestUtil.clearData();
  }

  @Test
  @DisplayName("[empty state] DELETE /notes/{noteId}: 404 status")
  public void deleteNoteEmpty() throws Exception {
    String noteEntityPath = "/notes/1";

    mockRequest.perform(MockMvcRequestBuilders.delete(noteEntityPath))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("[populated state] DELETE /notes/{noteId}: 204 status")
  public void deleteNotePopulated() throws Exception {
    NoteEntity existingNote = noteDataTestUtil.createTestNote("the title", "the body");
    String noteEntityPath = "/notes/" + existingNote.getId();

    mockRequest.perform(MockMvcRequestBuilders.delete(noteEntityPath))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
