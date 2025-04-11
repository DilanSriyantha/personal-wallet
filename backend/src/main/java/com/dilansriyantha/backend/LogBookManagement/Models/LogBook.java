package com.dilansriyantha.backend.LogBookManagement.Models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogBook {
    
    private Integer id;

    private String caption;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
