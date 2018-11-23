-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Verzió:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table proba.order: ~4 rows (approximately)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`OrderId`, `BuyerName`, `BuyerEmail`, `OrderDate`, `OrderTotalValue`, `Address`, `PostCode`) VALUES
	(1, 'Kiss Béla', 'kiss.bela@vmi.com', '2018-11-16', 9900, 'Budapest, Könyves Kálmán utca 12.', 1002),
	(2, 'Kovács István', 'kovacs.istvan@akarmi.com', '2018-11-23', 10800, 'Debrecen, Mester utca 6.', 4026),
	(3, 'Szabó János', 'szabo.janos@barmi.hu', '2018-11-23', 10500, 'Balatonlelle, Fő utca 18.', 8965),
	(4, 'Gál Mária', 'gal.maria@vmi.com', '2018-11-16', 17000, 'Harkány, Kölcsey Ferenc tér 4.', 7565);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Dumping data for table proba.order_item: ~5 rows (approximately)
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` (`OrderItemId`, `OrderId`, `SalePrice`, `ShippingPrice`, `TotalItemPrice`, `SKU`, `Status`) VALUES
	(1, 1, 3000, 900, 3900, '12345-AB', 'IN_STOCK'),
	(2, 1, 5000, 1000, 6000, '12346-AC', 'IN_STOCK'),
	(3, 2, 4500, 900, 5400, '12345-AD', 'OUT_OF_STOCK'),
	(4, 3, 9000, 1500, 10500, '12345-AF', 'IN_STOCK'),
	(5, 4, 15000, 2000, 17000, '12345-AG', 'IN_STOCK');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
