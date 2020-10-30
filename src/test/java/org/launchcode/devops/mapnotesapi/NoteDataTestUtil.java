package org.launchcode.devops.mapnotesapi;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NoteDataTestUtil {
  @Autowired
  private NoteData noteData;

  public Note createTestNote(String title, String body) {
    Note note = new Note(title, body);
    return noteData.save(note);
  }

  public void clearData() {
    noteData.deleteAll();
  }
}
