package com.example.one_manage.controller;

import com.example.one_manage.entity.Notification;
import com.example.one_manage.entity.User;
import com.example.one_manage.service.NotificationService;
import com.example.one_manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{user_id}")
    public String showEditForm(@PathVariable("user_id") Long user_id, Model model) {
        Optional<User> user = userService.getUserById(user_id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user-form";
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id) {
        userService.deleteUser(user_id);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This will resolve to users/login.html due to the prefix
    }

    // @PostMapping("/login")
    // public String login(@RequestParam("email") String email,
    // @RequestParam("password") String password, Model model) {
    // Optional<User> user = userService.findByEmail(email);
    // if (user.isPresent() && user.get().getPassword().equals(password)) {
    // // Set user info in model to use in the dashboard
    // model.addAttribute("user", user.get());
    // // Fetch notifications based on user role
    // List<Notification> notifications =
    // notificationService.getNotificationsByRole(user.get().getRole());
    // model.addAttribute("notifications", notifications);
    // return "dashboard"; // This will resolve to users/dashboard.html due to the
    // prefix
    // }
    // // If authentication fails, return to the login page with an error message
    // model.addAttribute("error", "Invalid email or password");
    // return "login"; // This will resolve to users/login.html due to the prefix
    // }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // Set user info in model to use in the dashboard
            model.addAttribute("user", user.get());
            // Fetch notifications based on user role and dismissal status
            List<Notification> notifications = notificationService.getNotificationsByRoleAndUser(user.get().getRole(),
                    user.get());
            model.addAttribute("notifications", notifications);
            return "dashboard";
        }
        // If authentication fails, return to the login page with an error message
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    // @GetMapping("/dashboard")
    // public String showDashboard(@RequestParam("role") String role, Model model) {
    // List<Notification> notifications =
    // notificationService.getNotificationsByRole(role);
    // model.addAttribute("notifications", notifications);
    // return "dashboard"; // This will resolve to users/dashboard.html due to the
    // prefix
    // }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam("role") String role, Model model, @RequestParam("user_id") Long userId) {
        User user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            List<Notification> notifications = notificationService.getNotificationsByRoleAndUser(role, user);
            model.addAttribute("notifications", notifications);
            model.addAttribute("user", user);
        }
        return "dashboard";
    }

    @PostMapping("/dismiss-notification")
    public String dismissNotification(@RequestParam("notificationId") Long notificationId,
            @RequestParam("user_id") Long userId) {
        User user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            notificationService.dismissNotification(user, notificationId);
        }
        return "redirect:/users/dashboard?role=" + user.getRole() + "&user_id=" + userId;
    }

}
