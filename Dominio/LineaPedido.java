package Dominio;

import Interfaces.PedidoComposite;

public class LineaPedido extends PedidoComposite {
    private Producto producto;
    private int unidades;
    private float precioUnitario;

    public LineaPedido(Producto producto, int unidades, float precioUnitario) {
        this.producto = producto;
        this.unidades = unidades;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public void add(PedidoComposite pedidoComposite) {
        throw new UnsupportedOperationException("No se puede agregar a una línea de pedido.");
    }

    @Override
    public void remove(PedidoComposite pedidoComposite) {
        throw new UnsupportedOperationException("No se puede eliminar de una línea de pedido.");
    }

    @Override
    public float calculateTotalWithoutIVA() {
        return unidades * precioUnitario;
    }

    @Override
    public String toString() {
        return "LineaPedido{" +
                "producto=" + producto +
                ", unidades=" + unidades +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
