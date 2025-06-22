CREATE TABLE IF NOT EXISTS `users`
(
    `user_id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
    `email`             VARCHAR(255) UNIQUE NOT NULL,
    `password`          VARCHAR(255),
    `nickname`          VARCHAR(100)        NOT NULL,
    `introduction`      VARCHAR(255),
    `profile_image_url` VARCHAR(255),
    `role`              ENUM ('ROLE_USER','ROLE_ADMIN') DEFAULT 'ROLE_USER',
    `created_at`        DATETIME                        DEFAULT (now()),
    `updated_at`        DATETIME                        DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `oauths`
(
    `oauth_id`     BIGINT PRIMARY KEY AUTO_INCREMENT,
    `provider_id` VARCHAR(255)             NOT NULL,
    `provider`    ENUM ('GOOGLE','GITHUB') NOT NULL,
    `user_id`     BIGINT                   NOT NULL,
    `created_at`  DATETIME DEFAULT (now()),
    `updated_at`  DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `categories`
(
    `category_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`        VARCHAR(100) UNIQUE NOT NULL,
    `created_at`  DATETIME DEFAULT (now()),
    `updated_at`  DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tags`
(
    `tag_id`     BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`       VARCHAR(50) UNIQUE NOT NULL,
    `created_at` DATETIME DEFAULT (now()),
    `updated_at` DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `articles`
(
    `article_id`    BIGINT PRIMARY KEY AUTO_INCREMENT,
    `category_id`   BIGINT,
    `title`         VARCHAR(255) NOT NULL,
    `summary`       VARCHAR(500),
    `thumbnail_url` VARCHAR(255),
    `created_at`    DATETIME DEFAULT (now()),
    `updated_at`    DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `article_tags`
(
    `article_tag_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `article_id`     BIGINT NOT NULL,
    `tag_id`         BIGINT NOT NULL,
    `created_at`     DATETIME DEFAULT (now()),
    `updated_at`     DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bookmarks`
(
    `bookmark_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`    BIGINT NOT NULL,
    `article_id` BIGINT NOT NULL,
    `created_at` DATETIME DEFAULT (now()),
    `updated_at` DATETIME DEFAULT (now())
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `oauths`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `articles`
    ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

ALTER TABLE `article_tags`
    ADD FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);

ALTER TABLE `article_tags`
    ADD FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`);

ALTER TABLE `bookmarks`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `bookmarks`
    ADD FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);
