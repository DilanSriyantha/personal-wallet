package com.dilansriyantha.backend.LogBookManagement.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dilansriyantha.backend.LogBookManagement.Models.LogBook;
import com.dilansriyantha.backend.Utils.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LogBookDAO implements DAO<LogBook> {
    
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<LogBook> rowMapper = (rs, rowNum) -> {
        return LogBook.builder()
            .id(rs.getInt("id"))
            .caption(rs.getString("caption"))
            .description(rs.getString("description"))
            .updatedAt(rs.getTimestamp("updated_at"))
            .createdAt(rs.getTimestamp("created_at"))
            .build();
    };
    
    @Override
    public List<LogBook> getAll() {
        var sql = """
            SELECT 
                l.id AS id,
                l.caption AS caption,
                l.description AS description,
                l.updated_at AS updated_at,
                l.created_at AS created_at
            FROM logbook
        """;

        var logBooks = jdbcTemplate.query(sql, rowMapper);

        return logBooks;
    }

    @Override
    public Page<LogBook> getPage(int page, int pageSize) {
        var offset = page*pageSize;

        var sql = """
            SELECT
                l.id AS id,
                l.caption AS caption,
                l.description AS description,
                l.updated_at AS updated_at,
                l.created_at AS created_at
            FROM logbook ORDER BY id ASC LIMIT ? OFFSET ?
        """;
        var logBooks = jdbcTemplate.query(sql, rowMapper, pageSize, offset);

        var countSql = "SELECT COUNT(*) FROM logbook";
        var total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(logBooks, PageRequest.of(page, pageSize), total);
    }

    public Page<LogBook> getPage(int userId, int page, int pageSize) {
        var offset = page*pageSize;

        var sql = """
                SELECT 
                    l.id AS id,
                    l.caption AS caption,
                    l.description AS description,
                    l.updated_at AS updated_at,
                    l.created_at AS created_at
                FROM user_logbook ul 
                JOIN user u ON ul.user_id = u.id
                JOIN logbook l ON ul.logbook_id = l.id
                WHERE u.id=?
                ORDER BY l.id ASC LIMIT ? OFFSET ?
                """;
        var logbooks = jdbcTemplate.query(sql, rowMapper, userId, pageSize, offset);

        var countSql = """
                SELECT COUNT(*) FROM user_logbook ul 
                JOIN user u ON ul.user_id = u.id
                JOIN logbook l ON ul.logbook_id = l.id
                ORDER BY l.id ASC
                """;
        var total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(logbooks, PageRequest.of(page, pageSize), total);
    }

    @Override
    public Optional<LogBook> get(int id) {
        var sql = """
                SELECT * FROM (
                    SELECT 
                        l.id AS id,
                        l.caption AS caption,
                        l.description AS description,
                        l.updated_at AS updated_at,
                        l.created_at AS created_at
                    FROM user_logbook ul
                    JOIN user u ON ul.user_id = u.id
                    JOIN logbook l on ul.logbook_id = l.id
                    ORDER BY ul.id ASC
                ) t WHERE t.id=?
                """;
        var logBook = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return Optional.ofNullable(logBook);
    }

    @Override
    public void create(LogBook logBook) {
        throw new UnsupportedOperationException("Logbook must be created with user");
    }

    public void create(int userId, LogBook logBook) {
        var sql = "CALL create_logbook_with_user(?, ?, ?)";

        var rowsAffected = jdbcTemplate.update(sql, userId, logBook.getCaption(), logBook.getDescription());

        if(rowsAffected > 0){
            log.info("Logbook created successfully.");
            return;
        }
        log.error("Error occurred when creating a logbook");
    }

    @Override
    public void update(int id, LogBook newDetails) {
        var sql = "UPDATE logbook SET caption=?, description=? WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, newDetails.getCaption(), newDetails.getDescription(), id);

        if(rowsAffected > 0){
            log.info("Logbook updated successfully.");
            return;
        }
        log.error("Error occurred when updating logbook id={}", id);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM logbook WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, id);

        if(rowsAffected > 0){
            log.info("Logbook with ID {} was deleted successfully.", id);
            return;
        }
        log.error("No logbook found with ID {}. Nothing was deleted", id);
    }
    
}
