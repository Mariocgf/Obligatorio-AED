package tads;

public class Nodo<T> {
    private Nodo sig;
    private T value;

    public Nodo(T value) {
        this.value = value;
        sig = null;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo<T> sig) {
        this.sig = sig;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
}
