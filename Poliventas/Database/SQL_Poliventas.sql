
CREATE DATABASE IF NOT EXISTS PoliventasDB ;
USE  PoliventasDB;


CREATE TABLE IF NOT EXISTS Personas (
   Cedula CHAR(10) NOT NULL PRIMARY KEY ,
   Nombres VARCHAR(200) NOT NULL,
   Apellidos VARCHAR(200) NOT NULL,
   tieneWhatsapp TINYINT,
   telefonoActual VARCHAR(45) NOT NULL
   );
   

CREATE TABLE IF NOT EXISTS Estudiantes (
   Matricula INT NOT NULL PRIMARY KEY,
   idStudent CHAR(10) NOT NULL,
   Usuario VARCHAR(45) NOT NULL,
   Contraseña VARCHAR(45) NOT NULL,
   Rol VARCHAR(45) NOT NULL,
   EsVisible TINYINT DEFAULT 1,
  
   FOREIGN KEY (idStudent) REFERENCES Personas (Cedula)
   );

   
   CREATE TABLE IF NOT EXISTS Vendedores (
   idVendedor INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idSeller INT NOT NULL,
   CalificacionPromedioVendedor INT NOT NULL,
  
   FOREIGN KEY (idSeller) REFERENCES Estudiantes (Matricula)
   );
   
   CREATE TABLE IF NOT EXISTS Productos (
   idProducto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idProvider  INT NOT NULL,
   Nombre VARCHAR(200) NOT NULL,
   PrecioUnitario DOUBLE NOT NULL,
   Categoria VARCHAR(200),
   Descripcion VARCHAR(200) NOT NULL,
   CantidadDisponible INT NOT NULL,
   TiempoMaxEntrega TIME NOT NULL,
   CalificacionPromedioProducto INT,
   NroBusquedas INT,
   FechaIngreso DATE,
   EsVisible TINYINT DEFAULT 1,
  
   FOREIGN KEY (idProvider) REFERENCES Vendedores (idVendedor)
   );


CREATE TABLE IF NOT EXISTS PersonasCorreos (
    idPersonaCorreo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPersonEmail CHAR(10) NOT NULL,
    Correo VARCHAR(45) NOT NULL,

    FOREIGN KEY (idPersonEmail) REFERENCES Personas (Cedula)
    );

CREATE TABLE IF NOT EXISTS PersonasTelefonos (
    idPersonaTelefono INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPersonPhone CHAR(10) NOT NULL,
    Telefono VARCHAR(45) NOT NULL,

    FOREIGN KEY (idPersonPhone) REFERENCES Personas (Cedula)
   );

CREATE TABLE IF NOT EXISTS PersonasDomicilios (
    idPersonaDomicilio INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPersonHome CHAR(10) NOT NULL,
    Direccion VARCHAR(200) NOT NULL,
  
    FOREIGN KEY (idPersonHome) REFERENCES Personas (Cedula)
   );


CREATE TABLE IF NOT EXISTS Compradores (
   idComprador INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idBuyer INT NOT NULL,
   SaldoDisponible DOUBLE NOT NULL,
  
   FOREIGN KEY (idBuyer) REFERENCES Estudiantes (Matricula)
   );
   
   
CREATE TABLE IF NOT EXISTS Operaciones (
    idOperacion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPerson1  INT NOT NULL,
    idPerson2 INT NOT NULL,
    FechaHora  DATETIME NOT NULL,
    Tipo  VARCHAR(45) NOT NULL,
   
    FOREIGN KEY (idPerson1) REFERENCES Vendedores (idVendedor),
    FOREIGN KEY (idPerson2) REFERENCES Compradores (idComprador)
    );
    
CREATE TABLE IF NOT EXISTS ComprasVentas (
   idCompraVenta INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idTrading INT  NOT NULL,
   MontoTotal DOUBLE NOT NULL,
   Estado VARCHAR(45) NOT NULL,
   
   FOREIGN KEY (idTrading) REFERENCES Operaciones(idOperacion)

   );
   
   
CREATE TABLE IF NOT EXISTS Pagos (
   idPago INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idPay INT  NOT NULL,
   idPurchase INT NOT NULL,
   ValorAbonado DOUBLE NOT NULL,
   FormaPago VARCHAR(45) NOT NULL,
  
   FOREIGN KEY (idPay) REFERENCES  Pagos(idPago),
   FOREIGN KEY (idPurchase) REFERENCES ComprasVentas(idCompraVenta)

   );
   
