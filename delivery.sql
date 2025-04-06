-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-04-2025 a las 07:35:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

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
(21, 'Honduras Medical Center', 'Trabajo', 24, 1, NULL, 0x0000000001010000009f2876592dcc55c0064fd7c9cf302c40),
(22, 'Boulevard Morazán, Col. Palmira', 'Sucursal principal Little Caesars', 24, NULL, 1, 0x000000000101000000956588635dcc55c08fc2f5285cef2b40),
(23, 'Avenida La Paz, Frente a La Ceiba', 'Sucursal Pizza Hut', 24, NULL, 2, 0x000000000101000000b84082e2c7cc55c058a835cd3bee2b40),
(24, 'Boulevard Suyapa, Col. Alameda', 'Sucursal KFC', 24, NULL, 3, 0x0000000001010000000e2db29defcb55c05bb1bfec9efc2b40),
(25, 'Calle 5, Col. La Joya', 'Sucursal McDonald\'s', 24, NULL, 4, 0x0000000001010000004850fc1873cb55c0bc74931804f62b40),
(26, 'Avenida San Juan, Col. Miraflores', 'Sucursal Subway', 24, NULL, 5, 0x000000000101000000aa8251499dcc55c0f31fd26f5fe72b40),
(27, 'Avenida Juan Pablo II, Col. El Trapiche', 'Sucursal Domino\'s Pizza', 24, NULL, 6, 0x00000000010100000037894160e5cc55c057ec2fbb27ef2b40),
(28, 'Calle Los Arcos, Col. La Puerta', 'Sucursal Papa John\'s', 24, NULL, 7, 0x000000000101000000c1caa145b6cb55c0857cd0b359f52b40),
(29, 'Avenida Las Américas, Col. Las Palmas', 'Sucursal Taco Bell', 24, NULL, 8, 0x000000000101000000c66d3480b7cc55c055302aa913f02b40),
(30, 'Avenida La Cumbre, Col. Colón', 'Sucursal Denny\'s', 24, NULL, 9, 0x000000000101000000f163cc5d4bcc55c04ed1915cfee32b40),
(31, 'Avenida 10 de Septiembre, Col. Zacatecoluca', 'Sucursal Burger King', 24, NULL, 10, 0x000000000101000000c139234a7bcb55c026e4839ecdea2b40),
(32, 'Calle Pinares, Col. El Cacao', 'Sucursal Wendy\'s', 24, NULL, 11, 0x000000000101000000c7bab88d06cc55c0b30c71ac8bfb2b40),
(33, 'Boulevard Los Próceres, Col. Lempira', 'Sucursal Chili\'s', 24, NULL, 12, 0x000000000101000000e9263108accc55c0e561a1d634ef2b40),
(34, 'Calle de la Loma, Col. Miramar', 'Sucursal Hardee\'s', 24, NULL, 13, 0x0000000001010000004faf946588cb55c0c1a8a44e40f32b40),
(35, 'Boulevard Santa Fe, Col. El Pedregal', 'Sucursal Popeyes', 24, NULL, 14, 0x0000000001010000005305a3923acd55c0d578e92631e82b40),
(36, 'Calle Los Laureles, Col. Santa Rosa', 'Sucursal Shake Shack', 24, NULL, 15, 0x0000000001010000001b0de02d90cc55c009f9a067b3ea2b40),
(37, 'Avenida Las Maderas, Col. Montecillos', 'Sucursal Pizza Factory', 24, NULL, 16, 0x000000000101000000780b24287ecc55c0b6f3fdd478e92b40),
(38, 'Avenida Principal, Col. Los Andes', 'Sucursal Wingstop', 24, NULL, 17, 0x000000000101000000fe43faedebcc55c01ff46c567dee2b40),
(39, 'Calle La Ceiba, Col. El Roble', 'Sucursal Krispy Kreme', 24, NULL, 18, 0x0000000001010000008048bf7d1dcc55c082734694f6e62b40),
(40, 'Avenida México, Col. Miraflor', 'Sucursal Five Guys', 24, NULL, 19, 0x0000000001010000005b423ee8d9cc55c01c7c613255f02b40),
(41, 'Avenida 1 de Mayo, Col. Los Pinos', 'Sucursal Jersey Mike\'s', 24, NULL, 20, 0x000000000101000000151dc9e53fcc55c0226c787aa5ec2b40),
(52, 'La Kennedy', 'Casa', 24, 1, NULL, 0x000000000101000000a9237450accc55c0de48ea4ec1232c40),
(53, 'CAlpules', 'Mi casa', 24, 13, NULL, 0x0000000001010000001952ea37b8cc55c096253acb2c1a2c40),
(54, 'Hon', 'Trabajo', 24, 13, NULL, 0x0000000001010000009f2876592dcc55c0064fd7c9cf302c40);

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
(3, 1, 'KFC', '0301-0301-0301', 70, NULL),
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
(2, 'EN PROCESO'),
(3, 'PENDIENTE'),
(4, 'EN CAMINO'),
(5, 'DISPONIBLE'),
(6, 'EN RUTA'),
(7, 'FINALIZADO'),
(18, 'OCUPADO');

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
(24, 'Distrito Central (Tegucigalpa y Comayagüela)', 13, 'Municipio'),
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
(1, 2, 'FAC-4445', 460, '2025-04-05 15:47:58.000000'),
(2, 1, 'FAC-5513', 397.68986626029226, '2025-04-05 15:52:53.000000'),
(3, 1, 'FAC-1834', 162.8550860453558, '2025-04-05 15:58:47.000000'),
(4, 1, 'FAC-2978', 371.86, '2025-04-05 15:59:45.000000'),
(5, 2, 'FAC-2303', 262.2, '2025-04-05 16:00:53.000000'),
(6, 2, 'FAC-9402', 377.86, '2025-04-05 16:06:32.000000'),
(7, 1, 'FAC-3152', 377.86, '2025-04-05 16:07:01.000000'),
(8, 1, 'FAC-2871', 352.2, '2025-04-05 16:27:22.000000'),
(9, 1, 'FAC-5603', 352.2, '2025-04-05 16:28:17.000000'),
(10, 1, 'FAC-7719', 162.86, '2025-04-05 16:28:31.000000'),
(11, 1, 'FAC-6980', 247.47, '2025-04-05 17:54:59.000000'),
(12, 1, 'FAC-5346', 629.17, '2025-04-05 19:42:57.000000'),
(13, 1, 'FAC-9376', 162.86, '2025-04-05 19:56:29.000000'),
(14, 1, 'FAC-7735', 222.7, '2025-04-05 20:28:59.000000'),
(15, 1, 'FAC-1743', 162.86, '2025-04-05 20:34:35.000000'),
(16, 2, 'FAC-7529', 162.86, '2025-04-05 20:36:02.000000'),
(17, 2, 'FAC-5803', 242.86, '2025-04-05 20:37:54.000000'),
(18, 2, 'FAC-6429', 232.01, '2025-04-05 20:38:17.000000'),
(19, 2, 'FAC-9671', 162.86, '2025-04-05 20:45:18.000000'),
(20, 1, 'FAC-9409', 162.86, '2025-04-05 20:51:38.000000'),
(21, 2, 'FAC-5322', 242.86, '2025-04-05 20:53:44.000000'),
(22, 2, 'FAC-9397', 246.71, '2025-04-05 20:54:35.000000'),
(23, 2, 'FAC-7202', 162.86, '2025-04-05 20:54:57.000000'),
(24, 2, 'FAC-8907', 262.2, '2025-04-05 21:44:18.000000'),
(25, 1, 'FAC-5308', 217.86, '2025-04-05 21:52:20.000000'),
(26, 2, 'FAC-3996', 242.86, '2025-04-05 21:53:07.000000'),
(27, 1, 'FAC-1462', 242.86, '2025-04-05 22:32:17.000000'),
(28, 1, 'FAC-3033', 162.86, '2025-04-05 22:34:40.000000'),
(29, 2, 'FAC-7120', 242.86, '2025-04-05 22:36:24.000000'),
(30, 1, 'FAC-2076', 251.86, '2025-04-05 22:38:00.000000'),
(31, 2, 'FAC-5533', 246.71, '2025-04-05 22:42:36.000000'),
(32, 1, 'FAC-2550', 162.86, '2025-04-05 22:53:19.000000'),
(33, 2, 'FAC-9559', 512.86, '2025-04-05 23:01:06.000000');

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
(1, 1, 11, 7, 20, 1, '2025-04-05 15:47:58', NULL, 50, 460),
(2, 1, 3, 7, 15, 2, '2025-04-05 15:52:53', NULL, 70, 270.98),
(3, 13, 11, 7, 9, 3, '2025-04-05 15:58:47', NULL, 107.8550860453558, 55),
(4, 1, 3, 7, 14, 4, '2025-04-05 15:59:45', NULL, 126.70986626029223, 245.15),
(5, 1, 3, 7, 13, 5, '2025-04-05 16:00:53', NULL, 126.71000000000001, 135.49),
(7, 1, 11, 7, 12, 7, '2025-04-05 16:07:01', NULL, 107.86, 270),
(8, 1, 3, 7, 11, 8, '2025-04-05 16:27:22', NULL, 126.71000000000001, 225.49),
(9, 1, 11, NULL, 7, 10, '2025-04-05 16:28:31', NULL, 107.86, 55),
(10, 13, 17, 7, 8, 11, '2025-04-05 17:54:59', NULL, 112.47, 135),
(11, 1, 3, 7, 10, 12, '2025-04-05 19:42:57', NULL, 126.71000000000001, 502.46000000000004),
(12, 1, 11, 7, 19, 13, '2025-04-05 19:56:29', NULL, 107.86, 55),
(13, 1, 3, 7, 3, 14, '2025-04-05 20:28:59', NULL, 126.71000000000001, 95.99),
(14, 1, 11, 7, 3, 15, '2025-04-05 20:34:35', NULL, 107.86, 55),
(15, 1, 11, 7, 3, 16, '2025-04-05 20:36:02', NULL, 107.86, 55),
(16, 1, 11, 7, 3, 17, '2025-04-05 20:37:54', NULL, 107.86, 135),
(17, 1, 3, 8, 3, 18, '2025-04-05 20:38:17', NULL, 126.71000000000001, 105.3),
(18, 1, 11, 9, 3, 19, '2025-04-05 20:45:18', NULL, 107.86, 55),
(19, 1, 11, 10, 3, 20, '2025-04-05 20:51:38', NULL, 107.86, 55),
(20, 1, 11, 11, 7, 21, '2025-04-05 20:53:44', NULL, 107.86, 135),
(21, 1, 3, 15, 3, 22, '2025-04-05 20:54:35', NULL, 126.71000000000001, 120),
(22, 1, 11, 14, 3, 23, '2025-04-05 20:54:57', NULL, 107.86, 55),
(23, 1, 3, 16, 7, 24, '2025-04-05 21:44:18', NULL, 126.71000000000001, 135.49),
(24, 1, 11, 17, 7, 25, '2025-04-05 21:52:20', NULL, 107.86, 110),
(25, 1, 11, 18, 7, 26, '2025-04-05 21:53:07', NULL, 107.86, 135),
(26, 1, 11, 19, 3, 27, '2025-04-05 22:32:17', NULL, 107.86, 135),
(27, 1, 11, 20, 3, 28, '2025-04-05 22:34:40', NULL, 107.86, 55),
(28, 1, 11, 21, 3, 29, '2025-04-05 22:36:24', NULL, 107.86, 135),
(29, 1, 3, 22, 3, 30, '2025-04-05 22:38:00', NULL, 126.71000000000001, 125.15),
(30, 1, 3, 23, 7, 31, '2025-04-05 22:42:36', NULL, 126.71000000000001, 120),
(31, 1, 11, 24, 7, 32, '2025-04-05 22:53:19', NULL, 107.86, 55),
(32, 1, 11, 25, 7, 33, '2025-04-05 23:01:06', NULL, 107.86, 405);

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
(1, 21, 3, 135, 405, NULL, NULL),
(1, 22, 1, 55, 55, NULL, NULL),
(2, 43, 2, 135.49, 270.98, NULL, NULL),
(3, 22, 1, 55, 55, NULL, NULL),
(4, 5, 1, 120, 120, NULL, NULL),
(4, 44, 1, 125.15, 125.15, NULL, NULL),
(5, 43, 1, 135.49, 135.49, NULL, NULL),
(7, 21, 2, 135, 270, NULL, NULL),
(8, 6, 1, 90, 90, NULL, NULL),
(8, 43, 1, 135.49, 135.49, NULL, NULL),
(9, 22, 1, 55, 55, NULL, NULL),
(10, 34, 1, 135, 135, NULL, NULL),
(11, 41, 1, 95.99, 95.99, NULL, NULL),
(11, 43, 3, 135.49, 406.47, NULL, NULL),
(12, 22, 1, 55, 55, NULL, NULL),
(13, 41, 1, 95.99, 95.99, NULL, NULL),
(14, 22, 1, 55, 55, NULL, NULL),
(15, 22, 1, 55, 55, NULL, NULL),
(16, 21, 1, 135, 135, NULL, NULL),
(17, 45, 1, 105.3, 105.3, NULL, NULL),
(18, 22, 1, 55, 55, NULL, NULL),
(19, 22, 1, 55, 55, NULL, NULL),
(20, 21, 1, 135, 135, NULL, NULL),
(21, 5, 1, 120, 120, NULL, NULL),
(22, 22, 1, 55, 55, NULL, NULL),
(23, 43, 1, 135.49, 135.49, NULL, NULL),
(24, 22, 2, 55, 110, NULL, NULL),
(25, 21, 1, 135, 135, NULL, NULL),
(26, 21, 1, 135, 135, NULL, NULL),
(27, 22, 1, 55, 55, NULL, NULL),
(28, 21, 1, 135, 135, NULL, NULL),
(29, 44, 1, 125.15, 125.15, NULL, NULL),
(30, 5, 1, 120, 120, NULL, NULL),
(31, 22, 1, 55, 55, NULL, NULL),
(32, 21, 3, 135, 405, NULL, NULL);

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
(1, 7, NULL, 11.571017209071162, 57.86, '2025-04-05 16:07:01'),
(2, 8, NULL, 11.341973252058446, 56.71, '2025-04-05 16:27:22'),
(3, 9, NULL, 11.571017209071162, 57.86, '2025-04-05 16:28:31'),
(4, 10, NULL, 9.493593502742998, 47.47, '2025-04-05 17:54:59'),
(5, 11, NULL, 11.341973252058446, 56.71, '2025-04-05 19:42:57'),
(6, 12, NULL, 11.571017209071162, 57.86, '2025-04-05 19:56:29'),
(7, 13, NULL, 11.341973252058446, 56.71, '2025-04-05 20:29:00'),
(8, 14, NULL, 11.571017209071162, 57.86, '2025-04-05 20:34:35'),
(9, 15, NULL, 11.571017209071162, 57.86, '2025-04-05 20:36:02'),
(10, 16, NULL, 11.571017209071162, 57.86, '2025-04-05 20:37:54'),
(11, 17, NULL, 11.341973252058446, 56.71, '2025-04-05 20:38:17'),
(12, 18, NULL, 11.571017209071162, 57.86, '2025-04-05 20:45:18'),
(13, 19, NULL, 11.571017209071162, 57.86, '2025-04-05 20:51:38'),
(14, 20, NULL, 11.571017209071162, 57.86, '2025-04-05 20:53:44'),
(15, 21, NULL, 11.341973252058446, 56.71, '2025-04-05 20:54:35'),
(16, 22, NULL, 11.571017209071162, 57.86, '2025-04-05 20:54:57'),
(17, 23, NULL, 11.341973252058446, 56.71, '2025-04-05 21:44:18'),
(18, 24, NULL, 11.571017209071162, 57.86, '2025-04-05 21:52:20'),
(19, 25, NULL, 11.571017209071162, 57.86, '2025-04-05 21:53:07'),
(20, 26, NULL, 11.571017209071162, 57.86, '2025-04-05 22:32:17'),
(21, 27, NULL, 11.571017209071162, 57.86, '2025-04-05 22:34:40'),
(22, 28, NULL, 11.571017209071162, 57.86, '2025-04-05 22:36:24'),
(23, 29, NULL, 11.341973252058446, 56.71, '2025-04-05 22:38:00'),
(24, 30, NULL, 11.341973252058446, 56.71, '2025-04-05 22:42:36'),
(25, 31, 24, 11.571017209071162, 57.86, '2025-04-05 22:53:19'),
(26, 32, 25, 11.571017209071162, 57.86, '2025-04-05 23:01:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacionesrepartidores`
--

CREATE TABLE `ubicacionesrepartidores` (
  `idUbicacionRepartidor` int(11) NOT NULL,
  `idRepartidor` int(11) NOT NULL,
  `ubicacion` point DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ubicacionesrepartidores`
--

INSERT INTO `ubicacionesrepartidores` (`idUbicacionRepartidor`, `idRepartidor`, `ubicacion`) VALUES
(1, 7, 0xe61000000101000000b4c876be9ffa2b40f90fe9b7af032dc0),
(2, 8, 0xe610000001010000009a99999999f92b40f853e3a59b042dc0),
(3, 9, 0xe6100000010100000046b6f3fdd4f82b403108ac1c5a042dc0),
(4, 10, 0xe610000001010000007b14ae47e1fa2b40508d976e12032dc0),
(5, 11, 0xe6100000010100000096438b6ce7fb2b4085eb51b81e052dc0);

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
  `idRol` int(11) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `idubicacionrepartidor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `idEstado`, `nombre`, `apellido`, `correo`, `contrasenia`, `dni`, `idRol`, `telefono`, `idubicacionrepartidor`) VALUES
(1, 1, 'EDUARDO', 'MARTINEZ', 'emartinez@hmc.hn', '$2y$10$y3MHpjDet5p9u/yWeBB5pektkjsiKj6U7H.q2P1gizH24lzuwwrMS', '0801-1992-17896', 1, NULL, NULL),
(7, 18, 'Carlos', 'Pérez', 'carlos.perez@example.com', 'password123', '1234567890', 2, NULL, NULL),
(8, 18, 'Ana', 'Gómez', 'ana.gomez@example.com', 'password123', '2345678901', 2, NULL, NULL),
(9, 18, 'Luis', 'Martínez', 'luis.martinez@example.com', 'password123', '3456789012', 2, NULL, NULL),
(10, 18, 'Marta', 'Lopez', 'marta.lopez@example.com', 'password123', '4567890123', 2, NULL, NULL),
(11, 18, 'Pedro', 'Ramírez', 'pedro.ramirez@example.com', 'password123', '5678901234', 2, NULL, NULL),
(13, 1, 'EDUARDO', 'MARTINEZ', 'amartinez@hmc.hn', '$2y$10$gPXw1QiOv1bQJzLV909G6e9OeD..u/xcAsMdhkmz1/Gi6O5xppwTy', '0801-1992-178962', 3, NULL, NULL),
(14, 18, 'Ana', 'Gómez', 'repartidor_1743910947568@gmail.com', '123345677', NULL, 2, '232342342', NULL),
(15, 18, 'Carlos', 'Pérez', 'repartidor_1743910989348@gmail.com', '123345677', NULL, 2, '232342342', NULL),
(16, 18, 'Luis', 'Martínez', 'repartidor_1743911063150@gmail.com', '123345677', NULL, 2, '232342342', NULL),
(17, 18, 'Luis', 'Martínez', 'repartidor_1743911545896@gmail.com', '123345677', NULL, 2, '232342342', NULL),
(18, 18, 'Luis', 'Martínez', 'repartidor_1743911591346@gmail.com', '123345677', NULL, 2, '232342342', NULL),
(19, 18, 'Pedro', 'Ramírez', 'repartidor_1743913944332@gmail.com', 'repartidor_1743913944333', NULL, 2, '+504-1743913944333', NULL),
(20, 18, 'Carlos', 'Pérez', 'repartidor_1743914084107@gmail.com', 'repartidor_1743914084107', NULL, 2, '+504-1743914084107', NULL),
(21, 18, 'Ana', 'Gómez', 'repartidor_1743914187814@gmail.com', 'repartidor_1743914187814', NULL, 2, '+504-1743914187814', NULL),
(22, 18, 'Luis', 'Martínez', 'repartidor_1743914285289@gmail.com', 'repartidor_1743914285289', NULL, 2, '+504-1743914285289', NULL),
(23, 18, 'Ana', 'Gómez', 'repartidor_1743914560335@gmail.com', 'repartidor_1743914560336', NULL, 2, '+504-1743914560336', NULL),
(24, 18, 'Marta', 'Lopez', 'repartidor_1743915204903@gmail.com', 'repartidor_1743915204903', NULL, 2, '+504-1743915204903', NULL),
(25, 18, 'Ana', 'Gómez', 'repartidor_1743915668986@gmail.com', 'repartidor_1743915668987', NULL, 2, '+504-1743915668987', NULL);

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
(1, 7, 'Motocicleta', 'XRX 250', 'Honda', 2021, 150),
(2, 8, 'Motocicleta', 'YBR 125', 'Yamaha', 2020, 120),
(3, 9, 'Motocicleta', 'Gixxer 150', 'Suzuki', 2022, 130),
(4, 10, 'Motocicleta', 'Duke 200', 'KTM', 2021, 140),
(5, 11, 'Motocicleta', 'R15 V3', 'Yamaha', 2020, 135),
(6, 19, 'Hatchback', 'X5', 'BMW', 0, 100),
(7, 20, 'Pickup', 'Mustang', 'Chevrolet', 0, 250),
(8, 21, 'Sedán', 'Corolla', 'Ford', 0, 100),
(9, 22, 'Sedán', 'Mustang', 'Chevrolet', 0, 250),
(10, 23, 'Sedán', 'Camaro', 'Ford', 0, 300),
(11, 24, 'Sedán', 'Camaro', 'Honda', 0, 200),
(12, 25, 'Sedán', 'Camaro', 'Honda', 0, 150);

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
-- Indices de la tabla `ubicacionesrepartidores`
--
ALTER TABLE `ubicacionesrepartidores`
  ADD PRIMARY KEY (`idUbicacionRepartidor`),
  ADD KEY `idRepartidor` (`idRepartidor`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `UK5s5ax8wi1w608csyou6bve33x` (`idubicacionrepartidor`),
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
  MODIFY `idCarrito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  MODIFY `idDireccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `idEmpresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

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
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `idPedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `trabajosrealizados`
--
ALTER TABLE `trabajosrealizados`
  MODIFY `idTrabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `ubicacionesrepartidores`
--
ALTER TABLE `ubicacionesrepartidores`
  MODIFY `idUbicacionRepartidor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  MODIFY `idVehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

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
-- Filtros para la tabla `trabajosrealizados`
--
ALTER TABLE `trabajosrealizados`
  ADD CONSTRAINT `trabajosrealizados_ibfk_1` FOREIGN KEY (`idRepartidor`) REFERENCES `usuarios` (`idUsuario`),
  ADD CONSTRAINT `trabajosrealizados_ibfk_2` FOREIGN KEY (`idPedido`) REFERENCES `pedidos` (`idPedido`);

--
-- Filtros para la tabla `ubicacionesrepartidores`
--
ALTER TABLE `ubicacionesrepartidores`
  ADD CONSTRAINT `ubicacionesrepartidores_ibfk_1` FOREIGN KEY (`idRepartidor`) REFERENCES `usuarios` (`idUsuario`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `FKd484ybcncx7igl40otssxd1cp` FOREIGN KEY (`idubicacionrepartidor`) REFERENCES `direcciones` (`idDireccion`),
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
