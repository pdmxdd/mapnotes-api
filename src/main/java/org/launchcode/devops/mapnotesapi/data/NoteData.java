package org.launchcode.devops.mapnotesapi.data;

import org.launchcode.devops.mapnotesapi.models.Note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteData extends JpaRepository<Note, Long> {

}
