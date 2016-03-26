-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 05, 2015 at 10:36 AM
-- Server version: 5.6.25-log
-- PHP Version: 5.5.25-pl1-gentoo

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `mbbx9mg3`
--

-- --------------------------------------------------------

--
-- Table structure for table `3YP_ITEMS`
--

CREATE TABLE IF NOT EXISTS `3YP_ITEMS` (
  `ID` bigint(20) NOT NULL,
  `NAME` char(60) COLLATE utf8_unicode_ci NOT NULL,
  `PRICE` decimal(19,4) NOT NULL,
  `QUANTITY` int(11) unsigned NOT NULL,
  `STOCK_COUNT_ON` tinyint(1) NOT NULL,
  `NEED_AGE_CHECK` tinyint(1) NOT NULL DEFAULT '0',
  `FOOD_OR_DRINK` enum('FOOD','DRINK','','') COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `3YP_ITEMS`
--

INSERT INTO `3YP_ITEMS` (`ID`, `NAME`, `PRICE`, `QUANTITY`, `STOCK_COUNT_ON`, `NEED_AGE_CHECK`, `FOOD_OR_DRINK`) VALUES
(3, '14oz Cola', '1.5000', 100, 0, 0, 'DRINK'),
(4, '14oz Diet Cola', '1.5000', 100, 0, 0, 'DRINK'),
(5, '4oz Dash Cola', '0.3000', 1000, 0, 0, 'DRINK'),
(6, '4oz Dash Diet Cola', '0.3000', 1000, 0, 0, 'DRINK'),
(7, 'Pint Cola', '2.0000', 100, 0, 0, 'DRINK'),
(8, 'Pint Diet Cola', '2.0000', 100, 0, 0, 'DRINK'),
(19, 'Cheese Pie', '6.9500', 5509, 1, 0, 'FOOD'),
(27, 'Beef Burger', '5.9000', 100, 1, 0, 'FOOD'),
(29, 'Beef Lasagne', '8.9500', 150, 0, 0, 'FOOD');

-- --------------------------------------------------------

--
-- Table structure for table `3YP_MENU_PAGES`
--

CREATE TABLE IF NOT EXISTS `3YP_MENU_PAGES` (
  `NAME` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `PARENT_PAGE_ID` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BUTTON_NAME` varchar(25) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `3YP_MENU_PAGES`
--

INSERT INTO `3YP_MENU_PAGES` (`NAME`, `PARENT_PAGE_ID`, `BUTTON_NAME`) VALUES
('BURGER_PAGE', 'FOOD_PAGE', 'Burgers'),
('FOOD_PAGE', 'MAIN_PAGE', 'Food Menu'),
('GRILLS_PAGE', 'FOOD_PAGE', 'Grills'),
('MAIN_PAGE', NULL, 'Main Page'),
('MIN_MIX_PAGE', 'MAIN_PAGE', 'Minerals/Mixers'),
('TRAGO_PAGE', 'MAIN_PAGE', 'Spirits/Liqueurs');

-- --------------------------------------------------------

--
-- Table structure for table `3YP_POS_IN_MENU`
--

CREATE TABLE IF NOT EXISTS `3YP_POS_IN_MENU` (
  `ID` bigint(20) NOT NULL,
  `LOCATION` varchar(25) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `3YP_POS_IN_MENU`
--

INSERT INTO `3YP_POS_IN_MENU` (`ID`, `LOCATION`) VALUES
(3, 'MAIN_PAGE'),
(3, 'MIN_MIX_PAGE'),
(4, 'MIN_MIX_PAGE'),
(5, 'MIN_MIX_PAGE'),
(6, 'MIN_MIX_PAGE'),
(7, 'MIN_MIX_PAGE'),
(8, 'MIN_MIX_PAGE'),
(19, 'FOOD_PAGE'),
(27, 'BURGER_PAGE'),
(29, 'FOOD_PAGE');

-- --------------------------------------------------------

--
-- Table structure for table `3YP_TAKINGS`
--

CREATE TABLE IF NOT EXISTS `3YP_TAKINGS` (
  `DATE` date NOT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `3YP_TAKINGS`
--

INSERT INTO `3YP_TAKINGS` (`DATE`, `AMOUNT`) VALUES
('2015-03-10', '0'),
('2015-03-11', '0'),
('2015-04-28', '0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `3YP_ITEMS`
--
ALTER TABLE `3YP_ITEMS`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `NAME` (`NAME`),
  ADD KEY `ID` (`ID`);

--
-- Indexes for table `3YP_MENU_PAGES`
--
ALTER TABLE `3YP_MENU_PAGES`
  ADD PRIMARY KEY (`NAME`),
  ADD KEY `PARENT_PAGE_ID` (`PARENT_PAGE_ID`);

--
-- Indexes for table `3YP_POS_IN_MENU`
--
ALTER TABLE `3YP_POS_IN_MENU`
  ADD PRIMARY KEY (`ID`,`LOCATION`),
  ADD KEY `ID` (`ID`),
  ADD KEY `LOCATION` (`LOCATION`);

--
-- Indexes for table `3YP_TAKINGS`
--
ALTER TABLE `3YP_TAKINGS`
  ADD PRIMARY KEY (`DATE`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `3YP_ITEMS`
--
ALTER TABLE `3YP_ITEMS`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `3YP_MENU_PAGES`
--
ALTER TABLE `3YP_MENU_PAGES`
  ADD CONSTRAINT `3YP_MENU_PAGES_ibfk_1` FOREIGN KEY (`PARENT_PAGE_ID`) REFERENCES `3YP_MENU_PAGES` (`NAME`);

--
-- Constraints for table `3YP_POS_IN_MENU`
--
ALTER TABLE `3YP_POS_IN_MENU`
  ADD CONSTRAINT `3YP_POS_IN_MENU_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `3YP_ITEMS` (`ID`) ON UPDATE NO ACTION,
  ADD CONSTRAINT `3YP_POS_IN_MENU_ibfk_2` FOREIGN KEY (`LOCATION`) REFERENCES `3YP_MENU_PAGES` (`NAME`);
