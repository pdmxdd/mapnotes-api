package org.launchcode.devops.mapnotesapi.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notes/{noteId}/features", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteFeaturesController {

}