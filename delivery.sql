-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-04-2025 a las 06:53:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `delivery`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carritos`
--

CREATE TABLE `carritos` (
  `idCarrito` int(11) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `preciounitario` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carritos`
--

INSERT INTO `carritos` (`idCarrito`, `idUsuario`, `idProducto`, `cantidad`, `preciounitario`, `subtotal`, `fechaCreacion`) VALUES
(10, 11, 3, 1, 190, 190, '2025-04-06 20:22:21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `idCategoria` int(11) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`idCategoria`, `categoria`) VALUES
(1, 'Pizzas'),
(2, 'Hamburguesas y Pollo'),
(3, 'Subs y Sándwiches'),
(4, 'Tex-Mex'),
(5, 'Platos Fuertes'),
(6, 'Postres y Donas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones`
--

CREATE TABLE `direcciones` (
  `idDireccion` int(11) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `idLugar` int(11) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idEmpresa` int(11) DEFAULT NULL,
  `ubicacion` point NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `direcciones`
--

INSERT INTO `direcciones` (`idDireccion`, `direccion`, `descripcion`, `idLugar`, `idUsuario`, `idEmpresa`, `ubicacion`) VALUES
(1, 'Boulevard Morazán, Col. Palmira', 'Sucursal principal Little Caesars', 24, NULL, 1, 0x000000000101000000956588635dcc55c08fc2f5285cef2b40),
(2, 'Avenida La Paz, Frente a La Ceiba', 'Sucursal Pizza Hut', 24, NULL, 2, 0x000000000101000000b84082e2c7cc55c058a835cd3bee2b40),
(3, 'Boulevard Suyapa, Col. Alameda', 'Sucursal KFC', 24, NULL, 3, 0x0000000001010000000e2db29defcb55c05bb1bfec9efc2b40),
(4, 'Calle 5, Col. La Joya', 'Sucursal McDonald\'s', 24, NULL, 4, 0x0000000001010000004850fc1873cb55c0bc74931804f62b40),
(5, 'Avenida San Juan, Col. Miraflores', 'Sucursal Subway', 24, NULL, 5, 0x000000000101000000aa8251499dcc55c0f31fd26f5fe72b40),
(6, 'Avenida Juan Pablo II, Col. El Trapiche', 'Sucursal Domino\'s Pizza', 24, NULL, 6, 0x00000000010100000037894160e5cc55c057ec2fbb27ef2b40),
(7, 'Calle Los Arcos, Col. La Puerta', 'Sucursal Papa John\'s', 24, NULL, 7, 0x000000000101000000c1caa145b6cb55c0857cd0b359f52b40),
(8, 'Avenida Las Américas, Col. Las Palmas', 'Sucursal Taco Bell', 24, NULL, 8, 0x000000000101000000c66d3480b7cc55c055302aa913f02b40),
(9, 'Avenida La Cumbre, Col. Colón', 'Sucursal Denny\'s', 24, NULL, 9, 0x000000000101000000f163cc5d4bcc55c04ed1915cfee32b40),
(10, 'Avenida 10 de Septiembre, Col. Zacatecoluca', 'Sucursal Burger King', 24, NULL, 10, 0x000000000101000000c139234a7bcb55c026e4839ecdea2b40),
(11, 'Calle Pinares, Col. El Cacao', 'Sucursal Wendy\'s', 24, NULL, 11, 0x000000000101000000c7bab88d06cc55c0b30c71ac8bfb2b40),
(12, 'Boulevard Los Próceres, Col. Lempira', 'Sucursal Chili\'s', 24, NULL, 12, 0x000000000101000000e9263108accc55c0e561a1d634ef2b40),
(13, 'Calle de la Loma, Col. Miramar', 'Sucursal Hardee\'s', 24, NULL, 13, 0x0000000001010000004faf946588cb55c0c1a8a44e40f32b40),
(14, 'Boulevard Santa Fe, Col. El Pedregal', 'Sucursal Popeyes', 24, NULL, 14, 0x0000000001010000005305a3923acd55c0d578e92631e82b40),
(15, 'Calle Los Laureles, Col. Santa Rosa', 'Sucursal Shake Shack', 24, NULL, 15, 0x0000000001010000001b0de02d90cc55c009f9a067b3ea2b40),
(16, 'Avenida Las Maderas, Col. Montecillos', 'Sucursal Pizza Factory', 24, NULL, 16, 0x000000000101000000780b24287ecc55c0b6f3fdd478e92b40),
(17, 'Avenida Principal, Col. Los Andes', 'Sucursal Wingstop', 24, NULL, 17, 0x000000000101000000fe43faedebcc55c01ff46c567dee2b40),
(18, 'Calle La Ceiba, Col. El Roble', 'Sucursal Krispy Kreme', 24, NULL, 18, 0x0000000001010000008048bf7d1dcc55c082734694f6e62b40),
(19, 'Avenida México, Col. Miraflor', 'Sucursal Five Guys', 24, NULL, 19, 0x0000000001010000005b423ee8d9cc55c01c7c613255f02b40),
(20, 'Avenida 1 de Mayo, Col. Los Pinos', 'Sucursal Jersey Mike\'s', 24, NULL, 20, 0x000000000101000000151dc9e53fcc55c0226c787aa5ec2b40),
(31, 'Honduras Medical Center', 'Trabajo', 24, 2, NULL, 0x0000000001010000009f2876592dcc55c0064fd7c9cf302c40),
(32, 'Calpules, Tegucigalpa', 'Mi Casa', 24, 2, NULL, 0x0000000001010000001952ea37b8cc55c096253acb2c1a2c40),
(33, 'Calpules', 'Casa', 24, 11, NULL, 0x0000000001010000001952ea37b8cc55c096253acb2c1a2c40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `idEmpresa` int(11) NOT NULL,
  `idUsuarioAdministrador` int(11) NOT NULL,
  `empresa` varchar(255) DEFAULT NULL,
  `rtn` varchar(255) DEFAULT NULL,
  `costoEnvio` double NOT NULL DEFAULT 0,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empresas`
--

INSERT INTO `empresas` (`idEmpresa`, `idUsuarioAdministrador`, `empresa`, `rtn`, `costoEnvio`, `imagen`) VALUES
(1, 1, 'Little Caesars', '0201-0201-0201', 50, NULL),
(2, 1, 'Pizza Hut', '0101-0101-0101', 60, NULL),
(3, 8, 'KFC', '0301-0301-0301', 25, NULL),
(4, 1, 'McDonald\'s', '0401-0401-0401', 55, NULL),
(5, 1, 'Subway', '0501-0501-0501', 45, NULL),
(6, 1, 'Domino\'s Pizza', '0601-0601-0601', 50, NULL),
(7, 1, 'Papa John\'s', '0701-0701-0701', 55, NULL),
(8, 1, 'Taco Bell', '0801-0801-0801', 40, NULL),
(9, 1, 'Denny\'s', '0901-0901-0901', 80, NULL),
(10, 1, 'Burger King', '1001-1001-1001', 65, NULL),
(11, 1, 'Wendy\'s', '1101-1101-1101', 50, NULL),
(12, 1, 'Chili\'s', '1201-1201-1201', 60, NULL),
(13, 1, 'Hardee\'s', '1301-1301-1301', 55, NULL),
(14, 1, 'Popeyes', '1401-1401-1401', 75, NULL),
(15, 1, 'Shake Shack', '1501-1501-1501', 70, NULL),
(16, 1, 'Pizza Factory', '1601-1601-1601', 50, NULL),
(17, 1, 'Wingstop', '1701-1701-1701', 65, NULL),
(18, 1, 'Krispy Kreme', '1801-1801-1801', 45, NULL),
(19, 1, 'Five Guys', '1901-1901-1901', 60, NULL),
(20, 1, 'Jersey Mike\'s', '2001-2001-2001', 50, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados`
--

CREATE TABLE `estados` (
  `idEstado` int(11) NOT NULL,
  `estado` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estados`
--

INSERT INTO `estados` (`idEstado`, `estado`) VALUES
(1, 'ACTIVO'),
(2, 'DISPONIBLE'),
(3, 'OCUPADO'),
(4, 'PENDIENTE'),
(5, 'EN PROCESO'),
(6, 'INACTIVO'),
(7, 'FINALIZADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugares`
--

CREATE TABLE `lugares` (
  `idLugar` int(11) NOT NULL,
  `lugar` varchar(255) DEFAULT NULL,
  `idLugarSuperior` int(11) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `lugares`
--

INSERT INTO `lugares` (`idLugar`, `lugar`, `idLugarSuperior`, `tipo`) VALUES
(1, 'El Mundo', NULL, 'Mundo'),
(2, 'América', 1, 'Continente'),
(3, 'Europa', 1, 'Continente'),
(4, 'Asia', 1, 'Continente'),
(5, 'Honduras', 2, 'País'),
(6, 'Atlántida', 5, 'Departamento'),
(7, 'Choluteca', 5, 'Departamento'),
(8, 'Colón', 5, 'Departamento'),
(9, 'Comayagua', 5, 'Departamento'),
(10, 'Copán', 5, 'Departamento'),
(11, 'Cortés', 5, 'Departamento'),
(12, 'El Paraíso', 5, 'Departamento'),
(13, 'Francisco Morazán', 5, 'Departamento'),
(14, 'Gracias a Dios', 5, 'Departamento'),
(15, 'Intibucá', 5, 'Departamento'),
(16, 'Islas de la Bahía', 5, 'Departamento'),
(17, 'La Paz', 5, 'Departamento'),
(18, 'Lempira', 5, 'Departamento'),
(19, 'Ocotepeque', 5, 'Departamento'),
(20, 'Olancho', 5, 'Departamento'),
(21, 'Santa Bárbara', 5, 'Departamento'),
(22, 'Valle', 5, 'Departamento'),
(23, 'Yoro', 5, 'Departamento'),
(24, 'Distrito Central (Tegucigalpa ', 13, 'Municipio'),
(25, 'Cedros', 13, 'Municipio'),
(26, 'Talanga', 13, 'Municipio'),
(27, 'Valle de Ángeles', 13, 'Municipio'),
(28, 'Santa Lucía', 13, 'Municipio'),
(29, 'Guaimaca', 13, 'Municipio'),
(30, 'Cantarranas', 13, 'Municipio'),
(31, 'San Pedro Sula', 11, 'Municipio'),
(32, 'Choloma', 11, 'Municipio'),
(33, 'Puerto Cortés', 11, 'Municipio'),
(34, 'La Lima', 11, 'Municipio'),
(35, 'Villanueva', 11, 'Municipio'),
(36, 'Pimienta', 11, 'Municipio'),
(37, 'Omoa', 11, 'Municipio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodopagos`
--

CREATE TABLE `metodopagos` (
  `idMetodoPago` int(11) NOT NULL,
  `metodopago` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `metodopagos`
--

INSERT INTO `metodopagos` (`idMetodoPago`, `metodopago`) VALUES
(1, 'EFECTIVO'),
(2, 'TARJETA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
  `idPago` int(11) NOT NULL,
  `idMetodoPago` int(11) NOT NULL,
  `factura` varchar(255) DEFAULT NULL,
  `totalFactura` double NOT NULL,
  `fecha` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagos`
--

INSERT INTO `pagos` (`idPago`, `idMetodoPago`, `factura`, `totalFactura`, `fecha`) VALUES
(1, 1, 'FAC-1476', 171.09, '2025-04-06 12:00:51.000000'),
(2, 1, 'FAC-1731', 572.48, '2025-04-06 12:04:55.000000'),
(3, 1, 'FAC-5152', 462.48, '2025-04-06 12:06:12.000000'),
(4, 1, 'FAC-8723', 397.29, '2025-04-06 12:08:54.000000'),
(5, 2, 'FAC-3501', 291.36, '2025-04-06 12:09:28.000000'),
(6, 1, 'FAC-7624', 181.55, '2025-04-06 20:02:14.000000'),
(7, 2, 'FAC-6857', 258.43, '2025-04-06 20:18:59.000000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `idPedido` int(11) NOT NULL,
  `idComprador` int(11) NOT NULL,
  `idEmpresa` int(11) NOT NULL,
  `idRepartidor` int(11) DEFAULT NULL,
  `idEstado` int(11) NOT NULL,
  `idPago` int(11) NOT NULL,
  `fechaPedido` datetime DEFAULT NULL,
  `fechaFinal` datetime DEFAULT NULL,
  `costoEnvioTotal` double NOT NULL,
  `montoTotalDeProductos` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`idPedido`, `idComprador`, `idEmpresa`, `idRepartidor`, `idEstado`, `idPago`, `fechaPedido`, `fechaFinal`, `costoEnvioTotal`, `montoTotalDeProductos`) VALUES
(1, 2, 1, 3, 5, 1, '2025-04-06 12:00:51', NULL, 121.09, 50),
(2, 2, 2, 9, 7, 2, '2025-04-06 12:04:55', NULL, 132.48000000000002, 440),
(3, 2, 2, 4, 7, 3, '2025-04-06 12:06:12', NULL, 132.48000000000002, 330),
(4, 2, 17, 5, 5, 4, '2025-04-06 12:08:54', NULL, 137.29000000000002, 260),
(5, 2, 12, 5, 7, 5, '2025-04-06 12:09:28', NULL, 131.36, 160),
(6, 2, 6, 5, 5, 6, '2025-04-06 20:02:14', '2025-04-06 20:02:22', 121.55, 60),
(7, 11, 13, 5, 7, 7, '2025-04-06 20:18:59', '2025-04-06 20:19:34', 98.43, 160);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidosproductos`
--

CREATE TABLE `pedidosproductos` (
  `idPedido` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `preciounitario` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `fechacreacion` datetime(6) DEFAULT NULL,
  `idusuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidosproductos`
--

INSERT INTO `pedidosproductos` (`idPedido`, `idProducto`, `cantidad`, `preciounitario`, `subtotal`, `fechacreacion`, `idusuario`) VALUES
(1, 2, 1, 50, 50, NULL, NULL),
(2, 4, 4, 110, 440, NULL, NULL),
(3, 4, 3, 110, 330, NULL, NULL),
(4, 33, 2, 130, 260, NULL, NULL),
(5, 24, 1, 160, 160, NULL, NULL),
(6, 12, 1, 60, 60, NULL, NULL),
(7, 25, 1, 120, 120, NULL, NULL),
(7, 26, 1, 40, 40, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `idProducto` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `idEmpresa` int(11) NOT NULL,
  `producto` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`idProducto`, `idCategoria`, `idEmpresa`, `producto`, `descripcion`, `precio`, `imagen`) VALUES
(1, 1, 1, 'Pizza Pepperoni', 'Pizza mediana con extra pepperoni', 150, NULL),
(2, 1, 1, 'Crazy Bread', 'Pan de ajo con queso', 50, NULL),
(3, 1, 2, 'Cheesy Bites', 'Pizza con bordes de queso', 190, NULL),
(4, 1, 2, 'Wings BBQ', 'Alitas bañadas en salsa BBQ', 110, NULL),
(5, 2, 3, 'Combo 2 Piezas', '2 piezas de pollo, papas y soda', 120, NULL),
(6, 2, 3, 'Twister', 'Tortilla con pollo frito, ensalada y salsas', 90, NULL),
(7, 2, 4, 'Big Mac', 'Hamburguesa doble con salsa especial', 130, NULL),
(8, 2, 4, 'McNuggets 10pcs', 'Nuggets de pollo con papas', 110, NULL),
(9, 3, 5, 'Sub Pollo Teriyaki', 'Sub de pollo con salsa teriyaki', 115, NULL),
(10, 3, 5, 'Sub Italiano', 'Jamón, salami, pepperoni y vegetales', 125, NULL),
(11, 1, 6, 'Pizza Suprema', 'Pizza con carne, vegetales y queso', 180, NULL),
(12, 1, 6, 'Pan de Queso', 'Pan horneado con queso derretido', 60, NULL),
(13, 1, 7, 'Pizza Garden', 'Pizza vegetariana con vegetales frescos', 170, NULL),
(14, 1, 7, 'Breadsticks', 'Palitos de pan con queso y ajo', 55, NULL),
(15, 4, 8, 'Crunchwrap Supreme', 'Tortilla rellena con carne, queso y vegetales', 95, NULL),
(16, 4, 8, 'Burrito Beef', 'Burrito de carne y frijoles', 85, NULL),
(17, 5, 9, 'Desayuno Grand Slam', 'Huevos, pancakes, salchichas y tocino', 150, NULL),
(18, 5, 9, 'Burger Bacon', 'Hamburguesa con tocino y queso', 130, NULL),
(19, 2, 10, 'Whopper', 'Hamburguesa clásica con carne flameada', 125, NULL),
(20, 2, 10, 'Onion Rings', 'Aros de cebolla crujientes', 45, NULL),
(21, 2, 11, 'Baconator', 'Hamburguesa doble con tocino', 135, NULL),
(22, 2, 11, 'Papas Curly', 'Papas fritas en espiral', 55, NULL),
(23, 5, 12, 'Baby Back Ribs', 'Costillas con salsa BBQ', 210, NULL),
(24, 5, 12, 'Tex Mex Burger', 'Hamburguesa con guacamole y nachos', 160, NULL),
(25, 2, 13, 'Famous Star', 'Hamburguesa con todo', 120, NULL),
(26, 2, 13, 'Hash Rounds', 'Papas hashbrown', 40, NULL),
(27, 2, 14, 'Spicy Chicken Sandwich', 'Sandwich picante con pollo frito', 115, NULL),
(28, 2, 14, 'Chicken Tenders', 'Tiras de pollo empanizado', 100, NULL),
(29, 2, 15, 'ShackBurger', 'Hamburguesa con ShackSauce', 140, NULL),
(30, 2, 15, 'Milkshake Vainilla', 'Malteada clásica', 65, NULL),
(31, 1, 16, 'Pizza Artesanal', 'Pizza hecha en horno de leña', 160, NULL),
(32, 1, 16, 'Rollitos de Queso', 'Rollitos rellenos de queso fundido', 70, NULL),
(33, 4, 17, 'Wings Lemon Pepper', 'Alitas sabor limón y pimienta', 130, NULL),
(34, 4, 17, 'Wings Mango Habanero', 'Alitas picantes con mango', 135, NULL),
(35, 6, 18, 'Donas Glaseadas', 'Caja de 6 donas originales', 95, NULL),
(36, 6, 18, 'Donas Rellenas', 'Donas con relleno de crema o fresa', 110, NULL),
(37, 2, 19, 'Cheeseburger', 'Hamburguesa con doble queso', 150, NULL),
(38, 2, 19, 'Papas Cajún', 'Papas fritas con sazón cajún', 65, NULL),
(39, 3, 20, 'Sub Roast Beef', 'Sub de roast beef con vegetales', 130, NULL),
(40, 3, 20, 'Sub Atún', 'Sub de atún fresco con mayonesa', 125, NULL),
(41, 2, 3, 'Hamburguesa Clásica', 'Hamburguesa Clásica delicioso y recién preparado.', 95.99, NULL),
(42, 2, 3, 'Hamburguesa Doble', 'Hamburguesa Doble delicioso y recién preparado.', 115.78, NULL),
(43, 2, 3, 'Pollo Frito', 'Pollo Frito delicioso y recién preparado.', 135.49, NULL),
(44, 2, 3, 'Hamburguesa con Queso', 'Hamburguesa con Queso delicioso y recién preparado.', 125.15, NULL),
(45, 2, 3, 'Tenders', 'Tenders delicioso y recién preparado.', 105.3, NULL),
(46, 2, 4, 'Pollo Frito', 'Pollo Frito delicioso y recién preparado.', 120.75, NULL),
(47, 2, 4, 'Hamburguesa Clásica', 'Hamburguesa Clásica delicioso y recién preparado.', 110.99, NULL),
(48, 2, 4, 'Hamburguesa con Queso', 'Hamburguesa con Queso delicioso y recién preparado.', 100.56, NULL),
(49, 2, 4, 'Hamburguesa Doble', 'Hamburguesa Doble delicioso y recién preparado.', 115.49, NULL),
(50, 2, 4, 'Tenders', 'Tenders delicioso y recién preparado.', 109.87, NULL),
(51, 3, 5, 'Sub de Pollo', 'Sub de Pollo delicioso y recién preparado.', 98.45, NULL),
(52, 3, 5, 'Sub Veggie', 'Sub Veggie delicioso y recién preparado.', 110.89, NULL),
(53, 3, 5, 'Sándwich Clásico', 'Sándwich Clásico delicioso y recién preparado.', 90.6, NULL),
(54, 3, 5, 'Sub Italiano', 'Sub Italiano delicioso y recién preparado.', 115.43, NULL),
(55, 3, 5, 'Wrap de Pollo', 'Wrap de Pollo delicioso y recién preparado.', 105.32, NULL),
(56, 3, 6, 'Sándwich Clásico', 'Sándwich Clásico delicioso y recién preparado.', 89.74, NULL),
(57, 3, 6, 'Sub de Pollo', 'Sub de Pollo delicioso y recién preparado.', 97.11, NULL),
(58, 3, 6, 'Wrap de Pollo', 'Wrap de Pollo delicioso y recién preparado.', 112.36, NULL),
(59, 3, 6, 'Sub Veggie', 'Sub Veggie delicioso y recién preparado.', 108.22, NULL),
(60, 3, 6, 'Sub Italiano', 'Sub Italiano delicioso y recién preparado.', 120.89, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productosmasvendidos`
--

CREATE TABLE `productosmasvendidos` (
  `idProductoMasVendido` int(11) NOT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `cantidadVendida` int(11) DEFAULT NULL,
  `ultimaFechaVenta` datetime DEFAULT NULL,
  `totalingresos` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productosmasvendidos`
--

INSERT INTO `productosmasvendidos` (`idProductoMasVendido`, `idProducto`, `cantidadVendida`, `ultimaFechaVenta`, `totalingresos`) VALUES
(1, 2, 1, '2025-04-06 12:00:51', 50),
(2, 4, 7, '2025-04-06 12:06:12', 770),
(3, 33, 2, '2025-04-06 12:08:54', 260),
(4, 24, 1, '2025-04-06 12:09:28', 160),
(5, 12, 1, '2025-04-06 20:02:14', 60),
(6, 25, 1, '2025-04-06 20:18:59', 120),
(7, 26, 1, '2025-04-06 20:18:59', 40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `idRol` int(11) NOT NULL,
  `rol` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`idRol`, `rol`) VALUES
(1, 'CLIENTE'),
(2, 'REPARTIDOR'),
(3, 'ADMINISTRADOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajosrealizados`
--

CREATE TABLE `trabajosrealizados` (
  `idTrabajo` int(11) NOT NULL,
  `idPedido` int(11) DEFAULT NULL,
  `idRepartidor` int(11) DEFAULT NULL,
  `kmRecorrido` double DEFAULT NULL,
  `ganancia` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `trabajosrealizados`
--

INSERT INTO `trabajosrealizados` (`idTrabajo`, `idPedido`, `idRepartidor`, `kmRecorrido`, `ganancia`, `fecha`) VALUES
(1, 1, 3, 14.218155599452949, 71.09, '2025-04-06 12:00:51'),
(2, 2, 9, 14.495023057499868, 72.48, '2025-04-06 12:04:55'),
(3, 3, 4, 14.495023057499868, 72.48, '2025-04-06 12:06:12'),
(4, 4, 5, 14.458225482649352, 72.29, '2025-04-06 12:08:54'),
(5, 5, 5, 14.272390120367703, 71.36, '2025-04-06 12:09:28'),
(6, 6, 5, 14.310504517719796, 71.55, '2025-04-06 20:02:14'),
(7, 7, 5, 8.686883638065083, 43.43, '2025-04-06 20:18:59');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `idEstado` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `idEstado`, `nombre`, `apellido`, `correo`, `contrasenia`, `dni`, `telefono`, `idRol`) VALUES
(1, 1, 'EMEKA', 'CORP', 'emeka@emeka.hn', '12345678', '0801-1992-179862', '31512355', 3),
(2, 1, 'Eduardo Gabriel', 'Martinez Zelaya', 'emartinez@hmc.hn', '$2y$10$cp7nBMLhPzAawCyoWmiWn.8tT185LOD..7dJIZjwIPvhYy4V77lAW', '0801-1992-17986', NULL, 1),
(3, 3, 'Eduardo Gabriel', 'CROP', 'repartidor_1743962491847@gmail.com', 'repartidor_1743962491847', NULL, '+504-1743962491847', 2),
(4, 3, 'Juan', 'Pérez', 'juan.perez@example.com', 'hashed_password', '12345678', '1234567890', 2),
(5, 2, 'Carlos', 'López', 'carlos.lopez@example.com', 'hashed_password', '23456789', '2345678901', 2),
(6, 2, 'Ana', 'García', 'ana.garcia@example.com', 'hashed_password', '34567890', '3456789012', 2),
(7, 2, 'Laura', 'Martínez', 'laura.martinez@example.com', 'hashed_password', '45678901', '4567890123', 2),
(8, 2, 'David', 'Rodríguez', 'david.rodriguez@example.com', 'hashed_password', '56789012', '5678901234', 2),
(9, 3, 'Eduardo Gabriel', 'Pérez', 'repartidor_1743962698164@gmail.com', 'repartidor_1743962698164', NULL, '+504-1743962698164', 2),
(10, 1, 'Eduardo Gabriel', 'Martinez Zelaya', 'amartinez@hmc.hn', '$2y$10$C7S8Ssykj9arZR59OGAxgey6DlGgredV7vEngTZc.f902LvPqA1lG', '0801', NULL, 3),
(11, 1, 'David', 'Parada', 'dparada@hotmail.com', '$2y$10$LQRzSGKXlDVHT.fFXFqki.G8waBVEkO8I63rx9ZI1j.evbTFLote6', '54646', '546494', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

CREATE TABLE `vehiculos` (
  `idVehiculo` int(11) NOT NULL,
  `idRepartidor` int(11) NOT NULL,
  `vehiculo` varchar(255) DEFAULT NULL,
  `modelo` varchar(255) DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `anio` int(11) NOT NULL,
  `velocidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vehiculos`
--

INSERT INTO `vehiculos` (`idVehiculo`, `idRepartidor`, `vehiculo`, `modelo`, `marca`, `anio`, `velocidad`) VALUES
(1, 3, 'Pickup', 'X5', 'Honda', 0, 50),
(2, 4, 'Moto', 'Ninja 400', 'Kawasaki', 2022, 180),
(3, 5, 'Moto', 'R15', 'Yamaha', 2021, 160),
(4, 6, 'Bicicleta', 'Mountain X', 'Specialized', 2023, 50),
(5, 7, 'Scooter', 'E-125', 'Honda', 2022, 120),
(6, 8, 'Moto', 'Duke 390', 'KTM', 2023, 170),
(7, 9, 'SUV', 'X5', 'Toyota', 0, 154);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carritos`
--
ALTER TABLE `carritos`
  ADD PRIMARY KEY (`idCarrito`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD PRIMARY KEY (`idDireccion`),
  ADD KEY `idLugar` (`idLugar`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idEmpresa` (`idEmpresa`);

--
-- Indices de la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`idEmpresa`),
  ADD UNIQUE KEY `RTN` (`rtn`),
  ADD KEY `idUsuarioAdministrador` (`idUsuarioAdministrador`);

--
-- Indices de la tabla `estados`
--
ALTER TABLE `estados`
  ADD PRIMARY KEY (`idEstado`);

--
-- Indices de la tabla `lugares`
--
ALTER TABLE `lugares`
  ADD PRIMARY KEY (`idLugar`),
  ADD KEY `idLugarSuperior` (`idLugarSuperior`);

--
-- Indices de la tabla `metodopagos`
--
ALTER TABLE `metodopagos`
  ADD PRIMARY KEY (`idMetodoPago`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`idPago`),
  ADD UNIQUE KEY `factura` (`factura`),
  ADD KEY `idMetodoPago` (`idMetodoPago`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`idPedido`),
  ADD KEY `idComprador` (`idComprador`),
  ADD KEY `idRepartidor` (`idRepartidor`),
  ADD KEY `idEmpresa` (`idEmpresa`),
  ADD KEY `idEstado` (`idEstado`),
  ADD KEY `idPago` (`idPago`);

--
-- Indices de la tabla `pedidosproductos`
--
ALTER TABLE `pedidosproductos`
  ADD PRIMARY KEY (`idPedido`,`idProducto`),
  ADD UNIQUE KEY `UK9cv1pjrcnqm03hu9lwo2o1gev` (`idusuario`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `idCategoria` (`idCategoria`),
  ADD KEY `idEmpresa` (`idEmpresa`);

--
-- Indices de la tabla `productosmasvendidos`
--
ALTER TABLE `productosmasvendidos`
  ADD PRIMARY KEY (`idProductoMasVendido`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `trabajosrealizados`
--
ALTER TABLE `trabajosrealizados`
  ADD PRIMARY KEY (`idTrabajo`),
  ADD KEY `idRepartidor` (`idRepartidor`),
  ADD KEY `idPedido` (`idPedido`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD KEY `idEstado` (`idEstado`),
  ADD KEY `idRol` (`idRol`);

--
-- Indices de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD PRIMARY KEY (`idVehiculo`),
  ADD KEY `idRepartidor` (`idRepartidor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carritos`
--
ALTER TABLE `carritos`
  MODIFY `idCarrito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  MODIFY `idDireccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `idEmpresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `lugares`
--
ALTER TABLE `lugares`
  MODIFY `idLugar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `metodopagos`
--
ALTER TABLE `metodopagos`
  MODIFY `idMetodoPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `idPedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de la tabla `productosmasvendidos`
--
ALTER TABLE `productosmasvendidos`
  MODIFY `idProductoMasVendido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `trabajosrealizados`
--
ALTER TABLE `trabajosrealizados`
  MODIFY `idTrabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  MODIFY `idVehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carritos`
--
ALTER TABLE `carritos`
  ADD CONSTRAINT `carritos_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`),
  ADD CONSTRAINT `carritos_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`);

--
-- Filtros para la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD CONSTRAINT `direcciones_ibfk_1` FOREIGN KEY (`idLugar`) REFERENCES `lugares` (`idLugar`),
  ADD CONSTRAINT `direcciones_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `direcciones_ibfk_3` FOREIGN KEY (`idEmpresa`) REFERENCES `empresas` (`idEmpresa`) ON DELETE CASCADE;

--
-- Filtros para la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD CONSTRAINT `empresas_ibfk_1` FOREIGN KEY (`idUsuarioAdministrador`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE;

--
-- Filtros para la tabla `lugares`
--
ALTER TABLE `lugares`
  ADD CONSTRAINT `lugares_ibfk_1` FOREIGN KEY (`idLugarSuperior`) REFERENCES `lugares` (`idLugar`) ON DELETE SET NULL;

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`idMetodoPago`) REFERENCES `metodopagos` (`idMetodoPago`) ON DELETE CASCADE;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`idComprador`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`idRepartidor`) REFERENCES `usuarios` (`idUsuario`) ON DELETE SET NULL,
  ADD CONSTRAINT `pedidos_ibfk_3` FOREIGN KEY (`idEmpresa`) REFERENCES `empresas` (`idEmpresa`) ON DELETE CASCADE,
  ADD CONSTRAINT `pedidos_ibfk_4` FOREIGN KEY (`idEstado`) REFERENCES `estados` (`idEstado`),
  ADD CONSTRAINT `pedidos_ibfk_5` FOREIGN KEY (`idPago`) REFERENCES `pagos` (`idPago`) ON DELETE CASCADE;

--
-- Filtros para la tabla `pedidosproductos`
--
ALTER TABLE `pedidosproductos`
  ADD CONSTRAINT `FKpowk01x087d4eg8eai8t9jx0e` FOREIGN KEY (`idusuario`) REFERENCES `usuarios` (`idUsuario`),
  ADD CONSTRAINT `pedidosproductos_ibfk_1` FOREIGN KEY (`idPedido`) REFERENCES `pedidos` (`idPedido`) ON DELETE CASCADE,
  ADD CONSTRAINT `pedidosproductos_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE CASCADE,
  ADD CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`idEmpresa`) REFERENCES `empresas` (`idEmpresa`) ON DELETE CASCADE;

--
-- Filtros para la tabla `productosmasvendidos`
--
ALTER TABLE `productosmasvendidos`
  ADD CONSTRAINT `productosmasvendidos_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`);

--
-- Filtros para la tabla `trabajosrealizados`
--
ALTER TABLE `trabajosrealizados`
  ADD CONSTRAINT `trabajosrealizados_ibfk_1` FOREIGN KEY (`idRepartidor`) REFERENCES `usuarios` (`idUsuario`),
  ADD CONSTRAINT `trabajosrealizados_ibfk_2` FOREIGN KEY (`idPedido`) REFERENCES `pedidos` (`idPedido`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`idEstado`) REFERENCES `estados` (`idEstado`),
  ADD CONSTRAINT `usuarios_ibfk_2` FOREIGN KEY (`idRol`) REFERENCES `roles` (`idRol`);

--
-- Filtros para la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`idRepartidor`) REFERENCES `usuarios` (`idUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
