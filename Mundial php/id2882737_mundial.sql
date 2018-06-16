-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Czas generowania: 16 Cze 2018, 08:43
-- Wersja serwera: 10.1.31-MariaDB
-- Wersja PHP: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `id2882737_mundial`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Bets`
--

CREATE TABLE `Bets` (
  `id_bet` int(11) NOT NULL,
  `login` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bet_a` int(11) DEFAULT NULL,
  `bet_b` int(11) DEFAULT NULL,
  `id_match` int(11) DEFAULT NULL,
  `Points` int(11) NOT NULL DEFAULT '0',
  `exact_result` int(11) NOT NULL DEFAULT '0',
  `bet_date` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Bets`
--

INSERT INTO `Bets` (`id_bet`, `login`, `bet_a`, `bet_b`, `id_match`, `Points`, `exact_result`, `bet_date`) VALUES
(80, 'Haras', 3, 0, 1, 2, 0, NULL),
(81, 'Beki', 1, 0, 1, 2, 0, NULL),
(82, 'koucz', 1, 0, 1, 2, 0, NULL),
(88, 'Haras', 0, 2, 2, 2, 0, NULL),
(89, 'Beki', 0, 2, 2, 2, 0, NULL),
(90, 'Beki', 2, 1, 8, 0, 0, NULL),
(92, 'Haras', 2, 1, 7, 0, 0, '2018-06-15 16:14:40'),
(118, 'Rękaw', 2, 0, 1, 2, 0, NULL),
(121, 'Rękaw', 1, 2, 2, 2, 0, NULL),
(122, 'Rękaw', 2, 0, 7, 0, 0, '2018-06-15 16:34:10'),
(123, 'Bordi', 1, 2, 2, 2, 0, NULL),
(138, 'AdamM', 2, 0, 1, 2, 0, NULL),
(141, 'AdamM', 0, 2, 2, 2, 0, NULL),
(142, 'AdamM', 1, 1, 7, 0, 0, '2018-06-15 17:28:26'),
(143, 'AdamM', 1, 2, 8, 0, 0, '2018-06-15 15:07:05'),
(144, 'Haras_SW', 2, 1, 7, 0, 0, '2018-06-15 16:14:03'),
(145, 'Bordi', 0, 2, 7, 2, 0, NULL),
(146, 'AdamM', 3, 0, 13, 0, 0, NULL),
(147, 'Haras_SW', 3, 0, 1, 2, 0, NULL),
(148, 'Haras_SW', 0, 2, 2, 2, 0, NULL),
(149, 'Jarek2', 4, 0, 1, 2, 0, NULL),
(150, 'Jarek2', 1, 2, 2, 2, 0, NULL),
(151, 'Jarek2', 1, 0, 7, 0, 0, NULL),
(152, 'Jarek2', 1, 1, 8, 2, 0, NULL),
(153, 'Jarek2', 1, 2, 13, 0, 0, NULL),
(154, 'vardos', 3, 0, 1, 2, 0, NULL),
(155, 'Bordi', 2, 0, 1, 2, 0, NULL),
(157, 'Buczi', 3, 1, 1, 2, 0, NULL),
(158, 'Gelo PrzeeHooy', 3, 1, 1, 2, 0, NULL),
(159, 'Janex', 3, 0, 1, 2, 0, NULL),
(160, 'Gelo PrzeeHooy', 0, 2, 7, 2, 0, NULL),
(161, 'Gelo PrzeeHooy', 1, 3, 2, 2, 0, NULL),
(162, 'Gelo PrzeeHooy', 1, 2, 8, 0, 0, NULL),
(163, 'Gelo PrzeeHooy', 4, 2, 13, 0, 0, NULL),
(164, 'Beki', 2, 0, 7, 0, 0, NULL),
(165, 'Beki', 4, 0, 13, 0, 0, NULL),
(166, 'Romek', 2, 1, 1, 2, 0, NULL),
(167, 'Stefan', 2, 1, 1, 2, 0, NULL),
(168, 'Stefan', 1, 1, 2, 0, 0, NULL),
(169, 'Stefan', 2, 1, 7, 0, 0, NULL),
(170, 'Stefan', 1, 2, 8, 0, 0, NULL),
(171, 'Stefan', 3, 0, 13, 0, 0, NULL),
(172, 'jordanrafalm', 2, 1, 1, 2, 0, NULL),
(173, 'jordanrafalm', 0, 3, 2, 2, 0, NULL),
(174, 'AdamD', 3, 1, 1, 2, 0, NULL),
(175, 'Escobar', 3, 0, 1, 2, 0, NULL),
(176, 'vardos', 0, 0, 8, 2, 0, NULL),
(177, 'AdamD', 1, 2, 2, 2, 0, NULL),
(178, 'AdamD', 1, 0, 7, 0, 0, NULL),
(179, 'AdamD', 1, 1, 8, 2, 0, NULL),
(180, 'AdamD', 1, 0, 13, 0, 0, NULL),
(181, 'AdamD', 2, 0, 19, 0, 0, NULL),
(182, 'koucz', 1, 2, 2, 2, 0, NULL),
(183, 'koucz', 1, 0, 7, 0, 0, NULL),
(184, 'koucz', 2, 2, 8, 2, 0, NULL),
(185, 'Haras_SW', 0, 1, 8, 0, 0, NULL),
(186, 'Haras', 1, 2, 8, 0, 0, NULL),
(187, 'Gelo PrzeeHooy', 5, 1, 19, 0, 0, NULL),
(188, 'Janex', 0, 2, 2, 2, 0, NULL),
(189, 'Janex', 0, 0, 7, 0, 0, NULL),
(190, 'Janex', 1, 2, 8, 0, 0, NULL),
(191, 'vardos', 1, 3, 2, 2, 0, NULL),
(192, 'vardos', 0, 1, 7, 5, 1, NULL),
(193, 'Romek', 2, 2, 2, 0, 0, NULL),
(194, 'Romek', 1, 1, 7, 0, 0, NULL),
(195, 'Romek', 2, 1, 8, 0, 0, NULL),
(196, 'Escobar', 2, 2, 2, 0, 0, NULL),
(197, 'Escobar', 1, 2, 7, 2, 0, NULL),
(198, 'Escobar', 2, 4, 8, 0, 0, NULL),
(199, 'Bordi', 1, 3, 8, 0, 0, NULL),
(200, 'Kapi', 0, 1, 2, 5, 1, NULL),
(204, 'jordanrafalm', 2, 1, 7, 0, 0, NULL),
(205, 'jordanrafalm', 2, 3, 8, 0, 0, NULL),
(206, 'vardos', 2, 0, 13, 0, 0, NULL),
(207, 'vardos', 2, 0, 19, 0, 0, NULL),
(208, 'vardos', 1, 0, 14, 0, 0, NULL),
(209, 'vardos', 2, 0, 20, 0, 0, NULL),
(210, 'vardos', 2, 0, 20, 0, 0, NULL),
(211, 'Całek', 0, 4, 2, 2, 0, NULL),
(1000, 'Buczi', 2, 3, 2, 2, 0, NULL),
(1001, 'Buczi', 2, 1, 7, 0, 0, NULL),
(1002, 'Buczi', 3, 2, 8, 0, 0, NULL),
(1003, 'Buczi', 4, 1, 13, 0, 0, NULL),
(1004, 'Buczi', 3, 0, 19, 0, 0, NULL),
(1005, 'Buczi', 1, 2, 14, 0, 0, NULL),
(1006, 'AdamD', 0, 1, 14, 0, 0, NULL),
(1007, 'AdamD', 3, 0, 20, 0, 0, NULL),
(1008, 'AdamD', 0, 0, 25, 0, 0, NULL),
(1009, 'AdamD', 2, 0, 31, 0, 0, NULL),
(1010, 'AdamD', 3, 0, 26, 0, 0, NULL),
(1011, 'AdamD', 2, 0, 32, 0, 0, NULL),
(1012, 'AdamD', 1, 0, 37, 0, 0, NULL),
(1013, 'AdamD', 0, 3, 38, 0, 0, NULL),
(1015, 'Haras_SW', 3, 0, 13, 0, 0, '2018-06-15 14:50:13'),
(1016, 'AdamM', 4, 0, 19, 0, 0, '2018-06-15 14:51:16'),
(1017, 'AdamM', 1, 2, 14, 0, 0, '2018-06-15 14:51:31'),
(1018, 'AdamM', 1, 3, 44, 0, 0, '2018-06-15 14:51:51'),
(1019, 'AdamM', 1, 3, 46, 0, 0, '2018-06-15 14:53:10'),
(1020, 'AdamM', 0, 2, 47, 0, 0, '2018-06-15 14:53:25'),
(1021, 'koucz', 3, 0, 13, 0, 0, '2018-06-15 16:00:25'),
(1022, 'koucz', 2, 0, 19, 0, 0, '2018-06-15 16:01:23'),
(1023, 'koucz', 0, 0, 14, 0, 0, '2018-06-15 16:01:53'),
(1024, 'koucz', 3, 1, 20, 0, 0, '2018-06-15 16:02:50'),
(1025, 'Rękaw', 0, 3, 8, 0, 0, '2018-06-15 16:34:38'),
(1026, 'Beki', 4, 0, 19, 0, 0, '2018-06-15 18:40:51'),
(1027, 'Beki', 0, 2, 14, 0, 0, '2018-06-15 18:41:16'),
(1028, 'Bordi', 3, 0, 13, 0, 0, '2018-06-15 18:51:25'),
(1029, 'Haras', 2, 0, 13, 0, 0, '2018-06-15 21:56:44'),
(1030, 'Rękaw', 2, 0, 19, 0, 0, '2018-06-15 22:08:26'),
(1031, 'Rękaw', 3, 0, 13, 0, 0, '2018-06-15 22:08:46'),
(1032, 'Bordi', 3, 1, 19, 0, 0, '2018-06-15 23:55:01'),
(1033, 'Bordi', 1, 1, 14, 0, 0, '2018-06-15 23:03:46'),
(1034, 'Bordi', 2, 1, 20, 0, 0, '2018-06-15 23:55:19'),
(1035, 'vardos', 2, 0, 31, 0, 0, '2018-06-16 00:36:32'),
(1036, 'vardos', 1, 2, 25, 0, 0, '2018-06-16 00:36:39'),
(1037, 'vardos', 3, 0, 26, 0, 0, '2018-06-16 00:36:48'),
(1038, 'Buczi', 2, 0, 20, 0, 0, '2018-06-16 06:36:01'),
(1039, 'Buczi', 1, 2, 25, 0, 0, '2018-06-16 06:36:31'),
(1040, 'Janex', 2, 0, 13, 0, 0, '2018-06-16 08:27:31'),
(1041, 'Janex', 2, 0, 19, 0, 0, '2018-06-16 08:27:41'),
(1042, 'Janex', 1, 1, 14, 0, 0, '2018-06-16 08:27:51'),
(1043, 'Janex', 0, 0, 20, 0, 0, '2018-06-16 08:28:02'),
(1044, 'Janex', 0, 3, 25, 0, 0, '2018-06-16 08:28:17');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_A`
--

