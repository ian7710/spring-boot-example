package com.example.service;

import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserStatusUpdateService {

    @Autowired
    private UserRepository userRepository;

    /**
     * This scheduled task runs daily at midnight to deactivate users who have been inactive for more than 6 months.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void deactivateInactiveUsers() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoffDate = now.minusMonths(6);

        int updatedRows = userRepository.deactivateUsersInactiveSince(cutoffDate);
        System.out.println("Deactivated " + updatedRows + " users who were inactive since " + cutoffDate);
    }
}