CREATE TABLE IF NOT EXISTS PagosEfectivo (
   idPagoEfectivo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idPayCash INT  NOT NULL,
   ValorEntregado DOUBLE NOT NULL,
   Cambio DOUBLE NOT NULL,
  
   FOREIGN KEY (idPayCash) REFERENCES  Pagos(idPago)

   );
  
  CREATE TABLE IF NOT EXISTS Entregas (
  idEntrega INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  idDelivery INT NOT NULL,
  idPedido INT NOT NULL,
  LocalizacionQuiosco VARCHAR(200) NOT NULL,
  Observacion VARCHAR(200) NOT NULL,
  
  FOREIGN KEY (idDelivery)REFERENCES Operaciones (idOperacion),
  FOREIGN KEY (idPedido) REFERENCES ComprasVentas (idCompraVenta)
  );
  
CREATE TABLE IF NOT EXISTS ComprasVentasProductos (
   idCompraVentaProducto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idSellBuyProduct INT NOT NULL,
   idArticulo INT  NOT NULL,
   Cantidad INT NOT NULL,
  
   FOREIGN KEY (idArticulo) REFERENCES Productos(idProducto),
   FOREIGN KEY (idSellBuyProduct) REFERENCES ComprasVentas(idCompraVenta)

   );

CREATE TABLE IF NOT EXISTS ComprasSaldos (
   idCompraSaldo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idCostumer INT NOT NULL,
   FechaHora INT NOT NULL,
   CelularAsociado VARCHAR(45) NOT NULL,
   Saldo DOUBLE NOT NULL,
  
   FOREIGN KEY (idCostumer) REFERENCES Compradores (idComprador)
   );
   
CREATE TABLE IF NOT EXISTS EntregasProductos (
   idEntregaProducto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idDeliveryProduct INT  NOT NULL,
   idArticle INT NOT NULL,
   CantidadEntregada INT NOT NULL,
   EsProductoValido TINYINT,
  
   FOREIGN KEY (idDeliveryProduct) REFERENCES Entregas(idEntrega),
   FOREIGN KEY (idArticle) REFERENCES Productos(idProducto)

   );

CREATE TABLE IF NOT EXISTS CalificacionesVendedores (
   idCalificacionVendedor INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idScoreSeller INT  NOT NULL,
   idQualifierSeller INT NOT NULL,
   Calificacion INT NOT NULL,
  
   FOREIGN KEY (idScoreSeller) REFERENCES  Vendedores(idVendedor),
   FOREIGN KEY (idQualifierSeller) REFERENCES Compradores(idComprador)

   );
   
CREATE TABLE IF NOT EXISTS CalificacionesProductos (
   idCalificacionProducto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   idScoreProduct INT  NOT NULL,
   idQualifierProduct INT NOT NULL,
   Calificacion INT NOT NULL,
  
   FOREIGN KEY (idScoreProduct) REFERENCES  Productos(idProducto),
   FOREIGN KEY (idQualifierProduct) REFERENCES Compradores(idComprador)

   );
   
#Stored Procedures para crear tablas 
   
DELIMITER //
CREATE PROCEDURE createPersona(
	IN cedula CHAR(10),
	IN nombres VARCHAR(200),
    IN apellidos VARCHAR(200),
	IN tipo VARCHAR(45)
	)
BEGIN
    INSERT INTO Personas
    VALUES (idPersona, nombre, tipo);
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createPersonaTelefono(
	IN idPersonPhone CHAR(10),
	IN telefono VARCHAR(45)
	)
BEGIN
    INSERT INTO PersonasTelefonos(idPersonPhone, Telefono)
    VALUES (idPersonPhone, telefono);
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createPersonaCorreo(
	IN idPersonEmail CHAR(10),
	IN correo VARCHAR(45)
	)
BEGIN
    INSERT INTO PersonasCorreos(idPersonEmail, Correo)
    VALUES (idPersonEmail, correo);
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createPersonaDomicilio(
	IN idPersonHome CHAR(10),
	IN direccion VARCHAR(200)
	)
BEGIN
    INSERT INTO PersonasDomicilios(idPersonHome, Direccion)
    VALUES (idPersonHome, direccion);
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createEstudiante(
	IN matricula CHAR(10),
	IN idStudent VARCHAR(200),
    IN usuario VARCHAR(45),
    IN contraseña VARCHAR(45),
    IN rol VARCHAR(45)
	)
BEGIN
    INSERT INTO Estudiantes(Matricula, idStudent, Usuario, Contraseña, Rol)
    VALUES (matricula, idStudent, usuario, contraseña, rol);
END
//
DELIMITER ;






DELIMITER //
CREATE PROCEDURE createComprador(
	IN idBuyer INT
	IN saldoDisponible DOUBLE
	)
BEGIN
    INSERT INTO Compradores(idBuyer, SaldoDisponible)
    VALUES (idBuyer, saldoDisponible) ;
