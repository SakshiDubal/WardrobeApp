package com.wardrobe.style.service;

import com.wardrobe.style.dto.ClothingItemRequest;
import com.wardrobe.style.dto.ClothingItemUpdateRequest;
import com.wardrobe.style.entity.*;
import com.wardrobe.style.repository.ClothingItemRepository;
import com.wardrobe.style.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClothingItemService {

    @Autowired
    private ClothingItemRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public ClothingItem addClothingItem(User user, ClothingItemRequest request, SeasonType seasonEnum) {

        ClothingItem item = ClothingItem.builder()
                .user(user)
                .imageId(request.getImageId())
                .thumbnailUrl(request.getThumbnailUrl())
                .type(request.getType())
                .brand(request.getBrand())
                .material(request.getMaterial())
                .primaryColor(request.getPrimaryColor())
                .secondaryColor(request.getSecondaryColor())
                .pattern(request.getPattern())
                .season(seasonEnum)
                .occasions(request.getOccasions())
                .attributes(request.getAttributes())
                .notes(request.getNotes())
                .purchasedAt(request.getPurchasedAt())
                .isFavorite(Boolean.TRUE.equals(request.getIsFavorite()))
                .build();

        // ✅ Only set imageId if provided
        if (request.getImageId() != null) {
            item.setImageId(request.getImageId());
        }


        return repository.save(item);
    }
    public ClothingItem updateClothingItem(UUID itemId, ClothingItemUpdateRequest dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ClothingItem item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Clothing item not found"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this item");
        }

        item.setPattern(dto.getName());
        item.setType(dto.getItemType());
        item.setSeason(dto.getSeason());
        item.setStockQuantity(dto.getStockQuantity());
        item.setLowStockThreshold(dto.getLowStockThreshold());

        return repository.save(item);
    }
    public void deleteClothingItem(UUID itemId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ClothingItem item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Clothing item not found"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this item");
        }

        repository.delete(item);
    }



    public List<ClothingItem> getItemsForUser(User user) {
        return repository.findByUser(user);
    }

    public List<ClothingItem> getByOccasion(User user, OccasionType occasion) {
        String occasionJson = "[\"" + occasion.name().toLowerCase() + "\"]";
        return repository.findByUserAndOccasion(user.getId(), occasionJson);
    }

    public List<ClothingItem> getItemsBySeason(SeasonType seasonEnum) {
        return repository.findBySeason(seasonEnum);
    }

    public List<ClothingItem> getItemsByType(ItemType itemTypeEnum) {
        return repository.findByType(itemTypeEnum);
    }

    public void updateStock(UUID itemId, int quantityPurchased) {
        ClothingItem item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Clothing item not found with ID: " + itemId));

        item.setStockQuantity(item.getStockQuantity() - quantityPurchased);
        repository.save(item);

        if (item.getStockQuantity() <= item.getLowStockThreshold()) {
            notifyLowStock(item);
        }
    }

    public void notifyLowStock(ClothingItem item) {
        String email = item.getUser().getEmail(); // assuming each item is linked to a user
        emailService.sendLowStockAlert(email, item.getPattern(), item.getStockQuantity());
    }



}