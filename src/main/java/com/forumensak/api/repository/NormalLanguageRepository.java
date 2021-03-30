package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.NormalLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface NormalLanguageRepository extends JpaRepository<NormalLanguage,Long> {
    List<NormalLanguage> findAllByCv(Cv cv);
}
