-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-06-2025 a las 12:12:45
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
('bq661M03T4kC', 5, 'El zorro de arriba y el zorro de abajo', 'José María Arguedas', 'http://books.google.com/books/content?id=bq661M03T4kC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71Jfd3VkrXXdz1Cq5Xngo7ucZTtDLsitqfQ6x18vsG07ftppGSElBU2nNEYAB3tEFiD2bfOeMCt0280vbg1bHWwcKcFFN-qy_dPz0XkCLjhZzonbgr4DTA9pnicymbl3IvNH5Ei&source=gbs_api', 1, '2025-06-13 08:41:48'),
('f7x_SmOQKuQC', 5, 'Relatos y romanceadas mapuches', 'César A. Fernández', 'http://books.google.com/books/content?id=f7x_SmOQKuQC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71OeovriaA_cxmjclhbNDK9Tc_cCtFY9OA_zKNKjHLWyE6qhwzj9UIM4VkDtKnyeKwsrzuCUUWV2-C2nQ5r2Kum9_GzxcUf-NL7mVEymrg0MJ-a6hf1ck8907SJAQ_Us66wyg-m&source=gbs_api', 1, '2025-06-13 08:47:49'),
('gskHEAAAQBAJ', 3, 'El Conde de Montecristo', 'Alexandre Dumas', 'http://books.google.com/books/publisher/content?id=gskHEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70vBHPuj_N_E9nsCn4R653xkF86EI0Dbavrn4aBmY68UfE9iZ_yt3LavmMtUY3_dWlsPo0Jmqy3smkIFxv_0WvxeIUiJaDzWDyuyv1eRN_-F2Gs2aNrt5JYZjPFbGWk6hDxjL3c&source=gbs_api', 1, '2025-06-13 04:08:18'),
('icKmd-tlvPMC', 5, 'Journey to the Center of the Earth', 'Jules Verne', 'http://books.google.com/books/content?id=icKmd-tlvPMC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70ZToqOLaGLUmPtAIEc6JfuOK51YMiFaza03Fn3V1cpGE6I4LzMec6dA6Oze84Hya6rTUbVMmN6kfO4weqZY-QpKGYc6a9_xfj4xUA9zJaQfHAzrA6X2akc8vAaVf3a6BJYDg2b&source=gbs_api', 1, '2025-06-13 04:08:04'),
('kiweEQAAQBAJ', 5, 'El Conde', 'F. C. Speziale', 'http://books.google.com/books/publisher/content?id=kiweEQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70XL_ySSiyDGHNE_-uH9GQmsnU9_OHcoJ1Z2sSBReOha9Cija6hE1M1cUWg-G3EDLGSSBco1lPwZ8r_WzhgDhEGTeZUgNIaWjFQXJ9e85b1NESpvPHn1cpEON7wuxA5KYXw-efr&source=gbs_api', 1, '2025-06-13 08:11:16'),
('kWuxngEACAAJ', 5, 'Bajo las estrellas de otoño', 'Knut Hamsun', NULL, 1, '2025-06-13 08:41:38'),
('MRoMUV2kLZEC', 5, 'Mysteries', 'Knut Hamsun', 'http://books.google.com/books/content?id=MRoMUV2kLZEC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE718MfXW-Y2w6EM3eTrvcnSZa2KFQ3WAaFj-TWYXUoB6zejSL0gnA004Iuk-0Q_jS4OXG0a2wBwKQIp1m0af6iKQqK9z3gsib-H86uDJb8dQl-pF9D-sYFukh4zAM_xpQrclT4vF&source=gbs_api', 1, '2025-06-13 08:41:27'),
('sBuqtAEACAAJ', 5, 'Novelas', 'Knut Hamsun', NULL, 1, '2025-06-13 08:41:34'),
('Sx7jtAEACAAJ', 5, 'Trilogía del vagabundo', 'Knut Hamsun', NULL, 1, '2025-06-13 08:41:30'),
('uR-wEAAAQBAJ', 5, 'Los ríos profundos (Edición conmemorativa de la RAE y la ASALE)', 'José María Arguedas', 'http://books.google.com/books/publisher/content?id=uR-wEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72SBvarlKLLUHioHiGrmziMdgk7y8HJ9HS_65WIbfH-akCVOskLA2I_Swa8fidsO26KaEjxg5M1-P8bYpZFlhK9zsVgPra4WCnseBz8KOM-AbvgzPP5MLkAzbX0HojZuuHjNHVX&source=gbs_api', 1, '2025-06-13 09:56:41'),
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

