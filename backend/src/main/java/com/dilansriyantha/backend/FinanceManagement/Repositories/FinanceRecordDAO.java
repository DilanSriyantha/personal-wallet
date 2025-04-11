package com.dilansriyantha.backend.FinanceManagement.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dilansriyantha.backend.Enums.Category;
import com.dilansriyantha.backend.Enums.FinanceType;
import com.dilansriyantha.backend.FinanceManagement.Models.FinanceRecord;
import com.dilansriyantha.backend.Utils.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FinanceRecordDAO implements DAO<FinanceRecord> {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<FinanceRecord> rowMapper = (rs, rowNum) -> {
        return FinanceRecord.builder()
            .id(rs.getInt("id"))
            .date(rs.getDate("date"))
            .description(rs.getString("description"))
            .financeType(FinanceType.valueOf(rs.getString("financeType")))
            .category(Category.valueOf(rs.getString("category")))
            .updatedAt(rs.getTimestamp("updated_at"))
            .createdAt(rs.getTimestamp("created_at"))
            .build();
    };
    
    @Override
    public List<FinanceRecord> getAll() {
        var sql = "SELECT * FROM finance_record";

        var financeRecords = jdbcTemplate.query(sql, rowMapper);

        return financeRecords;
    }

    @Override
    public Page<FinanceRecord> getPage(int page, int pageSize) {
        int offset = page*pageSize;

        var sql = "SELECT * FROM finance_record ORDER BY id ASC LIMIT ? OFFSET ?";
        var financeRecords = jdbcTemplate.query(sql, rowMapper, pageSize, offset);

        var countSql = "SELECT COUNT(*) FROM finance_record";
        var total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(financeRecords, PageRequest.of(page, pageSize), total);
    }

    @Override
    public Optional<FinanceRecord> get(int id) {
        var sql = "SELECT * FROM finance_record WHERE id=?";

        var financeRecord = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return Optional.ofNullable(financeRecord);
    }

    @Override
    public void create(FinanceRecord financeRecord) {
        var sql = "INSERT INTO finance_record (date, description, financeType, category) VALUES (?, ?, ?, ?)";

        var rowsAffected = jdbcTemplate.update(sql, financeRecord.getDate(), financeRecord.getDescription(), financeRecord.getFinanceType().name(), financeRecord.getCategory().name());

        if(rowsAffected > 0){
            log.info("Finance record is created successfully.");
            return;
        }
        log.error("Error occurred when creating a finance record");
    }

    @Override
    public void update(int id, FinanceRecord newDetails) {
        var sql = "UPDATE finance_record SET date=?, description=?, financeType=?, category=? WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, newDetails.getDate(), newDetails.getDescription(), newDetails.getFinanceType(), newDetails.getCategory(), id);

        if(rowsAffected > 0){
            log.info("Finance record id={} is updated successfully.", id);
            return;
        }
        log.error("Error occurred when updating the finance record id={}", id);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM finance_record WHERE id=?";

        var rowsAffected = jdbcTemplate.update(sql, id);

        if(rowsAffected > 0){
            log.info("Finance record id={} is deleted successfully.", id);
            return;
        }
        log.error("Error occurred when deleting the finance record id={}", id);
    }
}
