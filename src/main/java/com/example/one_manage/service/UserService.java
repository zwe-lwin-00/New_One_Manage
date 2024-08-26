package com.example.one_manage.service;

import com.example.one_manage.entity.User;
import com.example.one_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long user_id) {
        return userRepository.findById(user_id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public Optional<User> findById(Long user_id) {
        return userRepository.findById(user_id);
    }
}
