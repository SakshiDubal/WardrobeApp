package com.wardrobe.style.dto;

import com.wardrobe.style.entity.ItemType;
import com.wardrobe.style.entity.SeasonType;
import com.wardrobe.style.entity.OccasionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothingItemRequest {

    private UUID imageId;
    private String thumbnailUrl;
    private ItemType type;
    private String brand;
    private String material;
    private String primaryColor;
    private String secondaryColor;
    private String pattern;
    private SeasonType season;
    private List<OccasionType> occasions;
    @NotNull(message = "Attributes must not be null")
    private Map<String, Object> attributes;
    private String notes;
    private LocalDate purchasedAt;
    private Boolean isFavorite;
}