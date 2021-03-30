package com.forumensak.api.repository;

import com.forumensak.api.model.social.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;


@RestController
public interface PostRepository extends JpaRepository<Post, Long> {
}
