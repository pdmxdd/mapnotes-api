package org.launchcode.devops.mapnotesapi.controllers;

public class NoteController {
  public static String getRootPath(long noteId) {
    return String.format("%s/%", NotesController.getRootPath(), noteId);
  }
}