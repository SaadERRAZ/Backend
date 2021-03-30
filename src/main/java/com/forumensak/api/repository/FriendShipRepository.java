package com.forumensak.api.repository;

import com.forumensak.api.model.social.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipRepository extends JpaRepository<Friendship,Long> {
    Friendship findByIdSenderIdAndIdReceiverId(Long id,Long id1);
    List<Friendship> findByIdSenderIdOrIdReceiverId(Long id,Long id2);
}
