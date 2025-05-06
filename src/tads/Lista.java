package tads;

public class Lista<T extends Comparable> implements ILista<T> {

    private Nodo<T> initial;
    private Nodo<T> last;
    private int count;

    public Lista() {
        this.initial = null;
        this.last = null;
        this.count = 0;
    }

    @Override
    public void agregarInicio(T x) {
        Nodo nodo = new Nodo(x);
        if (esVacia()) {
            initial = nodo;
            last = nodo;
        } else {
            nodo.setSig(initial);
            initial = nodo;
        }
        count++;
    }

    @Override
    public void mostrar() {
        if (initial != null) {
            Nodo aux = initial;
            while (aux != null) {
                if (aux.getSig() != null) {
                    System.out.print(aux.getValue() + "#");
                } else {
                    System.out.println(aux.getValue());
                }
                aux = aux.getSig();
            }
        }
    }

    @Override
    public int cantidadElementos() {
        return count;
    }

    @Override
    public boolean esVacia() {
        return initial == null;
    }

    @Override
    public void vaciar() {
        initial = null;
        last = null;
        count = 0;
    }

    @Override
    public boolean existeElemento(T x) {
        boolean exist = false;
        Nodo<T> aux = initial;
        while (aux != null && !exist) {
            if (aux.getValue().equals(x)) {
                exist = true;
            }
            aux = aux.getSig();
        }
        return exist;
    }

    @Override
    public T obtenerElemento(int indice) {
        T ret = null;

        if (!esVacia()) {
            if (indice == 0) {
                ret = initial.getValue();
            } else if (indice == count - 1) {
                ret = last.getValue();
            } else {
                Nodo<T> aux = initial;
                int cont = 0;
                while (aux != null) {
                    if (indice == cont) {
                        ret = aux.getValue();
                    }
                    aux = aux.getSig();
                    cont++;
                }
            }
        }
        return ret;
    }

    @Override
    public void agregarFinal(T x) {
        if (esVacia()) {
            agregarInicio(x);
        } else {
            Nodo nuevo = new Nodo(x);
            last.setSig(nuevo);
            last = nuevo;
            count++;
        }
    }

    @Override
    public void eliminarInicio() {
        if (!esVacia()) {
            if (count == 1) {
                vaciar();
            } else {
                Nodo aux = initial;
                initial = initial.getSig();
                aux.setSig(null);
                count--;
            }
        }
    }

    @Override
    public void eliminarFinal() {
        if (!esVacia()) {
            Nodo aux = initial;
            if (count == 1) {
                vaciar();
            } else {
                while (aux.getSig().getSig() != null) {
                    aux = aux.getSig();
                }
                aux.setSig(null);
                last = aux;
                count--;
            }
        }
    }

    @Override
    public int obtenerPos(T x) {
        int pos = -1;
        Nodo aux = new Nodo(x);
        for (int i = 0; i < count && pos == -1; i++) {
            if (obtenerElemento(i).equals(x)) {
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public void insertarEnPos(int pos, T x) {
        if (pos == 0) {
            agregarInicio(x);
        } else if (pos == count) {
            agregarFinal(x);
        } else {
            Nodo ant = initial;
            Nodo act = initial.getSig();
            int posAux = 1;
            while (posAux != pos) {
                ant = act;
                act = act.getSig();
                posAux++;
            }
            Nodo aux = new Nodo(x);
            aux.setSig(act);
            ant.setSig(aux);

            count++;
        }
    }

    @Override
    public void eliminarEnPos(int pos) {
        if (!esVacia()) {
            if (pos == 0) {
                eliminarInicio();
            } else if (pos == count - 1) {
                eliminarFinal();
            } else {
                Nodo ant = initial;
                Nodo act = initial.getSig();
                int auxPos = 1;
                boolean eliminado = false;
                while (act != null && !eliminado) {
                    if (auxPos == pos) {
                        ant.setSig(act.getSig());
                    }
                    ant = act;
                    act = act.getSig();
                    auxPos++;
                }
                count--;
            }
        }
    }
    @Override
    public void agregarOrdenado(T x){
        Nodo<T> aux2 = initial;
        int pos = 0;
        while(aux2 != null && ((Comparable<T>) x).compareTo(aux2.getValue()) > 0){
            pos++;
            aux2 = aux2.getSig();
        }
        insertarEnPos(pos, x);
    }
}