END
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE createVendedor(
	IN idSeller INT
	IN calificacionPromedio INT 
	)
BEGIN
    INSERT INTO Vendedores(idSeller, CalificacionPromedio)
    VALUES (idSeller, caliicacionPromedio);
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE createCompraSaldo(
	IN idCostumer INT,
	IN fechaHora DATETIME,
    IN celularAsociado VARCHAR(45),
    IN saldo DOUBLE
	)
BEGIN
    INSERT INTO ComprasSaldos(idCostumer, FechaHora, CelularAsociado, Saldo)
    VALUES (idCostumer, fechaHora, celularAsociado, saldo);
END
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE createProducto(
	IN idProvider INT,
	IN nombre VARCHAR(200),
    IN precioUnitario DOUBLE,
   IN categoria VARCHAR(200),
   IN descripcion VARCHAR(200) ,
   IN cantidadDisponible INT ,
   IN tiempoMaxEntrega TIME,
   IN calificacionPromedioProducto INT,
   IN nroBusquedas INT,
   IN fechaIngreso DATE
   )
BEGIN
    INSERT INTO ComprasSaldos(idProvider, Nombre, PrecioUnitario, Categoria, Descripcion, CantidadDisponible, TiempoMaxEntrega, CalificacionPromedioProducto, NroBusquedas, FechaIngreso)
    VALUES (idProvider, nombre, precioUnitario, categoria, descripcion, cantidadDisponible, tiempoMax, calificacionPromedioProducto, nroBusquedas, fechaIngreso);
END
//
DELIMITER ;




#Extras
DELIMITER //
CREATE PROCEDURE readEstudianteXMatricula(
	IN matricula INT
	)
BEGIN
	SELECT P.Cedula, P.Nombres, P.Apellidos, P.telefonoActual
    FROM Estudiantes E, Pacientes P
    WHERE matricula=E.Matricula AND E.idStudent=P.Cedula;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE readCedulaCorreos(
	IN cedula CHAR(10)
	)
BEGIN
	SELECT Correo
    FROM PersonasCorreos
    WHERE cedula=idPersonEmail;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE readCedulaTelefonos(
	IN cedula CHAR(10)
	)
BEGIN
	SELECT Telefono
    FROM PersonasTelefonos
    WHERE cedula=idPersonPhone;
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE readCedulaDomicilios(
	IN cedula CHAR(10)
	)
BEGIN
	SELECT Direccion
    FROM PersonasDomicilios
    WHERE cedula=idPersonHome;
END
//
DELIMITER ;




#Stored Procedures para la Busqueda Sencilla

DELIMITER //
CREATE PROCEDURE readProductoNombre(
	IN nombre VARCHAR(200)
	)
BEGIN
    SELECT P.IdProducto, P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadDisponible, P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso, V.CalificacionPromedioVendedor
    FROM Productos P, Vendedores V
    WHERE (nombre=P.Nombre OR P.Nombre LIKE concat(nombre,"%")) AND P.idProvider=V.idVendedor;
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE readProductoDescripcion(
	IN descripcion VARCHAR(200)
	)
BEGIN
    SELECT  P.IdProducto, P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadDisponible, P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso, V.CalificacionPromedioVendedor
    FROM Productos P, Vendedores V
    WHERE  (descripcion=P.Descripcion OR P.Nombre LIKE concat(nombre,"%")) AND P.idProvider=V.idVendedor ;
END
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE readProductoNombreDescripcion(
	IN nombre VARCHAR(200),
    IN descripcion VARCHAR(200)
	)
BEGIN
    SELECT P.IdProducto, P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadDisponible, P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso, V.CalificacionPromedioVendedor
    FROM Productos P , Venderores V
    WHERE (nombre=P.Nombre OR P.Nombre LIKE concat(nombre,"%")) AND (descripcion=P.Descripcion OR P.Descripcion LIKE concat(descripcion,"%"))  ;
END
//
DELIMITER ;


#Stored Procedures para las Compras Pendientes


DELIMITER //
CREATE PROCEDURE readComprasPendientes(
	IN matricula INT,
    IN tipo VARCHAR(45)
	)
BEGIN
	IF tipo='Compra' THEN
		SELECT O.FechaHora, O.idPerson1, Cv.MontoTotal, Cv.idCompraVenta
		FROM Operaciones O, ComprasVentas Cv, Compradores Co
		WHERE matricula=Co.idBuyer AND O.idPerson2=Co.idComprador AND O.Tipo='CompraVenta' AND O.idOperacion=Cv.idTrading AND Cv.Estado='Pendiente';
        
	ELSEIF Tipo='Venta' THEN
		SELECT O.FechaHora, O.idPerson2, Cv.MontoTotal, Cv.idCompraVenta
		FROM Operaciones O, ComprasVentas Cv, Vendedores V
		WHERE matricula=V.idSeller AND O.idPerson1=V.idVendedor AND O.Tipo='CompraVenta' AND O.idOperacion=Cv.idTrading AND Cv.Estado='Pendiente';
    END IF ;
