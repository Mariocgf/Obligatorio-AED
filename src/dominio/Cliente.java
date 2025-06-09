package dominio;

import java.util.Objects;
import tads.Lista;

public class Cliente implements Comparable<Cliente>{
    private String cedula;
    private String Nombre;
    private Lista<Entrada> listaEntrada;

    public Cliente(String cedula, String Nombre) {
        this.cedula = cedula;
        this.Nombre = Nombre;
        this.listaEntrada = new Lista();
    }
    public Cliente(String cedula){
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Lista<Entrada> getListaEntrada() {
        return listaEntrada;
    }

    @Override
    public String toString() {
        return cedula + "-" + Nombre;
    }
    @Override
    public int compareTo(Cliente o) {
        return this.cedula.compareToIgnoreCase(o.cedula);
    }


    @Override
    public boolean equals(Object obj) {
        Cliente other = (Cliente) obj;
        return Objects.equals(this.cedula, other.cedula);
    }
    
}
