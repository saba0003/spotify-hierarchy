package com.solvd.spotify.service;

import com.solvd.spotify.domain.models.User;
import com.solvd.spotify.domain.repositories.UserRepository;

import java.util.List;

public class UserService implements Service<User, Integer> {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }
}
