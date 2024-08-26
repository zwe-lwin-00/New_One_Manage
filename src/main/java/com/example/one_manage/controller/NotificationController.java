package com.example.one_manage.controller;

import com.example.one_manage.entity.Notification;
import com.example.one_manage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String showindex() {
        return "index";
    }

    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        model.addAttribute("notifications", notificationService.getAllNotifications());
        return "notifications"; // The name of the Thymeleaf template
    }

    @GetMapping("/notification/new")
    public String showNotificationForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "notification-form";
    }

    @PostMapping("/notification/save")
    public String saveNotification(@ModelAttribute Notification notification) {
        notificationService.saveNotification(notification);
        return "redirect:/notifications";
    }
}
