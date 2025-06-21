CREATE TABLE `users` (
  `user_id` int PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255),
  `nickname` varchar(100) NOT NULL,
  `introduction` varchar(255),
  `profile_image_url` varchar(255),
  `role` enum('USER','ADMIN') DEFAULT 'USER',
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp DEFAULT (now())
);

CREATE TABLE `Oauths` (
  `oaut_id` int PRIMARY KEY AUTO_INCREMENT,
  `provider_id` varchar(255) NOT NULL,
  `provider` enum('GOOGLE','GITHUB') NOT NULL,
  `user_id` int NOT NULL
);

CREATE TABLE `categories` (
  `category_id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) UNIQUE NOT NULL,
  `created_at` timestamp DEFAULT (now())
);

CREATE TABLE `tags` (
  `tag_id` int PRIMARY KEY AUTO_INCREMENT,
  `tag_name` varchar(50) UNIQUE NOT NULL,
  `created_at` timestamp DEFAULT (now())
);

CREATE TABLE `articles` (
  `article_id` int PRIMARY KEY AUTO_INCREMENT,
  `category_id` int,
  `title` varchar(255) NOT NULL,
  `summary` varchar(500),
  `content` text NOT NULL,
  `thumbnail_path` varchar(255),
  `view_count` int DEFAULT 0,
  `is_published` boolean DEFAULT false,
  `published_at` timestamp,
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp DEFAULT (now())
);

CREATE TABLE `article_tags` (
  `article_id` int NOT NULL,
  `tag_id` int NOT NULL
);

CREATE TABLE `bookmarks` (
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  `created_at` timestamp DEFAULT (now())
);

ALTER TABLE `Oauths` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `articles` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

ALTER TABLE `article_tags` ADD FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);

ALTER TABLE `article_tags` ADD FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`);

ALTER TABLE `bookmarks` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `bookmarks` ADD FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);
