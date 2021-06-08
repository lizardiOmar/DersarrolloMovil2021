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
DROP TRIGGER IF EXISTS `bbasket`.`cliente_BEFORE_DELETE`;

DELIMITER $$
CREATE TRIGGER `cliente_BEFORE_DELETE`
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

CREATE TABLE categoria (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45)UNIQUE NOT NULL,
  descripcion TEXT NOT NULL COMMENT 'Breve descripción relacionada a los productos de esta categoría.',
  PRIMARY KEY (`id`));
  
  CREATE TABLE `marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) UNIQUE NOT NULL ,
  `descripcion` TEXT NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `producto` (
  `idproducto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `tamano` VARCHAR(45) NOT NULL,
  `idCategoria` INT NOT NULL,
  `idMarca` INT NOT NULL,
  PRIMARY KEY (`idproducto`),
  CONSTRAINT `idCategoria`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `categoria` (`id`),
  CONSTRAINT `idMarca`
    FOREIGN KEY (`idMarca`)
    REFERENCES `marca` (`id`));
	
	CREATE TABLE `tienda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `horaApertura` VARCHAR(45) NOT NULL,
  `horaCierre` VARCHAR(45) NOT NULL,
  `idCliente` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idCliente`
    FOREIGN KEY (`idCliente`)
    REFERENCES `cliente` (`ID`));
	
	
	CREATE TABLE `oferta` (
  `idOferta` INT NOT NULL AUTO_INCREMENT,
  `fecha_inicio` VARCHAR(45) NOT NULL,
  `fecha_fin` VARCHAR(45) NOT NULL,
  `precio` VARCHAR(45) NOT NULL,
  `idTienda` INT NOT NULL,
  `idProducto` INT NOT NULL,
  PRIMARY KEY (`idOferta`),
  CONSTRAINT `idTienda`
    FOREIGN KEY (`idTienda`)
    REFERENCES `tienda` (`id`),
  CONSTRAINT `idProdiucto`
    FOREIGN KEY (`idProducto`)
    REFERENCES `producto` (`idproducto`));
CREATE TABLE `consulta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `idCliente` INT NOT NULL,
  `idOferta` INT NOT NULL,
  `comentarios` TEXT NULL,
  `calificacion` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idCliente_c`
    FOREIGN KEY (`idCliente`)
    REFERENCES `cliente` (`ID`),
  CONSTRAINT `idOferta_c`
    FOREIGN KEY (`idOferta`)
    REFERENCES `oferta` (`idOferta`));
	
	CREATE TABLE `direccion_tienda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL,
  `altura` double NOT NULL,
  `idTienda` int UNIQUE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idTienda_d` FOREIGN KEY (`idTienda`) REFERENCES `tienda` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE VIEW `all_ofertas` AS
    SELECT 
        `oferta`.`idOferta` AS `ID`,
        `oferta`.`fecha_fin` AS `VIGENCIA`,
        `oferta`.`precio` AS `PRECIO`,
        `producto`.`nombre` AS `PRODUCTO`,
        `producto`.`tamano` AS `SIZE`,
        `tienda`.`id` AS `ID_TIENDA`,
        `tienda`.`nombre` AS `TIENDA`,
        `direccion_tienda`.`latitud` AS `LATITUD`,
        `direccion_tienda`.`longitud` AS `LONGITUD`,
        `categoria`.`nombre` AS `CATEGORIA`,
        `marca`.`nombre` AS `MARCA`
    FROM
        (((((`tienda`
        JOIN `oferta`)
        JOIN `producto`)
        JOIN `categoria`)
        JOIN `marca`)
        JOIN `direccion_tienda`)
    WHERE
        `oferta`.`idTienda` = `tienda`.`id`
            AND `oferta`.`idProducto` = `producto`.`idproducto`
            AND `producto`.`idCategoria` = `categoria`.`id`
            AND `producto`.`idMarca` = `marca`.`id`
            AND `tienda`.`id` = `direccion_tienda`.`idTienda`;
