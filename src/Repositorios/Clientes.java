package Repositorios;

import Dominio.Cliente;
import Interfaces.RepCliente;

import java.util.ArrayList;
import java.util.List;

public class Clientes implements RepCliente {
    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public List<Cliente> listar() {
        return List.copyOf(clientes);
    }


    @Override
    public void creacion(Object a) {
        if (a instanceof Cliente) {
            clientes.add((Cliente) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Cliente");
        }
    }

    @Override
    public void borrado(Object a) {
        if (a instanceof Cliente) {
            clientes.remove((Cliente) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Cliente");
        }
    }

    @Override
    public Object recuperar(Object a) {
        for (Cliente cliente : clientes){
            if(cliente.getCodigo().equalsIgnoreCase((String) a)){
                return cliente;
            }
        }
        return null;
    }
}
