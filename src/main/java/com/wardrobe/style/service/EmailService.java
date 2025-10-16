package com.wardrobe.style.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendLowStockAlert(String toEmail, String itemName, int quantityLeft) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Low Stock Alert: " + itemName);
        message.setText("Stock for item '" + itemName + "' is low. Only " + quantityLeft + " left in inventory.");

        mailSender.send(message);
    }
}