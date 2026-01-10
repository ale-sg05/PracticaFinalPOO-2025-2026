package Servicios;

import Dominio.Cliente;
import Interfaces.ServiciosClientes;
import Repositorios.Clientes;

import java.util.ArrayList;
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

        try{
            Cliente cliente = new Cliente(codigo, nombre, telefono);
            repositorio.creacion(cliente);

            System.out.println("Éxito en alta de cliente con código: " + codigo);
        }catch (Exception e){
            System.out.println("Error al dar de alta el cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> consulta(String nombre){
        List<Cliente> listaRep = repositorio.listar();
        List<Cliente> listaAux = new ArrayList<>();

        for (Cliente cliente : listaRep){
            String nombreClienteLower = cliente.getNombre().toLowerCase().trim();

            if(nombreClienteLower.contains(nombre.toLowerCase().trim())){
                listaAux.add(cliente);
            }
        }

        return List.copyOf(listaAux);
    }

    @Override
    public List<Cliente> listarClientes(){
        return repositorio.listar();
    }

}
