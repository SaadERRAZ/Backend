package com.forumensak.api.repository;

import com.forumensak.api.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CvRepository extends JpaRepository<Cv ,Long> {

}
