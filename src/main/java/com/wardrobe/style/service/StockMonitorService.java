package com.wardrobe.style.service;

import com.wardrobe.style.entity.ClothingItem;
import com.wardrobe.style.repository.ClothingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMonitorService {

    @Autowired
    private ClothingItemRepository repository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * *")// every day at 9 AM

    public void checkLowStockItems() {
        List<ClothingItem> allItems = repository.findAll();

        for (ClothingItem item : allItems) {
            if (item.getStockQuantity() <= item.getLowStockThreshold()) {
                emailService.sendLowStockAlert(
                        item.getUser().getEmail(),
                        item.getPattern(),
                        item.getStockQuantity()
                );
            }
        }
    }
}