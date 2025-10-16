package com.wardrobe.style.controller;

import com.wardrobe.style.dto.ClothingItemRequest;
import com.wardrobe.style.dto.ClothingItemUpdateRequest;
import com.wardrobe.style.entity.*;
import com.wardrobe.style.repository.ClothingItemRepository;
import com.wardrobe.style.repository.UserRepository;
import com.wardrobe.style.service.ClothingItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/user/clothingitem")
@AllArgsConstructor
public class ClothingController {

    private final ClothingItemService service;
    private final ClothingItemRepository repository;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addClothingItem(@RequestBody @Valid ClothingItemRequest request, Principal principal) {
        try {
            System.out.println("🚀 Entered addClothingItem controller"); // 👈 debug
            System.out.println("✅ ClothingController POST hit");

            String email = principal.getName(); // set by JwtRequestFilter
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            SeasonType seasonEnum =  request.getSeason();
            ClothingItem saved = service.addClothingItem(user, request, seasonEnum);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Item saved",
                    "id", saved.getId()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }

    @GetMapping("/wardrobe")
    public ResponseEntity<?> getWardrobeItems(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((List<ClothingItem>) Map.of("error", "Unauthorized Access"));
            }
            String email = principal.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            List<ClothingItem> items = repository.findByUser(user);

            return ResponseEntity.ok(Map.of(
                    "message", "Wardrobe items successfully fetched",
                    "status", "success",
                    "wardrobeItems", items
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/occasion/{occasion}")
    public ResponseEntity<?> getByOccasion(Principal principal, @PathVariable String occasion) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unauthorized Access"));
            }

            String email = principal.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            OccasionType occasionEnum;
            try {
                occasionEnum = OccasionType.valueOf(occasion.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid occasion type: " + occasion));
            }

            List<ClothingItem> items = service.getByOccasion(user, occasionEnum);
            if (items == null || items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No clothing items found for occasion: " + occasionEnum));
            }
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }
    @GetMapping("/season/{season}")
    public ResponseEntity<?> getItemsBySeason(Principal principal, @PathVariable String season) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unauthorized Access"));
            }

            String email = principal.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            SeasonType seasonEnum;
            try {
                seasonEnum = SeasonType.valueOf(season.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid season: " + season));
            }

            List<ClothingItem> items = service.getItemsBySeason(seasonEnum);
            if (items == null || items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No clothing items found for season: " + seasonEnum));
            }
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }
    @GetMapping("/itemtype/{itemtype}")
    public ResponseEntity<?> getItemsByType(Principal principal, @PathVariable String itemtype) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unauthorized Access"));
            }

            String email = principal.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            ItemType itemTypeEnum;
            try {
                itemTypeEnum = ItemType.valueOf(itemtype.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid item type: " + itemtype));
            }

            List<ClothingItem> items = service.getItemsByType(itemTypeEnum);
            if (items == null || items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No clothing items found for type: " + itemTypeEnum));
            }
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }
    @PutMapping("/item/{id}")
    public ResponseEntity<?> updateClothingItem(@PathVariable UUID id,
                                                @RequestBody ClothingItemUpdateRequest dto,
                                                Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unauthorized Access"));
            }

            ClothingItem updatedItem = service.updateClothingItem(id, dto, principal.getName());
            return ResponseEntity.ok(updatedItem);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }
    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteClothingItem(@PathVariable UUID id, Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unauthorized Access"));
            }

            service.deleteClothingItem(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Item deleted successfully"));

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }

}