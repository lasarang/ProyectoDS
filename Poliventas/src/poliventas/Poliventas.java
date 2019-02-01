/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poliventas;

import ClasesAuxiliares.CompraProducto;
import ClasesAuxiliares.InfoProducto;
import ClasesAuxiliares.Producto;
import Extra.Validate;
import FamiliaOperacion.Compra;
import FamiliaPersona.Administrador;
import FamiliaPersona.Comprador;
import FamiliaPersona.Vendedor;
import PatronDAO.Compra.CompraDAOImpl;
import PatronDAO.Estudiante.EstudianteDAOImpl;
import PatronDAO.Producto.ProductoDAOImpl;
import PatronSingleton.Conexion;
import PatronStrategy.PagoElectronico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Poliventas extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Main.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try {
            Conexion conexion = Conexion.getInstancia();
            conexion.conectar();
            launch(args);
            
            ProductoDAOImpl pdao = new ProductoDAOImpl();
            CompraDAOImpl cdo = new CompraDAOImpl();
            
            /* System.out.println("validando cadena: " + Validate.normalizeString("ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏ __$&*@!#+!!-\\{}:;= U.S. Art Supply 5.5\" x 8.5\" Premium Spiral Bound Sketch Pad, Pad of 100-Sheets, 60 Pound (100gsm) (Pack of 2 Pads)"));
            
            Comprador comp = new Comprador();
            
            System.out.println("Busqueda sencilla por descripcion: " + comp.realizarBusquedaSencillaDescripcion("Tecnologia"));
            System.out.println("Busqueda sencilla por nombre: " + comp.realizarBusquedaSencillaNombre("Audífoños   "));
            comp.setMatricula(201563748);
            System.out.println("Compras pendientes: " + comp.obtenerComprasPendientes());
            
            Vendedor vend = new Vendedor();
            vend.setMatricula(201915424);
            System.out.println("Ventas pendientes: " + vend.obtenerComprasPendientes());
            System.out.println("Mis Productos: " + vend.getMisProductos());
            EstudianteDAOImpl estudianteDao = new EstudianteDAOImpl();
            Administrador admi = new Administrador();
            
            admi.setCedula("1102371802");
            admi.setNombres("Joseph");
            admi.setApellidos("Stalin");
            admi.setTieneWhatsapp(true);
            admi.setTelefonoActual("09874553889");
            
            List<String> correos = new ArrayList<>();
            correos.add("correo1@espol.edu.ec");
            correos.add("correo2@gmail.com");
            admi.setCorreos(correos);
            
            List<String> telefonosSecundarios = new ArrayList<>();
            telefonosSecundarios.add("099787654332");
            telefonosSecundarios.add("232354665");
            admi.setTelefonosSecundarios(telefonosSecundarios);
            
            List<String> domicilios = new ArrayList<>();
            domicilios.add("direccion 1");
            domicilios.add("direccion 2");
            admi.setDomicilios(domicilios);
            
            admi.setMatricula(201515424);
            admi.setUsuario("Admi");
            admi.setContraseña("password");
            
            //estudianteDao.create(admi);
            Producto pd = new Producto();
            InfoProducto ip = new InfoProducto();
            ip.setNombre("Almohada viajera");
            ip.setCantidadDisponible(10);
            ip.setDescripcion("Cojin para dormir en un viaje largo");
            ip.setPrecioUnitario(25);
            ip.setTiempoMaxEntrega(LocalTime.parse("00:30:00", DateTimeFormatter.ofPattern("HH:mm:ss")));
            pd.setIdProvider(1);
            pd.setFechaIngreso(LocalDate.now());
            pd.setInfoProducto(ip);
            
            //admi.registrarProducto(pd);
            //admi.eliminarProducto(1);
            Map<String, Object> nuevosValores = new HashMap<>();
            
            nuevosValores.put("Contraseña", "nueva password");
            // admi.actualizarEstudiante(nuevosValores, 201915424);
            
            // System.out.println("Consulta de estudiante: " + admi.buscarEstudiante(201815424));
            Vendedor vendedor = new Vendedor();
            vendedor.setCedula("0926688383");
            vendedor.setNombres("NombreVendedor1");
            vendedor.setApellidos("ApellidoVendedor1");
            vendedor.setTieneWhatsapp(true);
            vendedor.setTelefonoActual("0976454224");
            vendedor.setTelefonosSecundarios(telefonosSecundarios);
            vendedor.setDomicilios(domicilios);
            vendedor.setCorreos(correos);
            
            vendedor.setMatricula(201715424);
            vendedor.setUsuario("Vendedor");
            vendedor.setContraseña("password");
            
            //admi.registrarEstudiante(vendedor);
            Comprador comprador = new Comprador();
            comprador.setCedula("0987654321");
            comprador.setNombres("NombreComprador1");
            comprador.setApellidos("ApellidoComprador1");
            comprador.setTieneWhatsapp(true);
            comprador.setTelefonoActual("123456789");
            comprador.setTelefonosSecundarios(telefonosSecundarios);
            comprador.setDomicilios(domicilios);
            comprador.setCorreos(correos);
            
            comprador.setMatricula(201815424);
            comprador.setUsuario("Comprador");
            comprador.setContraseña("password");
            
            //admi.registrarEstudiante(comprador);
            //se debe setear en el inicio de sesion
            int idComprador = estudianteDao.obtenerIdComprador(comprador.getMatricula());
            
            Compra compra = new Compra();
            
            Vendedor vendedorNotificado = new Vendedor(compra);
            
            compra.setIdComprador(idComprador);
            compra.setFechaHora(LocalDateTime.now());
            
            int idVendedor = 0;
            List<CompraProducto> carrito = new ArrayList<>();
            CompraProducto cp = null;
            for (Producto p : comprador.realizarBusquedaSencillaDescripcion("Tecnologia")) {
                cp = new CompraProducto();
                cp.setProducto(p);
                cp.setCantidad(2);
                carrito.add(cp);
                idVendedor = p.getIdProvider();
            }
            compra.setProductos(carrito);
            compra.setIdVendedor(idVendedor);
            
            PagoElectronico pago = new PagoElectronico();
            pago.setFechaHora(LocalDateTime.now());
            pago.setValorAbonado(compra.getMontoTotal());
            
            comprador.setCompra(compra);
            comprador.pagarNuevaCompra(pago);*/
            
            conexion.desconectar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
