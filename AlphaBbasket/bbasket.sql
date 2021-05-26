CREATE DATABASE `bbasket` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
/*TABLA CLIENTE*/
CREATE TABLE `cliente` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRES` varchar(45) NOT NULL,
  `APELLIDOS` varchar(45) NOT NULL,
  `CORREO` varchar(45) NOT NULL,
  `EDAD` varchar(3) NOT NULL,
  `CLAVE` varchar(45) NOT NULL,
  `FECHA_ALTA` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CORREO` (`CORREO`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*Tabla del TRIGGER*/
CREATE TABLE `cliente_delete` (
  `id` int(11) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `edad` varchar(3) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_baja` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*TRIGGER PARA ANTES DE BORRAR UN CLIENTE*/
DROP TRIGGER IF EXISTS `bbasket`.`cliente_BEFORE_DELETE`;

DELIMITER $$
USE `bbasket`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `bbasket`.`cliente_BEFORE_DELETE`
BEFORE DELETE
ON `cliente` FOR EACH ROW
BEGIN
    INSERT INTO cliente_delete(id,nombres,apellidos, correo, edad, clave, fecha_alta, fecha_baja)
    VALUES(OLD.id,OLD.nombres,OLD.apellidos, OLD.correo, OLD.edad, OLD.clave, OLD.fecha_alta, SYSDATE());
    DELETE FROM `bbasket`.`direccion`
	WHERE OLD.correo=direccion.correo;
END$$
DELIMITER ;

/**/
CREATE TABLE `direccion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL,
  `altura` double NOT NULL,
  `correo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  CONSTRAINT `correo` FOREIGN KEY (`correo`) REFERENCES `cliente` (`CORREO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
