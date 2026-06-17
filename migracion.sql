SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- 1. Modificar tabla User
ALTER TABLE User 
    ADD COLUMN alias VARCHAR(100) NOT NULL DEFAULT '' AFTER password,
    ADD COLUMN avatar_url VARCHAR(255) AFTER alias,
    DROP COLUMN age,
    DROP COLUMN weight,
    DROP COLUMN height,
    DROP COLUMN tmb,
    DROP COLUMN getd;

-- Agregar constraint UNIQUE al alias
ALTER TABLE User ADD CONSTRAINT uq_user_alias UNIQUE (alias);

-- 2. Crear tabla User_Profile
CREATE TABLE User_Profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    age INT NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    height DECIMAL(5,2) NOT NULL,
    tmb DECIMAL(7,2),
    getd DECIMAL(7,2),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

-- 3. Modificar tabla Post
ALTER TABLE Post ADD COLUMN user_id INT AFTER id;
UPDATE Post p JOIN Anonymous_Profile a ON p.anonymous_profile_id = a.id SET p.user_id = a.user_id;
ALTER TABLE Post DROP FOREIGN KEY post_ibfk_1;
ALTER TABLE Post DROP COLUMN anonymous_profile_id;
ALTER TABLE Post ADD CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE;

-- 4. Eliminar Anonymous_Profile
DROP TABLE Anonymous_Profile;

SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;