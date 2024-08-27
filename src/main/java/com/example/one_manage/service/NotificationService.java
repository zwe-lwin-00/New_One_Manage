package com.example.one_manage.service;

import com.example.one_manage.entity.Notification;
import com.example.one_manage.entity.User;
import com.example.one_manage.entity.UserNotificationStatus;
import com.example.one_manage.repository.NotificationRepository;
import com.example.one_manage.repository.UserNotificationStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationStatusRepository userNotificationStatusRepository;

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

    /**
     * Retrieves notifications based on the user's role and excludes notifications
     * that the user has dismissed.
     *
     * @param role The role of the user (e.g., "admin", "superadmin").
     * @param user The user object.
     * @return A list of notifications filtered by the user's role and dismissal
     *         status.
     */
    // public List<Notification> getNotificationsByRoleAndUser(String role, User
    // user) {
    // // Get notifications filtered by the specified role and those that the user
    // has
    // // not dismissed
    // return notificationRepository.findAll().stream()
    // .filter(notification -> (notification.getTarget().equals(role)
    // || notification.getTarget().equals("all")) &&
    // userNotificationStatusRepository
    // .findByUserAndNotificationAndIsDismissed(user, notification, true).isEmpty())
    // .collect(Collectors.toList());
    // }

    public List<Notification> getNotificationsByRoleAndUser(String role, User user) {
        // Get all notifications filtered by the specified role
        return notificationRepository.findAll().stream()
                .filter(notification -> (notification.getTarget().equals(role)
                        || notification.getTarget().equals("all")) &&
                        userNotificationStatusRepository
                                .findByUserAndNotificationAndIsDismissed(user, notification, true).isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Dismisses a notification for a specific user. Once dismissed, the
     * notification will not appear again for that user.
     *
     * @param user           The user who dismissed the notification.
     * @param notificationId The ID of the notification to be dismissed.
     */
    public void dismissNotification(User user, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            UserNotificationStatus status = new UserNotificationStatus();
            status.setUser(user);
            status.setNotification(notification);
            status.setDismissed(true);
            userNotificationStatusRepository.save(status);
        }
    }

}