CREATE TABLE `Group_A` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_A`
--

INSERT INTO `Group_A` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Russia', 1, 5, 0, '5', 3),
(2, 'Saudi Arabia', 1, 0, 5, '-5', 0),
(3, 'Egypt', 1, 0, 1, '-1', 0),
(4, 'Uruguay', 1, 1, 0, '1', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_B`
--

CREATE TABLE `Group_B` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_B`
--

INSERT INTO `Group_B` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Portugal', 0, 0, 0, '0', 0),
(2, 'Spain', 0, 0, 0, '0', 0),
(3, 'Marocco', 0, 0, 0, '0', 0),
(4, 'Iran', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_C`
--

CREATE TABLE `Group_C` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_C`
--

INSERT INTO `Group_C` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'France', 0, 0, 0, '0', 0),
(2, 'Australia', 0, 0, 0, '0', 0),
(3, 'Peru', 0, 0, 0, '0', 0),
(4, 'Denmark', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_D`
--

CREATE TABLE `Group_D` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_D`
--

INSERT INTO `Group_D` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Argentina', 0, 0, 0, '0', 0),
(2, 'Iceland', 0, 0, 0, '0', 0),
(3, 'Croatia', 0, 0, 0, '0', 0),
(4, 'Nigeria', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_E`
--

