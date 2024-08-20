package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.active = false WHERE u.lastLogin < :cutoffDate")
    int deactivateUsersInactiveSince(LocalDateTime cutoffDate);
}
