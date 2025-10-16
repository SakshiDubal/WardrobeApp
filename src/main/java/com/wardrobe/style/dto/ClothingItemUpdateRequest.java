package com.wardrobe.style.dto;

import com.wardrobe.style.entity.ItemType;
import com.wardrobe.style.entity.SeasonType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClothingItemUpdateRequest {
    private String name;
    private ItemType itemType;
    private SeasonType season;
    private int stockQuantity;
    private int lowStockThreshold;
}
