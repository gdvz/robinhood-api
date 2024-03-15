SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `topic_history`;
DROP TABLE IF EXISTS `topic_detail`;
DROP TABLE IF EXISTS `topic`;
DROP TABLE IF EXISTS `user`;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `full_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `app_user_role` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE `topic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `subject` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_by_id` bigint DEFAULT NULL,
  `create_date_time` timestamp NULL DEFAULT NULL,
  `collect` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'N',
  `update_date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_FK` (`create_by_id`),
  CONSTRAINT `topic_FK` FOREIGN KEY (`create_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;


CREATE TABLE `topic_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `topic_id` bigint NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_detail_FK` (`topic_id`),
  CONSTRAINT `topic_detail_FK` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;


CREATE TABLE `topic_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `topic_id` bigint DEFAULT NULL,
  `topic_subject` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `topic_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `topic_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_history_FK` (`topic_id`),
  CONSTRAINT `topic_history_FK` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;


CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by_id` bigint DEFAULT NULL,
  `create_date_time` timestamp NULL DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `topic_id` bigint DEFAULT NULL,
  `update_date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_FK` (`create_by_id`),
  KEY `comment_FK_1` (`topic_id`),
  CONSTRAINT `comment_FK` FOREIGN KEY (`create_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_FK_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

INSERT INTO `user`
(id, username, email, full_name, password, app_user_role)
VALUES(1, 'jakkarin', 'jakkarin@gmail.com', 'jakkarin', '$2a$10$FcBdg/1SHv9504S33q/r.OJy4nC1f2nEkt04/rHzTSDPwfyIrKS16', 'ROLE_ADMIN');
INSERT INTO `user`
(id, username, email, full_name, password, app_user_role)
VALUES(9, 'robinhood', 'robinhood@robinhood.com', 'robinhood superapp', '$2a$10$6FTWM9n6gmHsj60N/4AKbO/txxxJ6mDZr6uYkfx6DxN0/wxA3X.ci', 'ROLE_USER');

INSERT INTO topic
(id, subject, create_by_id, create_date_time, `collect`, update_date_time)
VALUES(12, 'นัดสัมภาษณ์งาน 1 แก้ไขแล้ว2', 1, '2024-03-15 00:16:07', 'N', '2024-03-15 00:24:58');
INSERT INTO topic
(id, subject, create_by_id, create_date_time, `collect`, update_date_time)
VALUES(14, 'นัดสัมภาษณ์งาน 2', 9, '2024-03-15 00:37:29', 'N', '2024-03-15 00:37:29');
INSERT INTO topic
(id, subject, create_by_id, create_date_time, `collect`, update_date_time)
VALUES(15, 'นัดสัมภาษณ์งาน 3', 9, '2024-03-15 00:38:17', 'N', '2024-03-15 00:38:17');
INSERT INTO topic
(id, subject, create_by_id, create_date_time, `collect`, update_date_time)
VALUES(16, 'นัดสัมภาษณ์งาน 4', 9, '2024-03-15 00:39:15', 'N', '2024-03-15 00:39:15');
INSERT INTO topic
(id, subject, create_by_id, create_date_time, `collect`, update_date_time)
VALUES(17, 'นัดสัมภาษณ์งาน 5', 1, '2024-03-15 00:39:58', 'N', '2024-03-15 00:39:58');

INSERT INTO topic_detail
(id, topic_id, description, status)
VALUES(9, 12, 'รายละเอียดการนัดหมาย แก้ไขแล้ว2', 'Done');
INSERT INTO topic_detail
(id, topic_id, description, status)
VALUES(10, 14, 'Chicago, Illinois, couple Valerie and Ted Rock took the cat in two years ago', 'To Do');
INSERT INTO topic_detail
(id, topic_id, description, status)
VALUES(11, 15, 'Since being adopted by the Rocks and after getting his picture posted on the Internet', 'To Do');
INSERT INTO topic_detail
(id, topic_id, description, status)
VALUES(12, 16, 'The Rocks have received calls from Good Morning America', 'To Do');
INSERT INTO topic_detail
(id, topic_id, description, status)
VALUES(13, 17, 'Fox News and The Tyra Banks Show. The moggy’s mugshot has graced the pages of the London Guardian', 'To Do');

INSERT INTO topic_history
(id, topic_id, topic_subject, topic_description, topic_status, create_date_time)
VALUES(13, 12, 'นัดสัมภาษณ์งาน 1', 'รายละเอียดการนัดหมาย', 'To Do', '2024-03-15 00:22:17');
INSERT INTO topic_history
(id, topic_id, topic_subject, topic_description, topic_status, create_date_time)
VALUES(14, 12, 'นัดสัมภาษณ์งาน 1 แก้ไขแล้ว', 'รายละเอียดการนัดหมาย แก้ไขแล้ว', 'In Progress', '2024-03-15 00:23:35');

INSERT INTO comment
(id, create_by_id, create_date_time, description, topic_id, update_date_time)
VALUES(30, 9, '2024-03-15 00:27:52', 'คอมเม้นหน่อยนะครับ', 12, '2024-03-15 00:27:52');
INSERT INTO comment
(id, create_by_id, create_date_time, description, topic_id, update_date_time)
VALUES(31, 1, '2024-03-15 00:28:33', 'ได้เลยครับ แก้ไขนิดนึง', 12, '2024-03-15 00:30:03');
INSERT INTO comment
(id, create_by_id, create_date_time, description, topic_id, update_date_time)
VALUES(33, 1, '2024-03-15 00:45:08', 'When you want to interview', 14, '2024-03-15 00:45:08');
INSERT INTO comment
(id, create_by_id, create_date_time, description, topic_id, update_date_time)
VALUES(34, 1, '2024-03-15 00:59:17', 'When you want to interview2', 14, '2024-03-15 00:59:17');

