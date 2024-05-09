package com.example.reporting.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = ApiController.URI_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public interface ApiController {

    String URI_PATH = "/api/report-service";
}