--
-- Volcado de datos para la tabla `peliculas_favoritas`
--

INSERT INTO `peliculas_favoritas` (`imdb_id`, `usuario_id`, `titulo`, `anio`, `poster_url`, `director`, `actores`, `genero`, `activo`) VALUES
('tt0074442', 5, 'Crime Busters', '1977', 'https://m.media-amazon.com/images/M/MV5BM2RiYTQ5YzgtMzEyMC00NWI0LWI1NTctOWM3YmJkNDdjMmMyXkEyXkFqcGc@._V1_SX300.jpg', 'Enzo Barboni', 'Terence Hill, Bud Spencer, David Huddleston', 'Action, Adventure, Comedy', 1),
('tt0107813', 5, 'Perry Mason: The Case of the Telltale Talk Show Host', '1993', 'https://m.media-amazon.com/images/M/MV5BMWE0MDgyYzAtMzk5NS00NjliLThjZjItZWVmMTMzOWJiNmZlXkEyXkFqcGc@._V1_SX300.jpg', 'Christian I. Nyby II', 'Raymond Burr, Barbara Hale, William R. Moses', 'Crime, Drama, Mystery', 1),
('tt0118655', 5, 'Austin Powers: International Man of Mystery', '1997', 'https://m.media-amazon.com/images/M/MV5BODQzM2UwODQtMzg1ZC00MTk4LTlkOTEtYzU4NDFhYmM1MWJkXkEyXkFqcGc@._V1_SX300.jpg', 'Jay Roach', 'Mike Myers, Elizabeth Hurley, Michael York', 'Adventure, Comedy', 1),
('tt0132347', 5, 'Mystery Men', '1999', 'https://m.media-amazon.com/images/M/MV5BNThiMzQ3ZmUtYzRmMS00Njg2LWEzM2EtNGI3ZTAwNmFiOTlkXkEyXkFqcGc@._V1_SX300.jpg', 'Kinka Usher', 'Ben Stiller, Janeane Garofalo, William H. Macy', 'Action, Comedy, Fantasy', 1),
('tt0330069', 5, 'Blue Collar Comedy Tour: The Movie', '2003', 'https://m.media-amazon.com/images/M/MV5BZjdlYzc1M2EtNzljZi00MTRlLWI4MjAtODg2ZjJmNWE5MjU5XkEyXkFqcGc@._V1_SX300.jpg', 'C.B. Harding', 'Jeff Foxworthy, Bill Engvall, Ron White', 'Comedy, Documentary', 1),
('tt0357563', 5, 'El conde Orsini', '1917', 'N/A', 'N/A', 'Inés Berutti, Lina Estévez, Pedro Gilardini', 'Crime', 0),
('tt12624584', 5, 'Untitled Horror Movie', '2021', 'https://m.media-amazon.com/images/M/MV5BYzgyNmFmMDUtMjc5OC00OTVjLWI2NzUtMzRhNWI4MmM2MTk1XkEyXkFqcGc@._V1_SX300.jpg', 'Nick Simon', 'Luke Baines, Darren Barnet, Timothy Granaderos', 'Comedy, Horror', 1),
('tt13304532', 5, 'The Sketch Comedy Movie', '2021', 'https://m.media-amazon.com/images/M/MV5BOWY2MTg3ZTgtZDZiMC00MGFjLWEzZjktNDNhODAwMWNiN2E5XkEyXkFqcGdeQXVyMTk2MzA5NTk@._V1_SX300.jpg', 'Dallas Ryan', 'Angie Bojorges, Chloe Gay Brewer, Ed McKenzie', 'Comedy', 1),
('tt15119154', 5, 'Life of Crime 1984-2020', '2021', 'https://m.media-amazon.com/images/M/MV5BODIzOGNmODEtYzA5OS00MWJiLTkyOGEtYmMyNTQwN2MzMTY5XkEyXkFqcGc@._V1_SX300.jpg', 'Jon Alpert', 'Deliris, Freddy, Robert', 'Documentary, Biography, Crime', 0),
('tt15675782', 5, 'The Invisible Man (2020) - Legacy of Horror', '2020', 'N/A', 'N/A', 'Matt Detisch, Alex Logan, Michael Rocco', 'Talk-Show', 1),
('tt1618434', 5, 'Murder Mystery', '2019', 'https://m.media-amazon.com/images/M/MV5BMzg4NDFjNmYtZjQxMy00MDY4LWEyZjUtYzRjNjNkNjJiZTZhXkEyXkFqcGc@._V1_SX300.jpg', 'Kyle Newacheck', 'Adam Sandler, Jennifer Aniston, Luke Evans', 'Crime, Mystery, Romance', 1),
('tt21191806', 5, 'Back in Action', '2025', 'https://m.media-amazon.com/images/M/MV5BMWQ4YWYxYTAtZTlhNC00Nzc3LWE3OWUtZjY5MThlNWNiYTBiXkEyXkFqcGc@._V1_SX300.jpg', 'Seth Gordon', 'Jamie Foxx, Cameron Diaz, McKenna Roberts', 'Action, Comedy', 0),
('tt22872224', 5, 'BT Sport Action Woman Awards 2022', '2022', 'https://m.media-amazon.com/images/M/MV5BNjY0ZGQyZTAtMmE2MS00YWY0LTgyNjAtYThjZjcwOTU5OWU0XkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg', 'N/A', 'Jo Ankier, Clare Balding, Sue Barker', 'Sport', 0),
('tt26930623', 5, 'El Buen Patrón (2021) #Drama #Trabajo #peliculas #audesc #podcast', '2021', 'https://m.media-amazon.com/images/M/MV5BNzhiNDlkOTUtYTZmOC00NWI4LTg4MGMtNzIyNjliYzBlMjZhXkEyXkFqcGc@._V1_SX300.jpg', 'N/A', 'N/A', 'Talk-Show', 1),
('tt27368541', 5, 'The Mystery of Inflation (2022)', '2022', 'https://m.media-amazon.com/images/M/MV5BZWI0NWYxMTMtZjE2OS00ZmM5LTlkMTYtYzcyYjc4NDhmYTlmXkEyXkFqcGdeQXVyODIyOTEyMzY@._V1_SX300.jpg', 'N/A', 'N/A', 'History', 1),
('tt3239932', 5, 'True Crime: The Movie', '2012', 'https://m.media-amazon.com/images/M/MV5BMTQ0NjQyMzk2Ml5BMl5BanBnXkFtZTgwOTA5ODE0NDE@._V1_SX300.jpg', 'Marijus Pikelis', 'Russell Wong, Avery Kidd Waddell, Gary Oldman', 'Documentary', 1),
('tt9652876', 5, 'Love Struck - A Valentine\'s Day Action Comedy', '2018', 'https://m.media-amazon.com/images/M/MV5BODRjMDVkZDItMDQ4ZS00NWIyLWExMDctM2JhYjhmZjdjZjkzXkEyXkFqcGdeQXVyNjYyNjI0MjA@._V1_SX300.jpg', 'Kyle Murillo', 'Josh Davidson, Sierra Estrada, Jordan Frey', 'Short', 0);

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
(4, 'admin@email.com', '$2a$10$pFu8jD.NsKfKOEE59r8cruM4/VimpMx.sAvDZ2Dl.N6HWQlyQ.I1a', 'admin', '2025-06-13 06:07:21.000000', NULL),
(5, 'din@email.com', '$2a$10$jK./91Jfly6KH1.Yigdb7.FW6rLwF4ZXlo5aR2HiE/gzjY8YbERdi', 'Dinah', '2025-06-13 06:53:49.000000', NULL);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
