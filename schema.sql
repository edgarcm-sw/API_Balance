CREATE DATABASE IF NOT EXISTS balance_db;
USE balance_db;

-- ==========================================
-- Módulo de Usuario y Configuración
-- ==========================================

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    height DECIMAL(5,2) NOT NULL,
    tmb DECIMAL(7,2),
    getd DECIMAL(7,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Daily_Log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    log_date DATE NOT NULL,
    calorie_goal DECIMAL(7,2) NOT NULL,
    calories_consumed DECIMAL(7,2) DEFAULT 0.00,
    calories_remaining DECIMAL(7,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    UNIQUE (user_id, log_date)
);

CREATE TABLE System_Insight (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    insight_type VARCHAR(50) NOT NULL, -- Ej. 'MOTIVATION', 'SLEEP', 'PERFORMANCE'
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

-- ==========================================
-- Módulo de Alimentación
-- ==========================================

CREATE TABLE Food_Item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    base_calories DECIMAL(7,2) NOT NULL,
    description TEXT
);

CREATE TABLE Meal_Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- Ej. 'Desayuno', 'Comida', 'Snack', 'Cena'
);

CREATE TABLE Food_Entry (
    id INT AUTO_INCREMENT PRIMARY KEY,
    daily_log_id INT NOT NULL,
    food_item_id INT NOT NULL,
    meal_category_id INT NOT NULL,
    quantity DECIMAL(5,2) DEFAULT 1.00,
    total_calories DECIMAL(7,2) NOT NULL,
    entry_time TIME,
    FOREIGN KEY (daily_log_id) REFERENCES Daily_Log(id) ON DELETE CASCADE,
    FOREIGN KEY (food_item_id) REFERENCES Food_Item(id),
    FOREIGN KEY (meal_category_id) REFERENCES Meal_Category(id)
);

-- ==========================================
-- Módulo de Actividad Física y Recuperación
-- ==========================================

CREATE TABLE Activity_Type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, -- Ej. 'Caminata', 'Yoga', 'Bicicleta'
    calories_per_minute DECIMAL(5,2) NOT NULL
);

CREATE TABLE Exercise_Entry (
    id INT AUTO_INCREMENT PRIMARY KEY,
    daily_log_id INT NOT NULL,
    activity_type_id INT NOT NULL,
    duration_minutes INT NOT NULL,
    calories_burned DECIMAL(7,2) NOT NULL,
    entry_time TIME,
    FOREIGN KEY (daily_log_id) REFERENCES Daily_Log(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_type_id) REFERENCES Activity_Type(id)
);

CREATE TABLE Sleep_Log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    daily_log_id INT NOT NULL,
    bed_time DATETIME NOT NULL,
    wake_time DATETIME NOT NULL,
    total_hours DECIMAL(4,2) NOT NULL,
    quality_percentage INT NOT NULL CHECK (quality_percentage BETWEEN 0 AND 100),
    FOREIGN KEY (daily_log_id) REFERENCES Daily_Log(id) ON DELETE CASCADE,
    UNIQUE (daily_log_id)
);

-- ==========================================
-- Módulo de Comunidad
-- ==========================================

CREATE TABLE Anonymous_Profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    alias VARCHAR(100) NOT NULL UNIQUE, -- Ej. 'Sauce', 'Cerezo', 'Marea'
    avatar_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    UNIQUE (user_id)
);

CREATE TABLE Post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anonymous_profile_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (anonymous_profile_id) REFERENCES Anonymous_Profile(id) ON DELETE CASCADE
);

CREATE TABLE Post_Interaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    interaction_type VARCHAR(50) NOT NULL, -- Ej. 'LIKE', 'COMMENT'
    interaction_value TEXT, -- Null for LIKE, Text for COMMENT
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES Post(id) ON DELETE CASCADE
);

-- Datos iniciales básicos
INSERT INTO Meal_Category (name) VALUES ('Desayuno'), ('Comida'), ('Snack'), ('Cena');
INSERT INTO Activity_Type (name, calories_per_minute) VALUES ('Caminata', 4.0), ('Yoga', 3.5), ('Bicicleta', 7.0);
