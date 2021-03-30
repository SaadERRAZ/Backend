package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findAllByCv(Cv cv);
}
