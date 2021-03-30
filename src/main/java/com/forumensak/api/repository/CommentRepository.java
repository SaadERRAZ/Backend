package com.forumensak.api.repository;

import com.forumensak.api.model.social.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
