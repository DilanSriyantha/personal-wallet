package com.dilansriyantha.backend.LogBookManagement.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dilansriyantha.backend.LogBookManagement.Models.LogBook;
import com.dilansriyantha.backend.LogBookManagement.Services.LogBookService;
import com.dilansriyantha.backend.Types.SuccessResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/api/v1/logbook-management")
@RequiredArgsConstructor
public class LogBookController {
    
    private final LogBookService logBookService;

    @GetMapping("/page")
    public @ResponseBody ResponseEntity<Page<LogBook>> handleGetPage(@RequestParam String email, @RequestParam int page, @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(logBookService.getPage(email, page, pageSize));
    }
    
    @PostMapping("/create")
    public @ResponseBody ResponseEntity<SuccessResponse> handleCreateLogbook(@RequestParam String email, @RequestBody LogBook logBook) throws Exception {
        return ResponseEntity.ok(logBookService.create(email, logBook));
    }

    @PutMapping("/update")
    public @ResponseBody ResponseEntity<SuccessResponse> handleUpdateLogbook(@RequestParam int logbookId, @RequestBody LogBook newDetails) {
        return ResponseEntity.ok(logBookService.update(logbookId, newDetails));
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<SuccessResponse> handleDeleteLogbook(@RequestParam int logbookId) {
        return ResponseEntity.ok(logBookService.delete(logbookId));
    }
}
