package com.dilansriyantha.backend.Authentication.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dilansriyantha.backend.Authentication.Models.User;
import com.dilansriyantha.backend.Enums.Role;
import com.dilansriyantha.backend.Utils.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDAO implements DAO<User> {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        return User.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .role(Role.valueOf(rs.getString("role")))
            .updatedAt(rs.getTimestamp("updated_at"))
            .createdAt(rs.getTimestamp("created_at"))
            .build();
    };

    @Override
    public List<User> getAll() {
        var sql = "SELECT * FROM user";

        var users = jdbcTemplate.query(sql, rowMapper);

        return users;
    }

    @Override
    public Page<User> getPage(int page, int pageSize) {
        var offset = page*pageSize;

        var sql = "SELECT * FROM user ORDER BY id ASC LIMIT ? OFFSET ?";
        var users = jdbcTemplate.query(sql, rowMapper, pageSize, offset);

        var countSql = "SELECT COUNT(*) FROM user";
        var total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(users, PageRequest.of(page, pageSize), total);
    }

    @Override
    public Optional<User> get(int id) {
        var sql = "SELECT * FROM user WHERE id=?";

        var user = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return Optional.ofNullable(user);
    }
    
    public Optional<User> getByEmail(String email) {
        var sql = "SELECT * FROM user WHERE email=?";

        var user = jdbcTemplate.queryForObject(sql, rowMapper, email);

        return Optional.ofNullable(user);
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
    public void update(int id, User newDetails) {
        var sql = "UPDATE user SET name=?, email=?, password=?, role=? WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, newDetails.getName(), newDetails.getEmail(), newDetails.getPassword(), newDetails.getRole().name());

        if(rowsAffected > 0){
            log.info("User updated successfully.");
            return;
        }
        log.error("Error occurred when updating user id={}", id);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM user WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, id);

        if(rowsAffected > 0){
            log.info("User deleted successfully.");
            return;
        }
        log.error("Error occurred when deleting user id={}", id);
    }
    
}
