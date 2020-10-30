package org.launchcode.devops.mapnotesapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Note;
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
