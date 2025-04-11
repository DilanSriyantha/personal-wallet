package com.dilansriyantha.backend.LogBookManagement.Services;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dilansriyantha.backend.Authentication.Repositories.UserDAO;
import com.dilansriyantha.backend.LogBookManagement.Models.LogBook;
import com.dilansriyantha.backend.LogBookManagement.Repositories.LogBookDAO;
import com.dilansriyantha.backend.Types.SuccessResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogBookService {
    
    private final LogBookDAO logBookDAO;

    private final UserDAO userDAO;

    public Page<LogBook> getPage(String email, int page, int pageSize) throws Exception {
        var user = userDAO.getByEmail(email)
            .orElseThrow(() -> new Exception("User not found"));

        return logBookDAO.getPage(user.getId(), page, pageSize);
    }

    public SuccessResponse create(String email, LogBook logBook) throws Exception {
        var user = userDAO.getByEmail(email)
            .orElseThrow(() -> new Exception("User not found"));

        logBookDAO.create(user.getId(), logBook);

        return SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Logbook created successfully.")
            .build();
    }

    public SuccessResponse update(int logbookId, LogBook newDetails) {
        logBookDAO.update(logbookId, newDetails);

        return SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Logbook updated successfully.")
            .build();
    }

    public SuccessResponse delete(int logbookId) {
        logBookDAO.delete(logbookId);

        return SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Logbook deleted successfully.")
            .build();
    }
}
