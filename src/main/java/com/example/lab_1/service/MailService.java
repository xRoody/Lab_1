package com.example.lab_1.service;


public interface MailService {
    void sendMessageWithHTML(String to, String theme,  Long taskId, boolean isOwner);
}
