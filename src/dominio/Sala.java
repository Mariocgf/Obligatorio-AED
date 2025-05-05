package dominio;

import java.util.Objects;
import tads.Lista;

public class Sala implements Comparable<Sala>{

    private String nombre;
    private int Capacidad;
    private Lista<Evento> listaEventos;

    public Sala(String Nombre, int Capacidad) {
        this.nombre = Nombre;
        this.Capacidad = Capacidad;
        this.listaEventos = new Lista<>();
    }
    public Sala(String nombre){
        this.nombre = nombre;
    }
    public Lista<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(Lista listaEventos) {
        this.listaEventos = listaEventos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int Capacidad) {
        this.Capacidad = Capacidad;
    }

    @Override
    public String toString() {
        return nombre + "-" + Capacidad;
    }

    @Override
    public int compareTo(Sala o) {
        return this.nombre.compareTo(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        Sala other = (Sala) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

}
