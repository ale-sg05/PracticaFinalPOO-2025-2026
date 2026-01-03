package Servicios;

import Dominio.Cliente;
import Interfaces.ServiciosClientes;
import Repositorios.Clientes;

import java.util.List;

public class ServCliente implements ServiciosClientes {
    private final Clientes repositorio;

    public ServCliente(Clientes repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void altaCliente(String codigo, String nombre, String telefono){

        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código del cliente no puede estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono del cliente no puede estar vacío");
        }

        Cliente cliente = new Cliente(codigo, nombre, telefono);
        repositorio.creacion(cliente);
    }

    @Override
    public List<Cliente> conulta(String nombre){
        List<Cliente> listaRep = repositorio.listar();
        List<Cliente> listaAux = List.of();

        for (Cliente cliente : listaRep){
            String nombreClienteLower = cliente.getNombre().toLowerCase();

            if(nombreClienteLower.equalsIgnoreCase(nombre.toLowerCase())){
                listaAux.add(cliente);
            }
        }

        return listaAux;
    }

    @Override
    public List<Cliente> listarClientes(){
        return repositorio.listar();
    }

}
