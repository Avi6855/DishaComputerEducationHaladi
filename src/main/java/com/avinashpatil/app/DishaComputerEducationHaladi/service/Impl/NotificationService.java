package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyBatchUpdate(String batchId, String message) {
        messagingTemplate.convertAndSend("/topic/batch/" + batchId, message);
    }

    public void notifyUser(String userId, String message) {
        messagingTemplate.convertAndSendToUser(userId, "/notifications", message);
    }
}