package tads;

public interface ILista<T> {
    public void agregarInicio(T x);

    public void mostrar();

    public int cantidadElementos();

    public boolean esVacia();

    public void vaciar();

    public boolean existeElemento(T x);

    public T obtenerElemento(int indice);

    public void agregarFinal(T x);

    public void eliminarInicio();

    public void eliminarFinal();
    
    public int obtenerPos(T x);
    
    public void insertarEnPos(int pos, T dato);
    
    public void eliminarEnPos(int pos);
    
    public void agregarOrdenado(T x);
}