END
//
DELIMITER ;

# no es necesario tener de columna la calificacion promedio del vendedor, por alguna razon los escogio
DELIMITER //
CREATE PROCEDURE readComprasVentasProductos(
	IN idCompraVenta INT
	)
BEGIN
	SELECT P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadEntregada ,P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso
    FROM Productos P, ComprasVentasProducto CVp
    WHERE idCompraVenta=CVp.idDeliveryProduct ;
END
//
DELIMITER ;

#

DELIMITER //
CREATE PROCEDURE readProductosMasBuscados()
BEGIN
	SELECT P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadDisponible, P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso, V.CalificacionPromedioVendedor
    FROM Productos P, ComprasVentasProducto CVp
	ORDER BY NroBusquedas DESC;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE readMisProductos(
	IN matriculaVendedor INT
)
BEGIN
	SELECT P.Nombre, P.PrecioUnitario, P.Categoria, P.Descripcion, P.CantidadDisponible, P.TiempoMaxEntrega, P.CalificacionPromedioProducto, P.NroBusquedas, P.FechaIngreso
    FROM Productos P,  Vendedores V
	WHERE matriculaVendedor=V.idSeller AND V.idVendedor=P.idProvider ;
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE updateVisibleProducto(
    )
BEGIN
	UPDATE Productos
    SET EsVisible=0;
END
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE updateVisibleEstudiante(
    )
BEGIN
	UPDATE Estudiantes
    SET EsVisible=0;
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE verificarEstudiante(
	IN usuario VARCHAR(45),
    IN psw VARCHAR(45)
    )
BEGIN
	SELECT Matricula
    FROM	Estudiantes
    WHERE usuario=Usuario AND psw=Contraseña;
END
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE obtenerNombreRolEstudiante(
	IN matricula VARCHAR(45)
    )
BEGIN
	SELECT Nombre, Rol
    FROM	Estudiantes
    WHERE matricula=Matricula;
END
//
DELIMITER ;

insert into Personas
values('0926688391', 'Luis Antonio', 'Sarango Parrales', 1, '0923754858'),
('0953666972', 'juan beto', 'jimenez figueroa', 1, '0923754858');

insert into Estudiantes(Matricula, idStudent, Usuario, Contraseña, Rol)
values(201515424, '0926688391', 'Lasarang', 'password', 'Vendedor'),
		(201563748, '0953666972', 'betoMelenas', 'password', 'Compradorr');


insert into Vendedores(idSeller, CalificacionPromedioVendedor)
values(201515424, 5);

insert into Compradores(idBuyer,SaldoDisponible)
values(201563748, 500);

insert into Operaciones( idPerson1, idPerson2,FechaHora, Tipo)
values(1, 1, DATE("2019-01-15 09:34:21"), 'CompraVenta'),
		  (1, 1, DATE("2019-01-15 10:34:21"), 'CompraVenta');

insert into ComprasVentas(idTrading, MontoTotal, Estado)
values(1, 50, 'Pendiente');

            

insert into Productos(idProvider , Nombre, PrecioUnitario, Categoria, Descripcion , CantidadDisponible , TiempoMaxEntrega , CalificacionPromedioProducto , NroBusquedas , FechaIngreso)
values(1, 'Audifonos mostrosos', 5, 'Tecnologia', 'Son audifonos alambricos de marca china', 60, TIME("01:30:10"), 5, 20, DATE("2019-01-12")),
		   (1, 'Camisetas T', 5, 'Tecnologia', 'Camisetas personalizables de todos los colores', 50, TIME("00:05:10"), 4, 20, DATE("2019-01-12")),
	       (1, 'Seco de pato', 5, 'Comida', 'Comida 100% manaba', 50, TIME("00:40:10"), 5, 150, DATE("2019-01-12")),
           (1, 'Micas', 5, 'Tecnologia', 'Son micas de vidrio que se rompen facilmente', 50, TIME("00:30:09"), 3, 14, DATE("2019-01-12"));

insert into ComprasVentasProductos(idSellBuyProduct ,idArticulo ,Cantidad)
values(1, 1, 2),
			(1, 2, 4);   
            
  
SELECT * FROM ComprasVentas;


#CALL verificarEstudiante('vh', '0i0' );
CALL readProductoNombre('Audi');
#drop database PoliventasDB;
CALL readComprasPendientes(201563748, 'Compra' );
