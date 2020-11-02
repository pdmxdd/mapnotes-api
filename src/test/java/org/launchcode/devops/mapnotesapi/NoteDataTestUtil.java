package org.launchcode.devops.mapnotesapi;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NoteDataTestUtil {
  @Autowired
  private NoteData noteData;

  public NoteEntity createTestNote(String title, String body) {
    NoteEntity note = new NoteEntity(title, body);
    return noteData.save(note);
  }

  public void clearData() {
    noteData.deleteAll();
  }
}
