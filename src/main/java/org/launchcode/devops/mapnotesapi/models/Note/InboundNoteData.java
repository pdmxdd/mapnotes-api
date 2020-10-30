package org.launchcode.devops.mapnotesapi.models.Note;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class InboundNoteData {
  @Size(min = 1, message = "The body must not be empty")
  private String body;

  @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters")
  private String title;
}
