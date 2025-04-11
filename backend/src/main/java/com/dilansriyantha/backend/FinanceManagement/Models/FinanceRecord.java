package com.dilansriyantha.backend.FinanceManagement.Models;

import java.sql.Date;
import java.sql.Timestamp;

import com.dilansriyantha.backend.Enums.Category;
import com.dilansriyantha.backend.Enums.FinanceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceRecord {
    
    private Integer id;

    private Date date;

    private String description;

    private FinanceType financeType;

    private Category category;

    private Timestamp updatedAt;

    private Timestamp createdAt;
}
