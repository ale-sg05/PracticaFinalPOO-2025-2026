package Interfaces;

import Dominio.Producto;
import java.util.List;

public interface RepProducto extends Repository {
    List<Producto> listar();
}
