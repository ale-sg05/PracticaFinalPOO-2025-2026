package Interfaces;

public interface Repository <T, K> {
    void creacion(T a);
    void borrado(T a);
    T recuperar (K a);
}
