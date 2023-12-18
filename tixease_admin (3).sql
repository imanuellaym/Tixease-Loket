-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2023 at 03:15 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tixease_admin`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `username`, `password`) VALUES
(1, 'admin1', 'admin1'),
(2, '2', '2'),
(3, '3', '3'),
(22, '2', '2');

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `tanggal` date NOT NULL,
  `tempat` varchar(255) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `jam` time NOT NULL,
  `kuota` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `name`, `tanggal`, `tempat`, `harga`, `jam`, `kuota`) VALUES
(1, 'Event1', '2023-11-15', 'Tempat1', 50.00, '14:00:00', 990),
(2, 'Event2', '2023-11-20', 'Tempat2', 75.00, '15:30:00', 106),
(5, 'dd', '2022-02-20', 'ee', 1000.00, '11:10:00', 2037),
(6, 'Konser Musik', '2023-11-29', 'Unesa', 2000.00, '12:30:00', 986),
(7, 'd', '2023-03-03', 'd', 10.00, '12:02:00', 10);

-- --------------------------------------------------------

--
-- Table structure for table `loket`
--

CREATE TABLE `loket` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `loket`
--

INSERT INTO `loket` (`id`, `username`, `password`) VALUES
(1, 'loket', 'loket'),
(2, '2', '2'),
(3, '3', '3'),
(22, '2', '2'),
(23, 'loket22', 'loket22');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `id_pengunjung` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `jumlah_tiket` int(11) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `payment` decimal(10,2) NOT NULL,
  `change_amount` decimal(10,2) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `nama_pengunjung` varchar(30) NOT NULL,
  `no_tlp` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `id_pengunjung`, `event_id`, `jumlah_tiket`, `total_amount`, `payment`, `change_amount`, `timestamp`, `nama_pengunjung`, `no_tlp`) VALUES
(1, 22, 1, 2, 100.00, 2.00, -98.00, '2023-11-16 15:39:35', '2', '2'),
(3, 22, 2, 1, 75.00, 2222.00, 2147.00, '2023-11-16 15:57:46', '2', '2'),
(4, 22, 2, 2, 150.00, 22222.00, 22072.00, '2023-11-16 16:00:30', '2', '2'),
(5, 2, 2, 2, 150.00, 1000.00, 850.00, '2023-11-16 16:05:09', '2', '2'),
(6, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:24', '22', '2'),
(7, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:34', '22', '2'),
(8, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:35', '22', '2'),
(9, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:36', '22', '2'),
(10, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:37', '22', '2'),
(11, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:37', '22', '2'),
(12, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:37', '22', '2'),
(13, 2, 2, 2, 150.00, 222.00, 72.00, '2023-11-16 16:23:38', '22', '2'),
(14, 2, 2, 2, 150.00, 222222.00, 222072.00, '2023-11-16 16:29:36', '2', '2'),
(15, 2, 2, 2, 150.00, 2.00, -148.00, '2023-11-16 16:34:28', '2', '2'),
(19, 2, 2, 2, 150.00, 1.00, -149.00, '2023-11-16 16:43:59', '2', '2'),
(22, 22, 5, 4, 4000.00, 3.00, -3997.00, '2023-11-16 16:47:55', '2', '3'),
(23, 2, 5, 4, 4000.00, 2.00, -3998.00, '2023-11-16 16:51:18', '2', '2'),
(24, 2, 5, 2, 2000.00, 2.00, -1998.00, '2023-11-16 16:52:35', '22', '2'),
(25, 22, 5, 22, 22000.00, 11.00, -21989.00, '2023-11-16 16:53:52', '2', '2'),
(27, 2, 5, 2, 2000.00, 2222.00, 222.00, '2023-11-17 15:41:25', '2', '2'),
(28, 12345, 6, 12, 24000.00, 50000.00, 26000.00, '2023-11-19 04:49:04', 'Imanuella Yesha', '0891234556'),
(29, 10, 2, 1, 75.00, 1222.00, 1147.00, '2023-11-28 09:36:24', 'hi', '01'),
(30, 12, 6, 2, 4000.00, 1000.00, -3000.00, '2023-11-28 09:43:05', 'Imanuella', '089'),
(31, 22, 1, 2, 100.00, 222.00, 122.00, '2023-12-04 15:27:21', '222', '22'),
(32, 1, 1, 1, 50.00, 121212.00, 121162.00, '2023-12-05 14:25:24', '1', '1'),
(33, 1, 2, 1, 75.00, 121212.00, 121137.00, '2023-12-05 14:25:24', '1', '1'),
(34, 1, 1, 1, 50.00, 121221.00, 121171.00, '2023-12-05 14:31:31', '1', '1'),
(35, 1, 2, 1, 75.00, 121221.00, 121146.00, '2023-12-05 14:31:31', '1', '1'),
(36, 2, 1, 2, 100.00, 1330.00, 1230.00, '2023-12-05 14:42:22', '2', '2'),
(37, 2, 2, 2, 150.00, 1330.00, 1180.00, '2023-12-05 14:42:22', '2', '2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `loket`
--
ALTER TABLE `loket`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nama_kunci_asing` (`event_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `loket`
--
ALTER TABLE `loket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `nama_kunci_asing` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  ADD CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
