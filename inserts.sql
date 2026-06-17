USE balance_db;

-- ==========================================
-- 1. Módulo de Usuario y Configuración
-- ==========================================

-- Insertar Usuarios
INSERT INTO User (name, age, weight, height, tmb, getd) VALUES 
('Carlos Mendoza', 32, 78.50, 175.00, 1750.00, 2400.00),
('Ana Sofia Rios', 28, 62.00, 165.00, 1420.00, 1950.00),
('Luis Fernanda', 45, 90.00, 180.00, 1900.00, 2600.00);

-- Insertar Registros Diarios (Daily_Log)
-- Asumimos la fecha actual o reciente
INSERT INTO Daily_Log (user_id, log_date, calorie_goal, calories_consumed, calories_remaining) VALUES 
(1, '2026-06-11', 2400.00, 1850.00, 550.00),
(2, '2026-06-11', 1950.00, 1900.00, 50.00),
(1, '2026-06-10', 2400.00, 2500.00, -100.00);

-- Insertar Insights del Sistema (System_Insight)
INSERT INTO System_Insight (user_id, insight_type, message) VALUES 
(1, 'SLEEP', 'Hemos notado que tus horas de sueño han bajado. Intenta dormir al menos 7 horas hoy.'),
(2, 'MOTIVATION', '¡Excelente racha! Has cumplido tu meta calórica 5 días seguidos.'),
(3, 'PERFORMANCE', 'Tus niveles de actividad física son óptimos esta semana. ¡Sigue así!');

-- ==========================================
-- 2. Módulo de Alimentación
-- ==========================================

-- Insertar Alimentos (Food_Item)
INSERT INTO Food_Item (name, base_calories, description) VALUES 
('Avena con manzana', 250.00, 'Tazón de avena cocida con trozos de manzana natural'),
('Pechuga de pollo a la plancha', 165.00, '100g de pechuga de pollo sin piel'),
('Ensalada César', 350.00, 'Ensalada con aderezo, crutones y queso parmesano'),
('Manzana Mediana', 95.00, 'Una manzana fresca de tamaño promedio'),
('Salmón al horno', 206.00, '100g de salmón horneado con finas hierbas');

-- Insertar Entradas de Alimentos (Food_Entry)
-- Recordatorio: Los Meal_Category son 1=Desayuno, 2=Comida, 3=Snack, 4=Cena
INSERT INTO Food_Entry (daily_log_id, food_item_id, meal_category_id, quantity, total_calories, entry_time) VALUES 
(1, 1, 1, 1.00, 250.00, '08:00:00'), -- Log 1, Avena, Desayuno
(1, 2, 2, 2.00, 330.00, '14:30:00'), -- Log 1, Pechuga (200g), Comida
(2, 3, 2, 1.00, 350.00, '15:00:00'), -- Log 2, Ensalada, Comida
(2, 4, 3, 1.00, 95.00, '11:00:00'),  -- Log 2, Manzana, Snack
(3, 5, 4, 1.50, 309.00, '20:00:00'); -- Log 3, Salmón (150g), Cena

-- ==========================================
-- 3. Módulo de Actividad Física y Recuperación
-- ==========================================

-- Insertar Ejercicios (Exercise_Entry)
-- Recordatorio: Activity_Type son 1=Caminata(4.0/min), 2=Yoga(3.5/min), 3=Bicicleta(7.0/min)
INSERT INTO Exercise_Entry (daily_log_id, activity_type_id, duration_minutes, calories_burned, entry_time) VALUES 
(1, 1, 30, 120.00, '07:00:00'), -- 30 min * 4.0 = 120
(2, 2, 45, 157.50, '18:00:00'), -- 45 min * 3.5 = 157.5
(3, 3, 60, 420.00, '19:00:00'); -- 60 min * 7.0 = 420

-- Insertar Registros de Sueño (Sleep_Log)
INSERT INTO Sleep_Log (daily_log_id, bed_time, wake_time, total_hours, quality_percentage) VALUES 
(1, '2026-06-10 23:00:00', '2026-06-11 06:30:00', 7.50, 85),
(2, '2026-06-10 23:30:00', '2026-06-11 06:00:00', 6.50, 60),
(3, '2026-06-09 22:00:00', '2026-06-10 06:00:00', 8.00, 95);

-- ==========================================
-- 4. Módulo de Comunidad
-- ==========================================

-- Insertar Publicaciones (Post)
INSERT INTO Post (user_id, content) VALUES 
(1, 'Hoy me costó muchísimo salir a caminar por la mañana, pero me siento genial de haberlo logrado. ¡La disciplina es clave!'),
(2, '¿Alguien tiene alguna receta rápida para cenas ligeras? Me estoy aburriendo de mis opciones actuales.'),
(3, 'Logré mi meta de sueño de esta semana. Realmente se nota la diferencia en los entrenamientos.');

-- Insertar Interacciones (Post_Interaction)
INSERT INTO Post_Interaction (post_id, interaction_type, interaction_value) VALUES 
(1, 'LIKE', NULL),
(1, 'COMMENT', '¡Súper bien! Los primeros 10 minutos son los más difíciles.'),
(2, 'COMMENT', 'Te recomiendo probar pescado blanco a la plancha con espárragos, tardas menos de 15 minutos en prepararlo.'),
(2, 'LIKE', NULL),
(3, 'LIKE', NULL);

INSERT INTO User (name, password, age, weight, height, tmb, getd) VALUES 
('Carlos Mendoza', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 32, 78.50, 175.00, 1750.00, 2400.00),
('Ana Sofia Rios', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 28, 62.00, 165.00, 1420.00, 1950.00),
('Luis Fernanda', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 45, 90.00, 180.00, 1900.00, 2600.00);



-- Inserts para los cambios de las tablas User, Post y eliminación de la tabla de usuarios anonimos
-- Insertar Usuarios
INSERT INTO User (name, password, alias, avatar_url) VALUES 
('Carlos Mendoza', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Roble_Fuerte', NULL),
('Ana Sofia Rios', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Brisa_Marina', NULL),
('Luis Fernanda', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Cielo_Claro', NULL);

-- Insertar Perfiles (User_Profile)
INSERT INTO User_Profile (user_id, age, weight, height, tmb, getd) VALUES
(1, 32, 78.50, 175.00, 1750.00, 2400.00),
(2, 28, 62.00, 165.00, 1420.00, 1950.00),
(3, 45, 90.00, 180.00, 1900.00, 2600.00);
