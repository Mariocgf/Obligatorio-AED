package dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Evento implements Comparable<Evento> {
    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala sala;

    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha, Sala sala) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.sala = sala;
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
        return codigo + "-" + descripcion + "-" + sala.getNombre();
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
