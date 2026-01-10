package Servicios;

import Dominio.Categoria;
import Dominio.Pedido;
import Dominio.Producto;
import Interfaces.ServiciosProductos;
import Repositorios.Pedidos;
import Repositorios.Productos;

import java.util.ArrayList;
import java.util.List;

public class ServProducto implements ServiciosProductos {
    private final Productos repositorioProductos;
    private final Pedidos repositorioPedidos;

    public ServProducto(Productos repositorioProductos, Pedidos repositorioPedidos) {
        this.repositorioPedidos = repositorioPedidos;
        this.repositorioProductos = repositorioProductos;
    }

    @Override
    public void altaProducto(String codigo, String nombre, Categoria categoria, float precioUnitario, int stock) {

        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código del producto no puede estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría del producto no puede ser nula");
        }
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        try{
            Producto producto = new Producto(codigo, nombre, categoria, precioUnitario, stock);
            repositorioProductos.creacion(producto);

            System.out.println("Éxito en alta de producto con código: " + codigo);

        }catch (Exception e){
            throw new IllegalArgumentException("Error al dar de alta el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listarProductos() {
        return repositorioProductos.listar();
    }

    @Override
    public List<Producto> consulta(String nombre) {
        List<Producto> listaRep = repositorioProductos.listar();
        List<Producto> listaAux = new ArrayList<>();


        for (Producto producto : listaRep){
            String nombreProductoLower = producto.getNombre().toLowerCase().trim();

            if(nombreProductoLower.contains(nombre.toLowerCase().trim())){
                listaAux.add(producto);
            }
        }

        return List.copyOf(listaAux);

    }

    @Override
    public int ajusteStock(String codigo, int cantidad) {
        List<Producto> listaRep = repositorioProductos.listar();

        for (Producto producto : listaRep) {
            if (producto.getCodigo().equals(codigo)) {
                int nuevoStock = producto.getStock() + cantidad;
                if (nuevoStock < 0) {
                    throw new IllegalArgumentException("El stock no puede ser negativo");
                }
                producto.setStock(nuevoStock);
                return nuevoStock;
            }
        }
        throw new IllegalArgumentException("Producto no encontrado con el código proporcionado");
    }

    @Override
    public void eliminacionRestringida(String codigo) {
        List<Producto> listaRep = repositorioProductos.listar();
        var pedidoConfirmado = false;

        for (Producto producto : listaRep) {
            if (producto.getCodigo().equals(codigo)) {
                // Verificar si el producto está asociado a algún pedido
                boolean asociadoAPedido = false;
                var listaPedidos = repositorioPedidos.listar();
                for (var pedido : listaPedidos) {
                    if (pedido.getLineasPedido() != null) {
                        for (var lineaPedido : pedido.getLineasPedido()) {
                            if (lineaPedido.getProducto().getCodigo().equals(codigo)) {
                                asociadoAPedido = true;
                                break;
                            }
                        }
                    }
                    if (asociadoAPedido && pedido.isConfirmado()) {
                        pedidoConfirmado = true;
                        break;
                    }
                }

                if (asociadoAPedido && pedidoConfirmado) {
                    throw new IllegalStateException("No se puede eliminar el producto porque está asociado a un pedido confirmado");
                } else {
                    repositorioProductos.borrado(producto);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Producto no encontrado con el código proporcionado");
    }

    public Productos getRepositorioProductos() {
        return repositorioProductos;
    }

    public Pedidos getRepositorioPedidos() {
        return repositorioPedidos;
    }


}
