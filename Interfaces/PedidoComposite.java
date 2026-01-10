package Interfaces;

import Dominio.Producto;

public abstract class PedidoComposite {
    public abstract void add(PedidoComposite pedidoComposite);
    public abstract void remove(PedidoComposite pedidoComposite);
    public abstract float calculateTotalWithoutIVA();
}
