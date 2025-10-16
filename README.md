**Wardrobe Inventory & Recommendation App**

A smart wardrobe management system that lets users add clothing items, categorize them by occasion, type, and season, track stock quantities, and receive low-stock notifications via Gmail. Built for fashion tech enthusiasts and inventory-conscious users.

🚀 Features
- Add Clothing Items: Store item name, type, season, occasion, and quantity.
- Filter & Select:
- By Occasion: Casual, Formal, Party, etc.
- By Clothing Type: Tops, Bottoms, Footwear, Accessories.
- By Season: Summer, Winter, Monsoon, All-season.
- Inventory Tracking:
- Quantity field for each item.
- Threshold-based alerts for low stock.
- Email Notifications:
- Sends Gmail alerts when item quantity falls below threshold.
- Encourages restocking with direct links to preferred sources.
  
🛠️ Tech Stack
Backend - Spring Boot, Hibernate, PostgreSQL
API Design - RESTful endpoints with DTOs, validation, and JWT auth
Notifications - JavaMailSender (Gmail SMTP)
Database - JSONB, Enums, GIN indexes for fast filtering
Testing - Postman with automated token capture

📦 Schema Overview
CREATE TYPE season AS ENUM ('SUMMER', 'WINTER', 'MONSOON', 'ALL_SEASON');
CREATE TYPE occasion AS ENUM ('CASUAL', 'FORMAL', 'PARTY', 'SPORTS');

CREATE TABLE clothing_item (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  type TEXT NOT NULL,
  season season NOT NULL,
  occasion occasion NOT NULL,
  quantity INT NOT NULL,
  low_stock_threshold INT DEFAULT 2,
  metadata JSONB
);

🔔 Notification Logic
- Triggered when quantity <= low_stock_threshold.
- Sends Gmail alert using configured SMTP credentials.
- Email includes item details and restock suggestion.
- 
📂 GitLab Source
To explore the source code, clone the repository:
https://github.com/SakshiDubal/WardrobeApp

🧪 API Testing
- Postman collection includes:
- Auth token capture
- Environment variables for base URL and credentials
- CRUD operations for clothing items
- Notification trigger simulation
 
🧠 Developer Notes
- Enum binding is case-insensitive.
- Global exception handling ensures clean error responses.
- Designed for future personalization (e.g., ML-based outfit suggestions).

