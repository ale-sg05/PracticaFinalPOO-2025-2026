package Interfaces;

import Dominio.Categoria;
import Dominio.Producto;

import java.util.List;

public interface ServiciosProductos extends Servicios {
    void altaProducto(String codigo, String nombre, Categoria categoria, float precioUnitario, int stock);
    List<Producto> listarProductos();
    List<Producto> consulta(String nombre);
    int ajusteStock(String codigo, int cantidad);
    void eliminacionRestringida(String codigo);
}
