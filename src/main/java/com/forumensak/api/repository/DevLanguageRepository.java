package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.DevLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface DevLanguageRepository extends JpaRepository<DevLanguage,Long> {
    List<DevLanguage> findAllByCv(Cv cv);
}
