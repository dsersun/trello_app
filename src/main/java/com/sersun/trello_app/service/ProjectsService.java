package com.sersun.trello_app.service;

import com.sersun.trello_app.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectsService {
    @Autowired
    ProjectRepository projectRepository;

    // add methods
}
