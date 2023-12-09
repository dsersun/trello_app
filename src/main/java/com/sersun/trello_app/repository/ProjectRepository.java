package com.sersun.trello_app.repository;

import com.sersun.trello_app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {




    List<Project> findAllByProjectNameContainingIgnoreCaseOrProjectDescriptionContainingIgnoreCase(String name, String description);

    List<Project> findAllByStartDateBetween(Date startDate, Date endDate);

    List<Project> findAllByEndDateBetween(Date startDate, Date endDate);
}