-- Usamos la base de datos
USE usuariocredencial;

-- 1
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Juan', 'Pérez', 'jperez', 'juan.perez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$fakehash.K1jL9p4pS.U2dF.wG3o', '$2a$10$fakesalt.K1jL9p4pS.', LAST_INSERT_ID());

-- 2
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('María', 'González', 'mgonzalez', 'maria.gonzalez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$anotherhash.L2kM0q5qT.V3eG.xH4p', '$2a$10$anothersalt.L2kM0q5qT.', LAST_INSERT_ID());

-- 3
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Carlos', 'Rodríguez', 'crodriguez', 'carlos.r@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$hashvalue.M3nN1r6rU.W4fH.yI5q', '$2a$10$saltvalue.M3nN1r6rU.', LAST_INSERT_ID());

-- 4
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Ana', 'Martínez', 'amartinez', 'ana.martinez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$securepass.N4oO2s7sV.X5gI.zJ6r', '$2a$10$securesalt.N4oO2s7sV.', LAST_INSERT_ID());

-- 5
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Luis', 'Sánchez', 'lsanchez', 'luis.s@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$userpass.O5pP3t8tW.Y6hJ.aK7s', '$2a$10$usersalt.O5pP3t8tW.', LAST_INSERT_ID());

-- 6
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Sofía', 'Gómez', 'sgomez', 'sofia.gomez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$pass123.P6qQ4u9uX.Z7iK.bL8t', '$2a$10$salt123.P6qQ4u9uX.', LAST_INSERT_ID());

-- 7
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Javier', 'Díaz', 'jdiaz', 'javier.diaz@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$hashed.Q7rR5v0vY.A8jL.cM9u', '$2a$10$salted.Q7rR5v0vY.', LAST_INSERT_ID());

-- 8
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Laura', 'Hernández', 'lhernandez', 'laura.h@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$loginhash.R8sS6w1wZ.B9kM.dN0v', '$2a$10$loginsalt.R8sS6w1wZ.', LAST_INSERT_ID());

-- 9
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Miguel', 'Álvarez', 'malvarez', 'miguel.alvarez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$datahash.S9tT7x2x.C0lN.eO1w', '$2a$10$datasalt.S9tT7x2x.', LAST_INSERT_ID());

-- 10
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Elena', 'Muñoz', 'emunoz', 'elena.munoz@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$keyhash.T0uU8y3y.D1mO.fP2x', '$2a$10$keysalt.T0uU8y3y.', LAST_INSERT_ID());

-- 11
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Pedro', 'Jiménez', 'pjimenez', 'pedro.j@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$example.U1vV9z4z.E2nP.gQ3y', '$2a$10$examplesalt.U1vV9z4z.', LAST_INSERT_ID());

-- 12
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Carmen', 'Ruiz', 'cruiz', 'carmen.ruiz@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$testpass.V2wW0a5a.F3oQ.hR4z', '$2a$10$testsalt.V2wW0a5a.', LAST_INSERT_ID());

-- 13
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Diego', 'Alonso', 'dalonso', 'diego.alonso@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$another.W3xX1b6b.G4pR.iS5A', '$2a$10$anothersalt.W3xX1b6b.', LAST_INSERT_ID());

-- 14
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Lucía', 'García', 'lgarcia', 'lucia.garcia@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$password.X4yY2c7c.H5qS.jT6B', '$2a$10$passwordsalt.X4yY2c7c.', LAST_INSERT_ID());

-- 15
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Adrián', 'López', 'alopez', 'adrian.lopez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$loginval.Y5zZ3d8d.I6rT.kU7C', '$2a$10$loginsalt.Y5zZ3d8d.', LAST_INSERT_ID());

-- 16
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Paula', 'Moreno', 'pmoreno', 'paula.m@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$hashsample.Z6aA4e9e.J7sU.lV8D', '$2a$10$saltsample.Z6aA4e9e.', LAST_INSERT_ID());

-- 17
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Manuel', 'Vázquez', 'mvazquez', 'manuel.vazquez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$newhash.A7bB5f0f.K8tV.mW9E', '$2a$10$newsalt.A7bB5f0f.', LAST_INSERT_ID());

-- 18
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Isabel', 'Domínguez', 'idominguez', 'isabel.d@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$finalhash.B8cC6g1g.L9uW.nX0F', '$2a$10$finalsalt.B8cC6g1g.', LAST_INSERT_ID());

-- 19
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('David', 'Ortega', 'dortega', 'david.ortega@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$mockdata.C9dD7h2h.M0vX.oY1G', '$2a$10$mocksalt.C9dD7h2h.', LAST_INSERT_ID());

-- 20
INSERT INTO usuario (nombre, apellido, username, email) 
VALUES ('Sara', 'Núñez', 'snunez', 'sara.nunez@example.com');
INSERT INTO credencial (contraseña, salt, id_usuario) 
VALUES ('$2a$10$lastexample.D0eE8i3i.N1wY.pZ2H', '$2a$10$lastsalt.D0eE8i3i.', LAST_INSERT_ID());
