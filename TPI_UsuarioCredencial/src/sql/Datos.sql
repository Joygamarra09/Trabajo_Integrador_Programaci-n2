USE tpi_programacion2;

-- Usuarios
INSERT INTO usuario (username, email, activo)
VALUES
('emilce97', 'emilce@example.com', TRUE),
( 'juanperez44', 'juan.perez@example.com', TRUE),
('maria123', 'maria@example.com', FALSE),
('usuario4', 'u4@example.com', TRUE),
('usuario5', 'u5@example.com', TRUE),
('usuario6', 'u6@example.com', TRUE),
('usuario7', 'u7@example.com', TRUE),
('usuario8', 'u8@example.com', TRUE),
('usuario9', 'u9@example.com', TRUE),
('usuario10', 'u10@example.com', TRUE),
('usuario11', 'u11@example.com', FALSE),
('usuario12', 'u12@example.com', TRUE),
('usuario13', 'u13@example.com', TRUE),
('usuario14', 'u14@example.com', FALSE),
('usuario15', 'u15@example.com', TRUE),
('usuario16', 'u16@example.com', TRUE),
('usuario17', 'u17@example.com', FALSE),
('usuario18', 'u18@example.com', TRUE),
('usuario19', 'u19@example.com', TRUE),
('usuario20', 'u20@example.com', FALSE),
('usuario21', 'u21@example.com', TRUE),
('usuario22', 'u22@example.com', TRUE),
('usuario23', 'u23@example.com', FALSE),
('usuario24', 'u24@example.com', TRUE),
('usuario25', 'u25@example.com', TRUE);


-- Credenciales
INSERT INTO credencial_acceso (hash_password, salt, requiere_reset, usuario_id)
VALUES
(MD5('password1'), 'abc123', FALSE, 1),
(MD5('abc123'), 'xyz999', TRUE, 2),
(MD5('qwerty'), 'salt22', FALSE, 3),
(MD5('userpass4'), 's44ltA', TRUE, 29),
(MD5('pass_005'), 'k9lm2a', FALSE, 30),
(MD5('claveSegura6'), 'slt09B', FALSE, 31),
(MD5('resetme7'), 'rxt77', TRUE, 32),
(MD5('p455word8'), 'pp19x', FALSE, 33),
(MD5('mypass09'), 'slA3t', TRUE, 34),
(MD5('contraseña10'), 'ss10p', FALSE, 35),
(MD5('alpha11'), 'ttr21', TRUE, 36),
(MD5('beta_pass12'), 'salt12', FALSE, 37),
(MD5('gamma13'), 'mMn33', FALSE, 38),
(MD5('delta14'), 'saltD4', TRUE, 39),
(MD5('omega15'), '00ppx', FALSE, 40),
(MD5('clave16'), 'sa16lt', TRUE, 41),
(MD5('usuario17'), 'lt171', FALSE, 42),
(MD5('secure18'), 'xxy18', FALSE, 43),
(MD5('temp19'), 's19xx', TRUE, 44),
(MD5('acceso20'), 'sa20z', FALSE, 45),
(MD5('miclave21'), 'h2s21', FALSE, 46),
(MD5('login22'), 's22mm', TRUE, 47),
(MD5('token23'), 'aa23p', FALSE, 48),
(MD5('clave24'), 's24xyz', TRUE, 49),
(MD5('final25'), 'salt25', FALSE, 50);


