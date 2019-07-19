package com.example.mapper;

import com.example.model.User;

import java.util.List;

public interface UserMapper {

    public List<User> findAll();

    public void addUser(User user);
}
