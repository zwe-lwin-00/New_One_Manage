package com.example.one_manage.service;

import com.example.one_manage.entity.Notification;
import com.example.one_manage.entity.User;
import com.example.one_manage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByRole(String role) {
        // Get notifications filtered by the specified role or 'all'
        return notificationRepository.findAll().stream()
                .filter(notification -> notification.getTarget().equals(role) || notification.getTarget().equals("all"))
                .collect(Collectors.toList());
    }






}
