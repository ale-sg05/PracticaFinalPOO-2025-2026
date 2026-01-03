package Interfaces;

import Dominio.Cliente;

import java.util.List;

public interface ServiciosClientes extends Servicios {
    void altaCliente(String codigo, String nombre, String telefono);
    List<Cliente> conulta(String nombre);
    List<Cliente> listarClientes();
}
