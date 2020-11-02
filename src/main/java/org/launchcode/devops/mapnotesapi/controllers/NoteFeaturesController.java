package org.launchcode.devops.mapnotesapi.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.launchcode.devops.mapnotesapi.data.NoteData;
import org.launchcode.devops.mapnotesapi.models.Feature.Feature;
import org.launchcode.devops.mapnotesapi.models.Feature.FeatureCollection;
import org.launchcode.devops.mapnotesapi.models.Feature.NoteFeatureEntity;
import org.launchcode.devops.mapnotesapi.models.Note.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notes/{noteId}/features", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteFeaturesController {

    @Autowired
    NoteData noteData;

    @GetMapping
    public ResponseEntity getNoteFeatures(@PathVariable Long noteId) {
        Optional<NoteEntity> optionalNoteEntity = noteData.findById(noteId);
        if(optionalNoteEntity.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        NoteEntity noteEntity = optionalNoteEntity.get();
        return ResponseEntity.status(200).body(FeatureCollection.fromNote(noteEntity));
    }

    @PutMapping
    public ResponseEntity putNoteFeatures(@PathVariable Long noteId, @RequestBody FeatureCollection featureCollection) {
        Optional<NoteEntity> optionalNoteEntity = noteData.findById(noteId);
        if(optionalNoteEntity.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        NoteEntity noteEntity = optionalNoteEntity.get();
        List<NoteFeatureEntity> noteFeatureEntitiesList = new ArrayList<>();
        for(Feature feature : featureCollection.getFeatures()) {
            NoteFeatureEntity noteFeatureEntity = feature.toNoteFeatureEntity();
            System.out.println(noteFeatureEntity);
            noteFeatureEntity.setNote(noteEntity);
            noteFeatureEntitiesList.add(noteFeatureEntity);
        }
        noteEntity.setFeatures(noteFeatureEntitiesList);
        noteData.save(noteEntity);

        return ResponseEntity.status(200).build();
    }

}