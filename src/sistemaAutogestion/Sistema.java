package sistemaAutogestion;

import dominio.Cliente;
import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import tads.Lista;

public class Sistema implements IObligatorio {

    private Lista<Sala> listaSala;
    private Lista<Evento> listaEvento;
    private Lista<Cliente> listaCliente;

    @Override
    public Retorno crearSistemaDeGestion() {
        this.listaSala = new Lista();
        this.listaEvento = new Lista();
        this.listaCliente = new Lista();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        Sala sala = new Sala(nombre, capacidad);
        int pos = listaSala.obtenerPos(sala);
        if (pos != -1) {
            return Retorno.error1();
        } else if (capacidad == 0) {
            return Retorno.error2();
        } else {
            listaSala.agregarInicio(sala);
            return Retorno.noImplementada();
        }
    }

    @Override
    public Retorno eliminarSala(String nombre) {
        int pos = listaSala.obtenerPos(new Sala(nombre));
        if (pos == -1) {
            return Retorno.error1();
        } else {
            listaSala.eliminarEnPos(pos);
        }
        return Retorno.ok();
    }

    @Override
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        int pos = listaEvento.obtenerPos(new Evento(codigo));
        if (pos != -1) {
            return Retorno.error1();
        } else if (aforoNecesario <= 0) {
            return Retorno.error2();
        } else {
            int dia = fecha.getDayOfMonth();
            int mes = fecha.getMonthValue();
            Sala salaLibre = null;
            for (int i = 0; i < listaSala.cantidadElementos() && salaLibre == null; i++) {
                boolean libre = true;
                for (int j = 0; j < listaSala.obtenerElemento(i).getListaEventos().cantidadElementos() && libre; j++) {
                    int diaSala = listaSala.obtenerElemento(i).getListaEventos().obtenerElemento(j).getFecha().getDayOfMonth();
                    int mesSala = listaSala.obtenerElemento(i).getListaEventos().obtenerElemento(j).getFecha().getMonthValue();
                    if (diaSala == dia && mesSala == mes) {
                        libre = false;
                    }
                }
                if (libre) {
                    salaLibre = listaSala.obtenerElemento(i);
                }
            }
            Evento e = new Evento(codigo, descripcion, aforoNecesario, fecha, salaLibre);
            if (salaLibre != null) {
                salaLibre.getListaEventos().agregarFinal(e);
                listaEvento.agregarOrdenado(e);
                return Retorno.ok();
            }
        }
        return Retorno.error3();
    }

    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        if (cedula.length() != 8) {
            return Retorno.error1();
        }
        Cliente cliente = new Cliente(cedula, nombre);
        if (listaCliente.obtenerPos(cliente) != -1) {
            listaCliente.agregarOrdenado(cliente);
            return Retorno.ok();
        }
        return Retorno.error2();
    }

    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarSalas() {
        listaSala.mostrar();
        return Retorno.ok();
    }

    @Override
    public Retorno listarEventos() {
        listaEvento.mostrar();
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarClientes() {
        listaCliente.mostrar();
        return Retorno.noImplementada();
    }

    @Override
    public Retorno esSalaOptima(String[][] vistaSala) {
        int contCol = 0; //cantidad de columnas juntas
        boolean corte = false;
        String msg = "No es optimo.";
        for (int j = 0; j < vistaSala[0].length && !corte; j++) {
            int asientosOcupadosConsecutivos = 0;
            int asientosLibres = 0;
            int aux = 0;
            for (int i = 0; i < vistaSala.length; i++) {
                if ("X".equals(vistaSala[i][j])) {
                    asientosLibres++;
                }
                if ("O".equals(vistaSala[i][j])) {
                    aux++;
                } else {
                    aux = 0;
                }
                if (aux > asientosOcupadosConsecutivos) {
                    asientosOcupadosConsecutivos = aux;
                }
            }
            if (asientosLibres < asientosOcupadosConsecutivos) {
                contCol++;
            }
            if (contCol == 2) {
                corte = true;
                msg = "Es optimo.";
            }
        }
        return Retorno.ok(msg);
    }

    @Override
    public Retorno listarClientesDeEvento(String código, int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarEsperaEvento() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasXDia(int mes) {
        return Retorno.noImplementada();
    }

    public Lista<Sala> getListaSala() {
        return listaSala;
    }

    public void setListaSala(Lista<Sala> listaSala) {
        this.listaSala = listaSala;
    }

    public Lista<Evento> getListaEvento() {
        return listaEvento;
    }

    public void setListaEvento(Lista<Evento> listaEvento) {
        this.listaEvento = listaEvento;
    }

}
