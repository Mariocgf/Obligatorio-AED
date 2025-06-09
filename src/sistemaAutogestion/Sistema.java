package sistemaAutogestion;

import dominio.Clasificacion;
import dominio.Cliente;
import dominio.Entrada;
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
        } else if (capacidad <= 0) {
            return Retorno.error2();
        } else {
            listaSala.agregarInicio(sala);
            return Retorno.ok();
        }
    }

    @Override
    public Retorno eliminarSala(String nombre) {
        int pos = listaSala.obtenerPos(new Sala(nombre));
        if (pos == -1) {
            return Retorno.error1("No existe una sala con ese nombre");
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
        if (listaCliente.obtenerPos(cliente) == -1) {
            listaCliente.agregarOrdenado(cliente);
            return Retorno.ok();
        }
        return Retorno.error2();
    }

    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        Retorno retorno = Retorno.ok();

        if (!listaCliente.existeElemento(new Cliente(cedula))) {
            retorno = Retorno.error1("Cliente no existe");
        } else if (!listaEvento.existeElemento(new Evento(codigoEvento))) {
            retorno = Retorno.error2("Evento no existe");
        } else {
            Evento evento = listaEvento.obtenerElemento(listaEvento.obtenerPos(new Evento(codigoEvento)));
            Cliente cliente = listaCliente.obtenerElemento(listaCliente.obtenerPos(new Cliente(cedula)));

            if (evento.getSala().getCapacidad() > evento.getListaEntradas().cantidadElementos()) {
                Entrada entrada = new Entrada(cliente, evento);
                evento.getListaEntradas().agregarFinal(entrada);
                cliente.getListaEntrada().agregarFinal(entrada);
            } else {
                evento.getListaEspera().agregarFinal(cliente);
            }
        }
        return retorno;
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        Retorno retorno = Retorno.ok();

        if (!listaEvento.existeElemento(new Evento(codigo))) {
            retorno = Retorno.error1("No existe el evento");
        } else {
            int eventoPos = listaEvento.obtenerPos(new Evento(codigo));
            Evento evento = listaEvento.obtenerElemento(eventoPos);

            if (evento.getListaEntradas().cantidadElementos() > 0) {
                retorno = Retorno.error2("El evento tiene entradas vendidas");
            } else {
                listaEvento.eliminarEnPos(eventoPos);
            }
        }
        return retorno;
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        Retorno retorno = Retorno.ok();
        if (!listaCliente.existeElemento(new Cliente(cedula))) {
            retorno = Retorno.error1("Cliente no existe");
        } else if (!listaEvento.existeElemento(new Evento(codigoEvento))) {
            retorno = Retorno.error2("Evento no existe");
        } else {
            Evento evento = listaEvento.obtenerElemento(listaEvento.obtenerPos(new Evento(codigoEvento)));
            Cliente cliente = listaCliente.obtenerElemento(listaCliente.obtenerPos(new Cliente(cedula)));

            evento.getListaEntradas().eliminarEnPos(evento.getListaEntradas().obtenerPos(new Entrada(cliente, evento)));
            if (evento.getListaEspera().cantidadElementos() > 0) {
                Cliente clienteEspera = evento.getListaEspera().obtenerElemento(0);
                evento.getListaEntradas().agregarFinal(new Entrada(clienteEspera, evento));
                evento.getListaEspera().eliminarInicio();
            }
        }
        return retorno;
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        Retorno retorno = Retorno.ok();
        if (!listaCliente.existeElemento(new Cliente(cedula))) {
            retorno = Retorno.error1("Cliente no existe");
        } else if (!listaEvento.existeElemento(new Evento(codigoEvento))) {
            retorno = Retorno.error2("Evento no existe");
        } else if (puntaje > 10 || puntaje < 0) {
            retorno = Retorno.error3("Puntaje < 1 o puntaje > 10");
        } else {
            Cliente cliente = listaCliente.obtenerElemento(listaCliente.obtenerPos(new Cliente(cedula)));
            Evento evento = listaEvento.obtenerElemento(listaEvento.obtenerPos(new Evento(codigoEvento)));
            Clasificacion clasificaion = new Clasificacion(cliente, evento, puntaje, comentario);
            if (evento.getListaClasificacion().existeElemento(clasificaion)) {
                retorno = Retorno.error4("El evento ya fue clasificado por el cliente");
            } else {
                evento.getListaClasificacion().agregarFinal(clasificaion);
            }
        }
        return retorno;
    }

    @Override
    public Retorno listarSalas() {
        String salida = "";
        for (int i = 0; i < listaSala.cantidadElementos(); i++) {
            if (i != (listaSala.cantidadElementos() - 1)) {
                salida += listaSala.obtenerElemento(i) + "#";
            } else {
                salida += listaSala.obtenerElemento(i);
            }
        }
        return Retorno.ok(salida);
    }

    @Override
    public Retorno listarEventos() {
        String salida = "";
        for (int i = 0; i < listaEvento.cantidadElementos(); i++) {
            if (i != (listaEvento.cantidadElementos() - 1)) {
                salida += listaEvento.obtenerElemento(i) + "#";
            } else {
                salida += listaEvento.obtenerElemento(i);
            }
        }
        return Retorno.ok(salida);
    }

    @Override
    public Retorno listarClientes() {
        String salida = "";
        for (int i = 0; i < listaCliente.cantidadElementos(); i++) {
            if (i != (listaCliente.cantidadElementos() - 1)) {
                salida += listaCliente.obtenerElemento(i) + "#";
            } else {
                salida += listaCliente.obtenerElemento(i);
            }
        }
        return Retorno.ok(salida);
    }

    @Override
    public Retorno esSalaOptima(String[][] vistaSala) {
        int contCol = 0; //cantidad de columnas juntas
        boolean corte = false;
        String msg = "No es óptimo";
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
                msg = "Es óptimo";
            }
        }
        return Retorno.ok(msg);
    }

    @Override
    public Retorno listarClientesDeEvento(String código, int n) {
        String salida = "";
        Retorno retorno = Retorno.ok(salida);
        if (!listaEvento.existeElemento(new Evento(código))) {
            retorno = Retorno.error1("Evento no existe");
        } else if (n < 1) {
            retorno = Retorno.error2("n < 1");
        } else {
            int cant = 0;
            Evento evento = listaEvento.obtenerElemento(listaEvento.obtenerPos(new Evento(código)));
            if (evento.getListaEntradas().cantidadElementos() < n) {
                cant = evento.getListaEntradas().cantidadElementos();
            } else {
                cant = n;
            }
            for (int i = 0; i < cant; i++) {
                if (i != (cant - 1)) {
                    salida += evento.getListaEntradas().obtenerElemento(i) + "#";
                } else {
                    salida += evento.getListaEntradas().obtenerElemento(i);
                }
            }
        }
        return retorno;
    }

    //Reparar el orden de cliente - ESTO NO ORDENA LOS CLIENTES
    @Override
    public Retorno listarEsperaEvento() {
        String salida = "";
        Lista lista = new Lista();
        for (int i = 0; i < listaEvento.cantidadElementos(); i++) {
            Evento evento = listaEvento.obtenerElemento(i);
            if (evento.getListaEspera().cantidadElementos() > 0) {
                if (i != (listaEvento.cantidadElementos() - 1)) {
                    salida += evento.getCodigo() + "-" + evento.getListaEspera().obtenerElemento(i).getCedula() + "#";
                } else {
                    salida += evento.getCodigo() + "-" + evento.getListaEspera().obtenerElemento(i).getCedula();
                }
            }
        }
        return Retorno.ok(salida);
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        Lista lista = new Lista();
        int max = 1;
        for (int i = 0; i < listaEvento.cantidadElementos(); i++) {
            Evento evento = listaEvento.obtenerElemento(i);
            int suma = 0;
            int cant = evento.getListaClasificacion().cantidadElementos();
            for (int j = 0; j < cant; j++) {
                suma += evento.getListaClasificacion().obtenerElemento(j).getPuntaje();
            }
            if ((suma / cant) > max) {
                lista.vaciar();
                lista.agregarFinal(evento.getCodigo() + "-" + max);
                max = suma/cant;
            } else if ((suma / cant) == max) {
                lista.agregarFinal(evento.getCodigo() + "-" + max);
            }
        }
        return Retorno.ok();
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        Retorno retorno = Retorno.ok();
        String salida = "";
        if(!listaCliente.existeElemento(new Cliente(cedula))){
            retorno = Retorno.error1("Cliente no existe");
        }else{
            Cliente cliente = listaCliente.obtenerElemento(listaCliente.obtenerPos(new Cliente(cedula)));
            for (int i = 0; i < cliente.getListaEntrada().cantidadElementos(); i++) {
                System.out.println("Entre");
                String codEvento = cliente.getListaEntrada().obtenerElemento(i).getEvento().getCodigo();
                String estadoEntrada = cliente.getListaEntrada().obtenerElemento(i).getEstado();
                if (i != (listaEvento.cantidadElementos() - 1)) {
                    salida +=  codEvento+ "-" + estadoEntrada + "#";
                } else {
                    salida += codEvento + "-" + estadoEntrada;
                }
            }
        }
        retorno.valorString = salida;
        return retorno;
    }

    @Override
    public Retorno comprasXDia(int mes) {
        Retorno retorno = Retorno.ok();
        String salida = "";
        if (mes > 12 || mes < 1){
            retorno = Retorno.error1("Mes < 1 o mes > 12");
        }else{
            
        }
        return retorno;
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

    public Lista<Cliente> getListaCliente() {
        return this.listaCliente;
    }

}
