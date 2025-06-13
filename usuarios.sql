-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-06-2025 a las 08:20:58
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ingsoft_media`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros_favoritos`
--

CREATE TABLE `libros_favoritos` (
  `libro_id` varchar(255) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autores` varchar(255) DEFAULT NULL,
  `portada_url` varchar(1000) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_agregado` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libros_favoritos`
--

INSERT INTO `libros_favoritos` (`libro_id`, `usuario_id`, `titulo`, `autores`, `portada_url`, `activo`, `fecha_agregado`) VALUES
('gskHEAAAQBAJ', 3, 'El Conde de Montecristo', 'Alexandre Dumas', 'http://books.google.com/books/publisher/content?id=gskHEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70vBHPuj_N_E9nsCn4R653xkF86EI0Dbavrn4aBmY68UfE9iZ_yt3LavmMtUY3_dWlsPo0Jmqy3smkIFxv_0WvxeIUiJaDzWDyuyv1eRN_-F2Gs2aNrt5JYZjPFbGWk6hDxjL3c&source=gbs_api', 1, '2025-06-13 04:08:18'),
('icKmd-tlvPMC', 3, 'Journey to the Center of the Earth', 'Jules Verne', 'http://books.google.com/books/content?id=icKmd-tlvPMC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71gbSHK1YiyvUDlivmdey5mfRE7fHFYsugupc7FrDvzktWKE7N2p1wMW6PNpxaM4moeoQEjAks8E4kEQLXFAfBEyi9To9F0-NTzMvyIv75OXk56w5Kird4yARG5-iwwg3co7Z5s&source=gbs_api', 0, '2025-06-13 04:08:04'),
('ztjaDwAAQBAJ', 3, 'El baile de máscaras', 'Alexandre Dumas', 'http://books.google.com/books/publisher/content?id=ztjaDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE7045EwHvu2wamuJa7Ig8HixawQHwfOWqSFk95Cr52M_XaX8YNoEi6XUtipOn1eBjr9ZzJgaIdikGbZTBypERThfP5zWvdca4n0f3LVbYVjreBJUjak49IjS7voBhVztq2JnTCda&source=gbs_api', 0, '2025-06-13 05:02:32'),
('_Wo_MQAACAAJ', 3, 'Antología Alexandre Dumas: Los Tres Mosqueteros (con Notas)', 'Alexandre Dumas', NULL, 1, '2025-06-13 05:02:27');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peliculas_favoritas`
--

CREATE TABLE `peliculas_favoritas` (
  `imdb_id` varchar(255) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `anio` varchar(255) DEFAULT NULL,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `actores` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `genero_preferido` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `email`, `password`, `username`, `fecha_registro`, `genero_preferido`) VALUES
(3, 'navy@email.com', '$2a$10$wdKpVx5zDksIY85zOLq2teHbV94loHvkk3tfw0KMiYdyYS7sYi5yy', 'Navy', '2025-06-13 02:35:49.000000', NULL),
(4, 'admin@email.com', '$2a$10$pFu8jD.NsKfKOEE59r8cruM4/VimpMx.sAvDZ2Dl.N6HWQlyQ.I1a', 'admin', '2025-06-13 06:07:21.000000', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `libros_favoritos`
--
ALTER TABLE `libros_favoritos`
  ADD PRIMARY KEY (`libro_id`,`usuario_id`),
  ADD KEY `idx_libros_favoritos_usuario` (`usuario_id`),
  ADD KEY `idx_libros_favoritos_activo` (`activo`);

--
-- Indices de la tabla `peliculas_favoritas`
--
ALTER TABLE `peliculas_favoritas`
  ADD PRIMARY KEY (`imdb_id`,`usuario_id`),
  ADD KEY `FK_peliculas_favoritas_usuario` (`usuario_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`),
  ADD UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `libros_favoritos`
--
ALTER TABLE `libros_favoritos`
  ADD CONSTRAINT `FK_libros_favoritos_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `libros_favoritos_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `peliculas_favoritas`
--
ALTER TABLE `peliculas_favoritas`
  ADD CONSTRAINT `FK_peliculas_favoritas_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `peliculas_favoritas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
