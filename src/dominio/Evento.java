package dominio;

import java.time.LocalDate;
import java.util.Objects;
import tads.Lista;

public class Evento implements Comparable<Evento> {
    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala sala;
    private Lista listaEntradas;

    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha, Sala sala) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.sala = sala;
        this.listaEntradas = new Lista();
    }
    public Evento(String codigo){
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforoNecesario() {
        return aforoNecesario;
    }

    public void setAforoNecesario(int aforoNecesario) {
        this.aforoNecesario = aforoNecesario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return codigo + "-" + descripcion + "-" + sala.getId() + "-" + (sala.getCapacidad() - this.listaEntradas.cantidadElementos()) + "-" + this.listaEntradas.cantidadElementos();
    }
    
    @Override
    public boolean equals(Object o){
        Evento event = (Evento) o;
        return Objects.equals(this.codigo, event.codigo);
    }

    @Override
    public int compareTo(Evento o) {
        return this.codigo.compareToIgnoreCase(o.codigo);
    }
}
