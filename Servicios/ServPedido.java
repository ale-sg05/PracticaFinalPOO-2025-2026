package Servicios;

import Dominio.Cliente;
import Dominio.LineaPedido;
import Dominio.Pedido;
import Dominio.Producto;
import Interfaces.ServiciosPedidos;
import Repositorios.Clientes;
import Repositorios.Pedidos;
import Repositorios.Productos;

import java.util.ArrayList;
import java.util.List;

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
    public void crearPedido(String pedido, String cliente, float iva){
        if (pedido == null) {
            throw new IllegalArgumentException("El código del pedido no puede ser nulo");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (iva < 0) {
            throw new IllegalArgumentException("El IVA no puede ser negativo");
        }


        try{
            Cliente cliente1 = (Cliente) repositorioClientes.recuperar(cliente);
            Pedido pedido1 = new Pedido(pedido, cliente1, iva, new ArrayList<LineaPedido>());

            repositorioPedidos.creacion(pedido1);

            System.out.println("Éxito en alta de pedido con código: " + pedido);
        }catch (IllegalArgumentException e){
            System.out.println("Error al dar de alta el pedido: " + e.getMessage());
        }
    }

    @Override
    public void addLineaPedido(String pedido, String producto, int unidades) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que cero");
        }

        try{
            Pedido pedidoObj = (Pedido) repositorioPedidos.recuperar(pedido);
            Producto productoObj = (Producto) repositorioProductos.recuperar(producto);

            if (pedidoObj.isConfirmado()){
                throw new IllegalStateException("No se pueden añadir líneas a un pedido confirmado");
            }

            pedidoObj.add(new LineaPedido(productoObj, unidades, (float) productoObj.getPrecio()));

            System.out.println("Éxito al añadir línea al pedido con código: " + pedido);
        }catch (Exception e){
            System.out.println("Error al añadir línea al pedido: " + e.getMessage());
        }
    }

    @Override
    public boolean confirmarPedido(String pedido){

        try{
            if (pedido == null) {
                throw new IllegalArgumentException("El pedido no puede ser nulo");
            }

            Pedido pedidoObj = (Pedido) repositorioPedidos.recuperar(pedido);

            for (LineaPedido linea : pedidoObj.getLineasPedido()) {
                Producto producto = linea.getProducto();
                int unidadesPedido = linea.getUnidades();

                Producto productoEnRepo = (Producto) repositorioProductos.recuperar(producto.getCodigo());

                if (productoEnRepo == null) {
                    throw new IllegalArgumentException("El producto " + producto.getNombre() + " no existe en el repositorio");
                }

                if (unidadesPedido > productoEnRepo.getStock()) {
                    return false; // No hay suficiente stock para este producto
                }
            }

            // Si llegamos aquí, hay suficiente stock para todos los productos
            for (LineaPedido linea : pedidoObj.getLineasPedido()) {
                Producto producto = linea.getProducto();
                int unidadesPedido = linea.getUnidades();
                Producto productoEnRepo = (Producto) repositorioProductos.recuperar(producto.getCodigo());
                productoEnRepo.setStock(productoEnRepo.getStock() - unidadesPedido);
            }

            if (!pedidoObj.isConfirmado()) {
                pedidoObj.setConfirmado(true);
            }
        }catch (Exception e){
            System.out.println("Error al confirmar el pedido: " + e.getMessage());
            return false;
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


    public Pedido[] consultarPedidosPorCliente(String c) {
        //devuelve un array con los pedidos de un cliente
        List<Pedido> listaPedidos = repositorioPedidos.listar();
        return listaPedidos.stream()
                .filter(pedido -> pedido.getCliente().getCodigo().equals(c))
                .toArray(Pedido[]::new);
    }
}
