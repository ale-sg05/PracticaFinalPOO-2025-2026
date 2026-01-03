package Interfaces;

import java.util.List;
import Dominio.Cliente;

public interface RepCliente extends Repository {
    List<Cliente> listar();
}
