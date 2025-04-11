package com.dilansriyantha.backend.Authentication.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.dilansriyantha.backend.Authentication.Models.User;
import com.dilansriyantha.backend.Utils.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDAO implements DAO<User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public List<User> getPage(int page, int pageSize) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPage'");
    }

    @Override
    public Optional<User> get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
    
    public Optional<User> getByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'getByEmail'");
    }

    @Override
    public void create(User user) {
        var sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";

        var rowsAffected = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getRole().name());

        if(rowsAffected > 0){
            log.info("User created successfully.");
            return;
        }
        log.error("Error occurred when creating new user.");
    }

    @Override
    public void update(int id, User t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
