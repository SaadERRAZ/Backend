package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface SoftwareRepository extends JpaRepository<Software,Long> {
    List<Software> findAllByCv(Cv cv);
}
