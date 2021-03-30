package com.forumensak.api.repository;

import com.forumensak.api.model.social.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
