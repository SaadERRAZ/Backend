package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import com.forumensak.api.model.cv.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findAllByCv(Cv cv);
}
