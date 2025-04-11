package com.dilansriyantha.backend.FinanceManagement.Services;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dilansriyantha.backend.FinanceManagement.Models.FinanceRecord;
import com.dilansriyantha.backend.FinanceManagement.Repositories.FinanceRecordDAO;
import com.dilansriyantha.backend.Types.SuccessResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinanceRecordService {
    
    private final FinanceRecordDAO financeRecordDAO;

    public Page<FinanceRecord> getPage(int page, int pageSize) {
        return financeRecordDAO.getPage(page, pageSize);
    }

    public SuccessResponse create(FinanceRecord record) {
        financeRecordDAO.create(record);

        return SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Finance record is created successfully.")
            .build();
    }

    public SuccessResponse update(int recordId, FinanceRecord newDetails) {
        financeRecordDAO.update(recordId, newDetails);

        return SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Finance record is created successfully.")
            .build();
    }

    public SuccessResponse delete(int recordId) {
        financeRecordDAO.delete(recordId);

        return SuccessResponse.builder()
        .status(HttpStatus.OK.value())
        .message("Finance record is created successfully.")
        .build();
    }
}
