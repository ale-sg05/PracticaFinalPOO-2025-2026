package Servicios;

import Dominio.Cliente;
import Dominio.LineaPedido;
import Dominio.Pedido;
import Dominio.Producto;
import Interfaces.ServiciosPedidos;
import Repositorios.Clientes;
import Repositorios.Pedidos;
import Repositorios.Productos;

import static java.lang.IO.println;

public class ServPedido implements ServiciosPedidos {
    private final Pedidos repositorioPedidos;
    private final Clientes repositorioClientes;
    private final Productos repositorioProductos;

    public ServPedido (Clientes repositorioClientes, Productos repositorioProductos, Pedidos repositorioPedidos) {
        this.repositorioClientes = repositorioClientes;
        this.repositorioProductos = repositorioProductos;
        this.repositorioPedidos = repositorioPedidos;
    }

    @Override
    public void crearPedido(String producto, String cliente, float iva){
        if (producto == null) {
            throw new IllegalArgumentException("El código del pedido no puede ser nulo");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (iva < 0) {
            throw new IllegalArgumentException("El IVA no puede ser negativo");
        }

        Cliente cliente1 = (Cliente) repositorioClientes.recuperar(producto);
        Pedido pedido = new Pedido(cliente1, iva, null);

        repositorioPedidos.creacion(pedido);
    }

    @Override
    public void addLineaPedido(String producto, String pedido, int unidades) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que cero");
        }

        Pedido pedidoObj = (Pedido) repositorioPedidos.recuperar(pedido);

        pedido.add(new LineaPedido(producto, unidades, (float) producto.getPrecio()));
    }

    @Override
    public boolean confirmarPedido(Pedido pedido){

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        //validar que, para cada producto, Σ(unidades en el pedido) ≤
        //stock actual; si es válido, descontar stock y marcar como confirmado
        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto producto = linea.getProducto();
            int unidadesPedido = linea.getUnidades();

            Producto productoEnRepo = (Producto) repositorioProductos.recuperar(producto);
            if (productoEnRepo == null) {
                throw new IllegalArgumentException("El producto " + producto.getNombre() + " no existe en el repositorio");
            }

            if (unidadesPedido > productoEnRepo.getStock()) {
                return false; // No hay suficiente stock para este producto
            }
        }

        // Si llegamos aquí, hay suficiente stock para todos los productos
        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto producto = linea.getProducto();
            int unidadesPedido = linea.getUnidades();
            Producto productoEnRepo = (Producto) repositorioProductos.recuperar(producto);
            productoEnRepo.setStock(productoEnRepo.getStock() - unidadesPedido);
        }

        repositorioPedidos.creacion(pedido);

        if (!pedido.isConfirmado()) {
            pedido.setConfirmado(true);
        }

        return true;
    }

    @Override
    public float consultaTotalFacturado() {
        //calcular el total facturado de todos los pedidos confirmados
        float totalFacturado = 0;

        for (Pedido pedido : repositorioPedidos.listar()) {
            totalFacturado += pedido.calculateTotalWithIVA();
        }

        return totalFacturado;
    }
}
