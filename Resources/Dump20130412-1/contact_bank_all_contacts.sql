CREATE TABLE IF NOT EXISTS `all_contacts`
 ( 
`id` BIGINT unsigned NOT NULL AUTO_INCREMENT, 
`name` VARCHAR(255) NOT NULL, 
`position` VARCHAR(255), 
`organization` VARCHAR(255), 
`address` TEXT, 
`country` VARCHAR(255), 
`email` VARCHAR(255), 
`phone` BIGINT unsigned NOT NULL, 
`projects` TEXT, 
`groups` TEXT,
PRIMARY KEY (`id`)) 
ENGINE=MyISAM DEFAULT CHARSET=utf8; 

INSERT INTO `all_contacts` VALUES  
('1', 'Aniekan Akai', 'Software Developer', 'Intel Corporation', '7 Alfred Rewane Road, Ikoyi', 'Nigeria', 'aeakai@intel.com', '2348055384116', 'ContactBank, COP Program', 'Intel Staff'), 
('2', 'Olubunmi Ekundare', 'Country Manager', 'Intel Corporation', '7 Alfred Rewane Road, Ikoyi', 'Nigeria', 'olubunmi.ekundare@intel.com', '2348055455900', 'COP Program', 'Intel Staff'), 
('3', 'Tony Egbuna', 'Account Manager', 'AIESEC', '23 Solar street, Scarborough', 'Canada', 'egbunatony@aiesec.com', '6479880189', 'COP Program', 'Marketing'), 
('4', 'Banky W', 'Promoter', ' ', '21 Banana Island, Lagos', 'Nigeria', 'bw@coson.com.ng', '2348077499300', 'COP Program', 'Artist');