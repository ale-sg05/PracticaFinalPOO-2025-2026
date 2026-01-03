package Repositorios;

import Dominio.Producto;
import Interfaces.RepProducto;

import java.util.ArrayList;
import java.util.List;

public class Productos implements RepProducto {
    List<Producto> productos = new ArrayList<>();

    @Override
    public List<Producto> listar() {
        return List.copyOf(productos);
    }

    @Override
    public void creacion(Object a) {
        if (a instanceof Producto) {
            productos.add((Producto) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Producto");
        }
    }

    @Override
    public void borrado(Object a) {
        if (a instanceof Producto) {
            productos.remove((Producto) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Producto");
        }
    }

    @Override
    public Object recuperar(Object a) {
        if (a instanceof Producto) {
            for (Producto producto : productos) {
                if (producto.equals(a)) {
                    return producto;
                }
            }
        }else {
            throw new IllegalArgumentException("El objeto no es una instancia de Producto");
        }
        return null;
    }
}