CREATE TABLE `Group_E` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_E`
--

INSERT INTO `Group_E` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Brazil', 0, 0, 0, '0', 0),
(2, 'Switzerland', 0, 0, 0, '0', 0),
(3, 'Costa Rica', 0, 0, 0, '0', 0),
(4, 'Serbia', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_F`
--

CREATE TABLE `Group_F` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_F`
--

INSERT INTO `Group_F` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Germany', 0, 0, 0, '0', 0),
(2, 'Mexico', 0, 0, 0, '0', 0),
(3, 'Sweden', 0, 0, 0, '0', 0),
(4, 'South Korea', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_G`
--

CREATE TABLE `Group_G` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_G`
--

INSERT INTO `Group_G` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Belgium', 0, 0, 0, '0', 0),
(2, 'Panama', 0, 0, 0, '0', 0),
(3, 'Tunisia', 0, 0, 0, '0', 0),
(4, 'England', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Group_H`
--

CREATE TABLE `Group_H` (
  `id_group` int(11) NOT NULL,
  `team` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matches` int(11) DEFAULT NULL,
  `goals_scored` int(11) DEFAULT NULL,
  `goals_lost` int(11) DEFAULT NULL,
  `balance2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Group_H`
--

INSERT INTO `Group_H` (`id_group`, `team`, `matches`, `goals_scored`, `goals_lost`, `balance2`, `points`) VALUES
(1, 'Poland', 0, 0, 0, '0', 0),
(2, 'Senegal', 0, 0, 0, '0', 0),
(3, 'Columbia', 0, 0, 0, '0', 0),
(4, 'Japan', 0, 0, 0, '0', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Matches`
--

CREATE TABLE `Matches` (
  `id_match` int(11) NOT NULL,
  `team_a` int(11) DEFAULT NULL,
  `team_b` int(11) DEFAULT NULL,
  `result_a` int(11) DEFAULT NULL,
  `result_b` int(11) DEFAULT NULL,
  `date_match` date DEFAULT NULL,
  `time_match` time DEFAULT NULL,
  `stage` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Matches`
--

INSERT INTO `Matches` (`id_match`, `team_a`, `team_b`, `result_a`, `result_b`, `date_match`, `time_match`, `stage`) VALUES
(1, 1, 2, 5, 0, '2018-06-14', '17:00:00', 'group'),
(2, 3, 4, 0, 1, '2018-06-15', '14:00:00', 'group'),
(3, 1, 3, NULL, NULL, '2018-06-19', '20:00:00', 'group'),
(4, 4, 2, NULL, NULL, '2018-06-20', '17:00:00', 'group'),
(5, 4, 1, NULL, NULL, '2018-06-25', '16:00:00', 'group'),
(6, 2, 3, NULL, NULL, '2018-06-25', '16:00:00', 'group'),
(7, 7, 8, 0, 1, '2018-06-15', '17:00:00', 'group'),
(8, 5, 6, 3, 3, '2018-06-15', '20:00:00', 'group'),
(9, 5, 7, NULL, NULL, '2018-06-20', '14:00:00', 'group'),
(10, 8, 6, NULL, NULL, '2018-06-20', '20:00:00', 'group'),
(11, 8, 5, NULL, NULL, '2018-06-25', '20:00:00', 'group'),
(12, 6, 7, NULL, NULL, '2018-06-25', '20:00:00', 'group'),
(13, 9, 10, NULL, NULL, '2018-06-16', '12:00:00', 'group'),
(14, 11, 12, NULL, NULL, '2018-06-16', '18:00:00', 'group'),
(15, 9, 11, NULL, NULL, '2018-06-21', '14:00:00', 'group'),
(16, 12, 10, NULL, NULL, '2018-06-21', '17:00:00', 'group'),
(17, 12, 9, NULL, NULL, '2018-06-26', '16:00:00', 'group'),
(18, 10, 11, NULL, NULL, '2018-06-26', '16:00:00', 'group'),
(19, 13, 14, NULL, NULL, '2018-06-16', '15:00:00', 'group'),
(20, 15, 16, NULL, NULL, '2018-06-16', '21:00:00', 'group'),
(21, 13, 15, NULL, NULL, '2018-06-21', '20:00:00', 'group'),
(22, 16, 14, NULL, NULL, '2018-06-22', '17:00:00', 'group'),
(23, 16, 13, NULL, NULL, '2018-06-26', '20:00:00', 'group'),
(24, 14, 15, NULL, NULL, '2018-06-26', '20:00:00', 'group'),
(25, 19, 20, NULL, NULL, '2018-06-17', '14:00:00', 'group'),
(26, 17, 18, NULL, NULL, '2018-06-17', '20:00:00', 'group'),
(27, 17, 19, NULL, NULL, '2018-06-22', '14:00:00', 'group'),
(28, 20, 18, NULL, NULL, '2018-06-22', '20:00:00', 'group'),
(29, 20, 17, NULL, NULL, '2018-06-27', '20:00:00', 'group'),
(30, 18, 19, NULL, NULL, '2018-06-27', '20:00:00', 'group'),
(31, 21, 22, NULL, NULL, '2018-06-17', '17:00:00', 'group'),
(32, 23, 24, NULL, NULL, '2018-06-18', '14:00:00', 'group'),
(33, 21, 23, NULL, NULL, '2018-06-23', '17:00:00', 'group'),
(34, 24, 22, NULL, NULL, '2018-06-23', '20:00:00', 'group'),
(35, 24, 21, NULL, NULL, '2018-06-27', '16:00:00', 'group'),
(36, 22, 23, NULL, NULL, '2018-06-27', '16:00:00', 'group'),
(37, 25, 26, NULL, NULL, '2018-06-18', '17:00:00', 'group'),
(38, 27, 28, NULL, NULL, '2018-06-18', '20:00:00', 'group'),
(39, 25, 27, NULL, NULL, '2018-06-23', '14:00:00', 'group'),
(40, 28, 26, NULL, NULL, '2018-06-24', '14:00:00', 'group'),
(41, 28, 25, NULL, NULL, '2018-06-28', '20:00:00', 'group'),
(42, 26, 27, NULL, NULL, '2018-06-28', '20:00:00', 'group'),
(43, 31, 32, NULL, NULL, '2018-06-19', '14:00:00', 'group'),
(44, 29, 30, NULL, NULL, '2018-06-19', '17:00:00', 'group'),
(45, 32, 30, NULL, NULL, '2018-06-24', '17:00:00', 'group'),
(46, 29, 31, NULL, NULL, '2018-06-24', '20:00:00', 'group'),
(47, 32, 29, NULL, NULL, '2018-06-28', '16:00:00', 'group'),
(48, 30, 31, NULL, NULL, '2018-06-28', '16:00:00', 'group');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Teams`
--

CREATE TABLE `Teams` (
  `id_team` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_name` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Teams`
--

INSERT INTO `Teams` (`id_team`, `name`, `group_name`, `points`) VALUES
(1, 'Russia', 'A', 0),
(2, 'Saudi Arabia', 'A', 0),
(3, 'Egypt', 'A', 0),
(4, 'Uruguay', 'A', 0),
(5, 'Portugal', 'B', 0),
(6, 'Spain', 'B', 0),
(7, 'Marocco', 'B', 0),
(8, 'Iran', 'B', 0),
(9, 'France', 'C', 0),
(10, 'Australia', 'C', 0),
(11, 'Peru', 'C', 0),
(12, 'Denmark', 'C', 0),
(13, 'Argentina', 'D', 0),
(14, 'Iceland', 'D', 0),
(15, 'Croatia', 'D', 0),
(16, 'Nigeria', 'D', 0),
(17, 'Brazil', 'E', 0),
(18, 'Switzerland', 'E', 0),
(19, 'Costa Rica', 'E', 0),
(20, 'Serbia', 'E', 0),
(21, 'Germany', 'F', 0),
(22, 'Mexico', 'F', 0),
(23, 'Sweden', 'F', 0),
(24, 'South Korea', 'F', 0),
(25, 'Belgium', 'G', 0),
(26, 'Panama', 'G', 0),
(27, 'Tunisia', 'G', 0),
(28, 'England', 'G', 0),
(29, 'Poland', 'H', 0),
(30, 'Senegal', 'H', 0),
(31, 'Columbia', 'H', 0),
(32, 'Japan', 'H', 0),
(33, 'Chile', NULL, NULL),
(34, 'Lithuania', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Users`
--

CREATE TABLE `Users` (
  `login` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `group_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Users`
--

INSERT INTO `Users` (`login`, `password`, `group_name`) VALUES
('AdamD', 'sam10cup', 'MQA'),
('AdamM', 'Samsung44', 'MQA'),
('Beki', 'dupa123', 'Twierdza'),
('Bordi', 'samsung1@', 'MQA'),
('Buczi', 'qaz123qaz', 'MQA'),
('Całek', 'qwerty1', 'MQA'),
('Escobar', 'abcd1234', 'MQA'),
('Gelo PrzeeHooy', 'dupa1dupa1', 'Twierdza'),
('Haras', 'sarpwxm2', 'Twierdza'),
('Haras_SW', 'sarpwxm2', 'MQA'),
('Janex', 'janusz8976', 'Twierdza'),
('Jarek2', 'sam1928.', 'MQA'),
('jordanrafalm', 'Rafal765.', 'MQA'),
('Kamil', 'Qwerty1', 'MQA'),
('Kapi', 'Testro12!@', 'MQA'),
('koucz', 'harasharas2018', 'Twierdza'),
('Rękaw', '1111', 'MQA'),
('Romek', 'dupa1dupa1', 'Twierdza'),
('Stefan', 'lukasz16', 'Twierdza'),
('vardos', 'brother350', 'Twierdza');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `Bets`
--
ALTER TABLE `Bets`
  ADD PRIMARY KEY (`id_bet`),
  ADD KEY `id_match` (`id_match`),
  ADD KEY `login` (`login`);

--
-- Indeksy dla tabeli `Group_A`
--
ALTER TABLE `Group_A`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_B`
--
ALTER TABLE `Group_B`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_C`
--
ALTER TABLE `Group_C`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_D`
--
ALTER TABLE `Group_D`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_E`
--
ALTER TABLE `Group_E`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_F`
--
ALTER TABLE `Group_F`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_G`
--
ALTER TABLE `Group_G`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Group_H`
--
ALTER TABLE `Group_H`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `Matches`
--
ALTER TABLE `Matches`
  ADD PRIMARY KEY (`id_match`),
  ADD KEY `team_a` (`team_a`),
  ADD KEY `team_b` (`team_b`);

--
-- Indeksy dla tabeli `Teams`
--
ALTER TABLE `Teams`
  ADD PRIMARY KEY (`id_team`);

--
-- Indeksy dla tabeli `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `Bets`
--
ALTER TABLE `Bets`
  MODIFY `id_bet` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1045;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `Bets`
--
ALTER TABLE `Bets`
  ADD CONSTRAINT `Bets_ibfk_1` FOREIGN KEY (`id_match`) REFERENCES `Matches` (`id_match`),
  ADD CONSTRAINT `Bets_ibfk_2` FOREIGN KEY (`login`) REFERENCES `Users` (`login`);

--
-- Ograniczenia dla tabeli `Matches`
--
ALTER TABLE `Matches`
  ADD CONSTRAINT `Matches_ibfk_1` FOREIGN KEY (`team_a`) REFERENCES `Teams` (`id_team`),
  ADD CONSTRAINT `Matches_ibfk_2` FOREIGN KEY (`team_b`) REFERENCES `Teams` (`id_team`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
