package Dominio;


public class Producto {
    private String codigo;
    private String nombre;
    private Categoria categoria;
    private float precioUnitario;
    private int stock;

    public Producto(String codigo, String nombre, Categoria categoria, float precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioUnitario = precio;
        this.stock = stock;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precioUnitario;
    }

    public void setPrecio(float precio) {
        this.precioUnitario = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", precio=" + precioUnitario +
                ", stock=" + stock +
                '}';
    }
}
