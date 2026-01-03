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
            pedidos.remove((Pedido) a);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Pedido");
        }
    }

    @Override
    public Object recuperar(Object a) {
        if (a instanceof Pedido) {
            for (Pedido pedido : pedidos) {
                if (pedido.equals(a)) {
                    return pedido;
                }
            }
        }else {
            throw new IllegalArgumentException("El objeto no es una instancia de Pedido");
        }
        return null;
    }
}
