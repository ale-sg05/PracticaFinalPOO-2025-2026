package Interfaces;

import Dominio.Pedido;

import java.util.List;

public interface RepPedido extends Repository {
    List<Pedido> listar();
}
