/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author mario
 */
public class Clasificacion implements Comparable<Clasificacion> {
    private Cliente cliente;
    private Evento evento;
    private int puntaje;
    private String comentario;

    public Clasificacion(Cliente cliente, Evento evento, int puntaje, String comentario) {
        this.cliente = cliente;
        this.evento = evento;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }
    public Clasificacion(Cliente cliente){
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getComentario() {
        return comentario;
    }
    

    @Override
    public int compareTo(Clasificacion o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
