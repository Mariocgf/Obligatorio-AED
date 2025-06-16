package sistemaAutogestion;

import dominio.Clasificacion;
import dominio.Cliente;
import dominio.Entrada;
import dominio.Evento;
import dominio.ReporteCompra;
import dominio.Sala;
import java.time.LocalDate;
import tads.Lista;

public class Sistema implements IObligatorio {

    private Lista<Sala> listaSala;
    private Lista<Evento> listaEvento;
    private Lista<Cliente> listaCliente;
    private Lista<Entrada> listaEntrada;

    @Override
    public Retorno crearSistemaDeGestion() {
        this.listaSala = new Lista();
        this.listaEvento = new Lista();
        this.listaCliente = new Lista();
        this.listaEntrada = new Lista();
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
                if (aforoNecesario > listaSala.obtenerElemento(i).getCapacidad()) {
                    libre = false;
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
        for (int i = 0; i < 8; i++) {
            if ((int) cedula.charAt(i) < 48 || (int) cedula.charAt(i) > 57) {
                return Retorno.error1();
            }
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

            if (evento.getAforoNecesario() > evento.getListaEntradas().cantidadElementos()) {
                Entrada entrada = new Entrada(cliente, evento);
                evento.getListaEntradas().agregarFinal(entrada);
                cliente.getListaEntrada().agregarFinal(entrada);
                listaEntrada.agregarFinal(entrada);
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
                int eventoEnSalaPos = evento.getSala().getListaEventos().obtenerPos(evento);
                listaEvento.eliminarEnPos(eventoPos);
                evento.getSala().getListaEventos().eliminarEnPos(eventoEnSalaPos);
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
            Boolean encontrado = false;
            Entrada entrada = cliente.getListaEntrada().obtenerElemento(cliente.getListaEntrada().obtenerPos(new Entrada(cliente, evento)));
            entrada.setEstado("D");
            evento.getListaEntradas().eliminarEnPos(evento.getListaEntradas().obtenerPos(entrada));
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
        } else if (puntaje > 10 || puntaje < 1) {
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
        return Retorno.ok(listaSala.toString());
    }

    @Override
    public Retorno listarEventos() {
        return Retorno.ok(listaEvento.toString());
    }

    @Override
    public Retorno listarClientes() {
        return Retorno.ok(listaCliente.toString());
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
                Cliente cliente = evento.getListaEntradas().obtenerElemento(i).getCliente();
                if (i != (cant - 1)) {
                    salida += cliente.getCedula() + '-' + cliente.getNombre() + "#";
                } else {
                    salida += cliente.getCedula() + '-' + cliente.getNombre();
                }
            }
        }
        retorno.valorString = salida;
        return retorno;
    }

    @Override
    public Retorno listarEsperaEvento() {
        String salida = "";
        Lista<Entrada> lista = new Lista();
        for (int i = 0; i < listaEvento.cantidadElementos(); i++) {
            Evento evento = listaEvento.obtenerElemento(i);
            if (evento.getListaEspera().cantidadElementos() > 0) {
                for (int j = 0; j < evento.getListaEspera().cantidadElementos(); j++) {
                    Cliente cliente = evento.getListaEspera().obtenerElemento(j);
                    Entrada entrada = new Entrada(cliente, evento);
                    lista.agregarOrdenado(entrada);
                }
            }
        }
        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        Retorno retorno = Retorno.ok();
        Lista<Entrada> entradas = new Lista();
        int cont = 0;
        for (int i = listaEntrada.cantidadElementos() - 1; i >= 0 && cont != n; i--) {
            cont++;
            Evento evento = listaEntrada.obtenerElemento(i).getEvento();
            Cliente cliente = listaEntrada.obtenerElemento(i).getCliente();
            Entrada entrada = new Entrada(cliente, evento);
            entradas.agregarOrdenado(entrada);
            devolverEntrada(cliente.getCedula(), evento.getCodigo());
            listaEntrada.eliminarEnPos(i);
        }
        return Retorno.ok(entradas.toString());
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        String salida = "";
        int max = 1;
        for (int i = 0; i < listaEvento.cantidadElementos(); i++) {
            Evento evento = listaEvento.obtenerElemento(i);
            int suma = 0;
            int cant = evento.getListaClasificacion().cantidadElementos();
            for (int j = 0; j < cant; j++) {
                suma += evento.getListaClasificacion().obtenerElemento(j).getPuntaje();
            }
            if (cant != 0) {
                if ((suma / cant) > max) {
                    max = suma / cant;
                    salida = evento.getCodigo() + "-" + max;
                } else if ((suma / cant) == max) {
                    salida += '#' + evento.getCodigo() + "-" + max;
                }
            }
        }
        return Retorno.ok(salida);
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        Retorno retorno = Retorno.ok();
        String salida = "";
        if (!listaCliente.existeElemento(new Cliente(cedula))) {
            retorno = Retorno.error1("Cliente no existe");
        } else {
            Cliente cliente = listaCliente.obtenerElemento(listaCliente.obtenerPos(new Cliente(cedula)));
            for (int i = 0; i < cliente.getListaEntrada().cantidadElementos(); i++) {
                String codEvento = cliente.getListaEntrada().obtenerElemento(i).getEvento().getCodigo();
                String estadoEntrada = cliente.getListaEntrada().obtenerElemento(i).getEstado();
                if (i != (listaEvento.cantidadElementos() - 1)) {
                    salida += codEvento + "-" + estadoEntrada + "#";
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
        Lista<ReporteCompra> lista = new Lista(); 
        int cont = 0;
        if (mes > 12 || mes < 1) {
            retorno = Retorno.error1("Mes < 1 o mes > 12");
        } else {
            for (int i = 0; i < listaEntrada.cantidadElementos(); i++) {
                Entrada entrada = listaEntrada.obtenerElemento(i);
                if(entrada.getFecha().getMonthValue() == mes){
                    ReporteCompra rCompra = new ReporteCompra(entrada.getFecha().getDayOfMonth(),1);
                    if(lista.existeElemento(rCompra)){
                        ReporteCompra aux = lista.obtenerElemento(lista.obtenerPos(rCompra));
                        aux.setCant(aux.getCant()+1);
                    }else{
                        lista.agregarOrdenado(rCompra);
                    }
                }
            }
            
            
            for (int i = 1; i <= 31; i++) {
                int cant = 0;
                for (int j = 0; j < listaEvento.cantidadElementos(); j++) {
                    int mesE = listaEvento.obtenerElemento(j).getFecha().getMonthValue();
                    int dia = listaEvento.obtenerElemento(j).getFecha().getDayOfMonth();
                    if (dia == i && mesE == mes) {
                        cant++;
                    }
                }
                if (cant > 0) {
                    cont++;
                    if (cont < listaEvento.cantidadElementos() - 1) {
                        salida += i + "-" + cant + "#";
                    } else {
                        salida += i + "-" + cant;
                    }
                }
            }
        }
        retorno.valorString = lista.toString();
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

    public Lista<Entrada> getListaEntrada() {
        return listaEntrada;
    }

}
