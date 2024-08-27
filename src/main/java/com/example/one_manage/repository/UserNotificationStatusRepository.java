package com.example.one_manage.repository;

import com.example.one_manage.entity.Notification;
import com.example.one_manage.entity.User;
import com.example.one_manage.entity.UserNotificationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNotificationStatusRepository extends JpaRepository<UserNotificationStatus, Long> {
    Optional<UserNotificationStatus> findByUserAndNotificationAndIsDismissed(User user, Notification notification,
            boolean isDismissed);
}
