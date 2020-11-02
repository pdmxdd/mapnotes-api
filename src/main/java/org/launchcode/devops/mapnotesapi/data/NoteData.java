package org.launchcode.devops.mapnotesapi.data;

import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteData extends JpaRepository<NoteEntity, Long> {

}
