package com.dilansriyantha.backend.FinanceManagement.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dilansriyantha.backend.FinanceManagement.Models.FinanceRecord;
import com.dilansriyantha.backend.FinanceManagement.Services.FinanceRecordService;
import com.dilansriyantha.backend.Types.SuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api/v1/finance-management")
@RequiredArgsConstructor
public class FinanceRecordController {
    
    private final FinanceRecordService financeRecordService;

    @GetMapping("/page")
    public @ResponseBody ResponseEntity<Page<FinanceRecord>> handleGetPage(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(financeRecordService.getPage(page, pageSize));
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<SuccessResponse> handleCreateFinanceRecord(@RequestBody FinanceRecord record) {
        return ResponseEntity.ok(financeRecordService.create(record));
    }
    
    @PutMapping("/update")
    public @ResponseBody ResponseEntity<SuccessResponse> handleUpdateFinanceRecord(@RequestParam int recordId, @RequestBody FinanceRecord newDetails) {
        return ResponseEntity.ok(financeRecordService.update(recordId, newDetails));
    }
    
    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<SuccessResponse> handleDeleteFinanceRecord(@RequestParam int recordId) {
        return ResponseEntity.ok(financeRecordService.delete(recordId));
    }
}
