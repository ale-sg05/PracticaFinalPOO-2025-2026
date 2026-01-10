package Interfaces;

import Dominio.Pedido;

public interface ServiciosPedidos extends Servicios {
    void crearPedido(String pedido, String cliente, float iva);
    void addLineaPedido(String producto, String pedido, int unidades);
    boolean confirmarPedido(String pedido);
    float consultaTotalFacturado();
}
