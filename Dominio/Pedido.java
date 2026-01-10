package Dominio;

import Interfaces.PedidoComposite;

import java.util.ArrayList;
import java.util.List;

public class Pedido extends PedidoComposite {
    private String codigo;
    private Cliente cliente;
    private float iva;
    private ArrayList<LineaPedido> lineasPedido;
    private boolean confirmado;

    public Pedido(String codigo, Cliente cliente, float iva, ArrayList<LineaPedido> lineasPedido) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.iva = iva;
        this.lineasPedido = lineasPedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(ArrayList<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    @Override
    public void add(PedidoComposite pedidoComposite) {
        if (pedidoComposite instanceof LineaPedido){
            lineasPedido.add((LineaPedido) pedidoComposite);
        } else {
            throw new UnsupportedOperationException("Solo se pueden agregar líneas de pedido.");
        }
    }

    @Override
    public void remove(PedidoComposite pedidoComposite) {
        if (pedidoComposite instanceof LineaPedido){
            lineasPedido.remove((LineaPedido) pedidoComposite);
        } else {
            throw new UnsupportedOperationException("Solo se pueden eliminar líneas de pedido.");
        }
    }

    public float calculateTotalWithIVA() {
        float total = 0;
        for (LineaPedido linea : lineasPedido) {
            total += linea.calculateTotalWithoutIVA();
        }
        total += total * iva;
        return total;
    }

    @Override
    public float calculateTotalWithoutIVA() {
        float total = 0;
        for (LineaPedido linea : lineasPedido) {
            total += linea.calculateTotalWithoutIVA();
        }
        return total;
    }

    @Override
    public String toString(){
        return "Pedido{" +
                "cliente=" + cliente +
                ", iva=" + iva +
                ", lineasPedido=" + lineasPedido +
                ", confirmado=" + confirmado +
                '}';
    }
}
