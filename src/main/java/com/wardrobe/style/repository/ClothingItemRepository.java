package com.wardrobe.style.repository;

import com.wardrobe.style.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClothingItemRepository extends JpaRepository<ClothingItem, UUID> {
    List<ClothingItem> findByUser(User user);
    List<ClothingItem> findBySeason(SeasonType season);
    List<ClothingItem> findByType(ItemType type);
    @Query(value = """
    SELECT * FROM clothing_items
    WHERE user_id = :userId
    AND occasions @> CAST(:occasionJson AS jsonb)
""", nativeQuery = true)
    List<ClothingItem> findByUserAndOccasion(@Param("userId") UUID userId, @Param("occasionJson") String occasionJson);
}
