import Dominio.Categoria;
import Repositorios.Clientes;
import Repositorios.Pedidos;
import Repositorios.Productos;
import Servicios.ServCliente;
import Servicios.ServPedido;
import Servicios.ServProducto;

public class Smoke {
    public static void main(String[] args) {

        //Los repositorios guardan una lista inmutable, por lo que no se puede modificar desde fuera. Entonces debo de inicializarla como null.

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

        System.out.println("\n== Alta de clientes ==");
        clientes.altaCliente("C001", "Ana Pérez", "600123123");
        clientes.altaCliente("C002", "Luis Gómez", "600456456");

        System.out.println("\n== Crear pedido C001 y añadir líneas ==");
        pedidos.crearPedido("PED-001", "C001", 0.21f);
        pedidos.addLineaPedido("PED-001", "P001", 2);
        pedidos.addLineaPedido("PED-001", "P002", 1);
        pedidos.addLineaPedido("PED-001", "P003", 3);

        Pedido ped = pedidos.getPedido("PED-001");
        System.out.printf("Total sin IVA:  %.2f €%n", ped.totalSinIVA());
        System.out.printf("Total con IVA:  %.2f €%n", ped.totalConIVA());

        System.out.println("\n== Confirmar pedido ==");
        pedidos.confirmar("PED-001");
        System.out.println("Confirmado: " + ped.isConfirmado());

        System.out.println("\n== Stock tras confirmar ==");
        for (Producto p : catalogo.listarProductos()) {
            System.out.printf("- %s: %s (stock=%d)%n", p.getId(), p.getNombre(), p.getStock());
        }

        System.out.println("\n== Total facturado (consultas) ==");
        System.out.printf("Facturado: %.2f €%n", pedidos.consultarTotalFacturado());

        System.out.println("\n== Añadir línea tras confirmar (debe fallar) ==");
        try {
            pedidos.anadirLinea("PED-001", "P001", 1);
            System.out.println("ERROR: no debería permitirse");
        } catch (Exception e) {
            System.out.println("OK (defensiva): " + e.getMessage());
        }

        System.out.println("\n== Eliminar producto en pedido ya confirmado (debe fallar) ==");
        try {
            catalogo.eliminarProducto("P001");
            System.out.println("ERROR: no debería permitirse");
        } catch (Exception e) {
            System.out.println("OK (defensiva): " + e.getMessage());
        }
        System.out.println("");
        System.out.println("Pedidos del cliente: C001");
        Cliente c = clientes.getCliente("C001");
        System.out.println(c.toString());
        for (Pedido p: pedidos.consultarPedidosPorCliente("C001")){
            System.out.println("Id: "+p.getId()+ ", "+ p.getCliente().toString());
            for (LineaPedido lp: p.lineas()){
                System.out.println("     "+lp.toString());
            }
        }

        System.out.println("\n-- FIN SMOKE --");
    }
}
