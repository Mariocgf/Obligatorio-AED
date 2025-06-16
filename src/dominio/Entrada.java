package dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Entrada implements Comparable<Entrada> {

    private Cliente cliente;
    private Evento evento;
    private String estado;
    private LocalDate fecha;

    public Entrada(Cliente cliente, Evento evento) {
        this.cliente = cliente;
        this.evento = evento;
        this.estado = "N";
        this.fecha = LocalDate.now();
    }
    public Entrada(Cliente cliente) {
        this.cliente = cliente;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return Objects.equals(this.evento, other.evento);
    }

    @Override
    public int compareTo(Entrada o) {
        int comparacion = this.evento.getCodigo().compareToIgnoreCase(o.getEvento().getCodigo());
        if (comparacion != 0) {
            return comparacion;
        }
        return this.cliente.getCedula().compareToIgnoreCase(o.getCliente().getCedula());
    }

    @Override
    public String toString() {
        return evento.getCodigo() + '-' + cliente.getCedula();
    }
    
}
