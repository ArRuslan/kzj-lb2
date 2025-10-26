CREATE TABLE `users` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `login` VARCHAR(64) UNIQUE NOT NULL,
    `password` CHAR(64) NOT NULL,
    `fullName` VARCHAR(255) NOT NULL,
    `role` ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE `groups` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE `user_groups` (
    `user_id` BIGINT NOT NULL,
    `group_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`group_id`) REFERENCES `groups`(`id`) ON DELETE CASCADE,
    PRIMARY KEY (`user_id`, `group_id`)
);