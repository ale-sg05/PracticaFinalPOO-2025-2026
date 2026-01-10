import Dominio.Categoria;
import Repositorios.Clientes;
import Repositorios.Pedidos;
import Repositorios.Productos;
import Servicios.ServCliente;
import Servicios.ServPedido;
import Servicios.ServProducto;
import Dominio.Pedido;
import Dominio.Producto;
import Dominio.Cliente;
import Dominio.LineaPedido;

public class Smoke {
    public static void main(String[] args) {

        Productos repositorioProductos = new Productos();
        Pedidos repositorioPedidos = new Pedidos();
        Clientes repositorioClientes = new Clientes();

        ServProducto productos  = new ServProducto(repositorioProductos, repositorioPedidos);
        ServCliente clientes  = new ServCliente(repositorioClientes);
        ServPedido pedidos   = new ServPedido(repositorioClientes, repositorioProductos, repositorioPedidos);

        System.out.println("== Alta de productos ==");
        productos.altaProducto("P001", "Café molido 250g", Categoria.ALIMENTACION, 3.95f, 50);
        productos.altaProducto("P002", "Taza cerámica", Categoria.HOGAR, 4.50f, 20);
        productos.altaProducto("P003", "Filtro café", Categoria.ACCESORIOS, 1.10f, 100);

        try{//No debe dejar dar de alta este producto (cambiar valores para ir probando errores)
            productos.altaProducto("P004", "Café en grano 1kg", Categoria.ALIMENTACION, -12.95f, 30);
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("== Ajuste de Stock ==");
        try{
            productos.ajusteStock("P001", 30);
            for (Producto p : productos.listarProductos()) {
                System.out.printf("- %s: %s (stock=%d)%n", p.getCodigo(), p.getNombre(), p.getStock());
            }
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n== Alta de clientes ==");
        clientes.altaCliente("C001", "Ana Pérez", "600123123");
        clientes.altaCliente("C002", "Luis Gómez", "600456456");

        try{//No debe dejar dar de alta este cliente (cambiar valores para ir probando errores)
            clientes.altaCliente("", "María López", "600789789");
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n== Crear pedido C001 y añadir líneas ==");
        pedidos.crearPedido("PED-001", "C001", 0.21f);
        pedidos.addLineaPedido("PED-001", "P001", 2);
        pedidos.addLineaPedido("PED-001", "P002", 1);
        pedidos.addLineaPedido("PED-001", "P003", 3);

        try{// No debe dejar crear este pedido (cambiar valores para ir probando errores)
            pedidos.crearPedido("", "C001", 0.21f);
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        try{ // No debe dejar añadir esta línea (cambiar valores para ir probando errores)
            pedidos.addLineaPedido("PED-001", "P004", -6);
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        Pedido ped = (Pedido) repositorioPedidos.recuperar("PED-001");
        System.out.printf("Total sin IVA:  %.2f €%n", ped.calculateTotalWithoutIVA());
        System.out.printf("Total con IVA:  %.2f €%n", ped.calculateTotalWithIVA());

        System.out.println("\n== Confirmar pedido ==");
        // Si falla (por ej prueba con PED-00), el servicio maneja las excepciones,
        // y por consiguiente más adelante no fallará lo que debería saltandonos ese mensaje por pantalla
        pedidos.confirmarPedido("PED-001");
        System.out.println("Confirmado: " + ped.isConfirmado());

        System.out.println("\n== Stock tras confirmar ==");
        for (Producto p : productos.listarProductos()) {
            System.out.printf("- %s: %s (stock=%d)%n", p.getCodigo(), p.getNombre(), p.getStock());
        }

        System.out.println("\n== Total facturado (consultas) ==");
        System.out.printf("Facturado: %.2f €%n", pedidos.consultaTotalFacturado());

        System.out.println("\n== Añadir línea tras confirmar (debe fallar) ==");
        try {
            pedidos.addLineaPedido("PED-001", "P001", 1);
            System.out.println("ERROR: no debería permitirse");
        } catch (Exception e) {
            System.out.println("OK (defensiva): " + e.getMessage());
        }

        System.out.println("\n== Eliminar producto en pedido ya confirmado (debe fallar) ==");
        try {
            productos.eliminacionRestringida("P001");
            System.out.println("ERROR: no debería permitirse");
        } catch (Exception e) {
            System.out.println("OK (defensiva): " + e.getMessage());
        }

        System.out.println("");


        System.out.println("\nPedidos del cliente: C001");
        Cliente c = (Cliente) repositorioClientes.recuperar("C001");
        System.out.println(c.toString());
        for (Pedido p: pedidos.consultarPedidosPorCliente("C001")){
            System.out.println("Id: "+p.getCliente().getCodigo()+ ", "+ p.getCliente().toString());
            for (LineaPedido lp: p.getLineasPedido()){
                System.out.println("     "+lp.toString());
            }
        }


        System.out.println("==  Consultas   ==");
        System.out.println("Consulta de productos: " + productos.consulta("café"));
        System.out.println("Consulta de clientes: " + clientes.consulta("Ana"));

        System.out.println("\n-- FIN SMOKE --");
    }
}
