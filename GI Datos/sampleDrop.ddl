ALTER TABLE administradores DROP FOREIGN KEY FK_administradores_IdUsuario
ALTER TABLE cajeros DROP FOREIGN KEY FK_cajeros_IdUsuario
ALTER TABLE pagos DROP FOREIGN KEY FK_pagos_IdVenta
ALTER TABLE productosventas DROP FOREIGN KEY FK_productosventas_IdVenta
ALTER TABLE productosventas DROP FOREIGN KEY FK_productosventas_IdProducto
ALTER TABLE ventas DROP FOREIGN KEY FK_ventas_IdCliente
ALTER TABLE ventas DROP FOREIGN KEY FK_ventas_RFC
ALTER TABLE ventas DROP FOREIGN KEY FK_ventas_IdUsuario
DROP TABLE usuarios
DROP TABLE administradores
DROP TABLE cajeros
DROP TABLE clientes
DROP TABLE doctores
DROP TABLE pagos
DROP TABLE productos
DROP TABLE productosventas
DROP TABLE ventas
