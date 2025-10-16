package com.wardrobe.style.mapper;

import com.wardrobe.style.dto.ClothingItemRequest;
import com.wardrobe.style.entity.ClothingItem;
import com.wardrobe.style.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ClothingItemMapper {

    public ClothingItem toEntity(ClothingItemRequest request, User user) {
        return ClothingItem.builder()
                .user(user)
                .imageId(request.getImageId())
                .thumbnailUrl(request.getThumbnailUrl())
                .type(request.getType())
                .brand(request.getBrand())
                .material(request.getMaterial())
                .primaryColor(request.getPrimaryColor())
                .secondaryColor(request.getSecondaryColor())
                .pattern(request.getPattern())
                .season(request.getSeason())
                .occasions(request.getOccasions())
                .attributes(request.getAttributes())
                .notes(request.getNotes())
                .purchasedAt(request.getPurchasedAt())
                .isFavorite(Boolean.TRUE.equals(request.getIsFavorite()))
                .build();
    }
}