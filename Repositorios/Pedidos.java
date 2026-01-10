package Repositorios;

import Dominio.Pedido;
import Interfaces.RepPedido;

import java.util.ArrayList;
import java.util.List;

public class Pedidos implements RepPedido {
    List<Pedido> pedidos = new ArrayList<>();

    @Override
    public List<Pedido> listar() {
        return List.copyOf(pedidos);
    }

    @Override
    public void creacion(Object a) {
        if (a instanceof Pedido) {
            pedidos.add((Pedido) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Pedido");
        }
    }

    @Override
    public void borrado(Object a) {
        if (a instanceof Pedido) {
            if (((Pedido) a).isConfirmado()) {// El pedido al ser confirmado es inmutable y no se puede borrar.
                throw new IllegalStateException("No se puede borrar un pedido confirmado");
            }
            else{
                pedidos.remove((Pedido) a);
            }
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Pedido");
        }
    }

    @Override
    public Object recuperar(Object a) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo().equalsIgnoreCase((String) a)){
                return pedido;
            }
        }
        throw new IllegalArgumentException("El objeto no se encuentra en la lista de pedidos");
    }
}
