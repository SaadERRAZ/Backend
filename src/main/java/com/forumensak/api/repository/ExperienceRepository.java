package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByCv(Cv cv);
}
