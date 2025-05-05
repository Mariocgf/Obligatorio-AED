package sistemaAutogestion;

import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import java.time.Month;
import tads.Lista;

public class prueba {

    public static void main(String args[]) {
        Sistema sistem = new Sistema();
        String[][] matriz = {
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };
//        Lista<Sala> listaSala = new Lista<>();
//        Sala s1 = new Sala("A1", 5);
//        Sala s2 = new Sala("A2", 50);
//        Sala s3 = new Sala("A3", 10);
//        listaSala.agregarInicio(s1);
//        listaSala.agregarInicio(s2);
//        listaSala.agregarInicio(s3);
//        LocalDate f1 = LocalDate.of(2025, 5, 3);
//        Evento e1 = new Evento("E1", "Evento 1", 8,f1,s1);
//        Evento e2 = new Evento("E2", "Evento 2", 8,f1,s1);
//        listaSala.obtenerElemento(0).getListaEventos().agregarFinal(e1);
//        System.out.println(listaSala.obtenerElemento(0).getListaEventos().obtenerElemento(0).getFecha().getDayOfMonth());
//        int diaSala = listaSala.obtenerElemento(0).getListaEventos().obtenerElemento(0).getFecha().getDayOfMonth();
//        int mesSala = listaSala.obtenerElemento(0).getListaEventos().obtenerElemento(0).getFecha().getMonthValue();
        
        //listaSala.mostrar();
        sistem.crearSistemaDeGestion();
        sistem.registrarSala("S1", 50);
        sistem.registrarSala("S1", 50);
        sistem.registrarSala("S2", 10);
        sistem.registrarSala("S3", 35);
        sistem.registrarSala("S4", 35);
        sistem.listarSalas();
        sistem.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        sistem.registrarEvento("EE2", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
        sistem.registrarEvento("EB3", "Evento 3", 25, LocalDate.of(2025, Month.MARCH, 2));
        sistem.registrarEvento("EA4", "Evento 4", 10, LocalDate.of(2025, Month.MARCH, 3));
        sistem.registrarEvento("EA4", "Evento 4 prueba", 10, LocalDate.of(2025, Month.MARCH, 3));
        sistem.registrarEvento("EA5", "Evento 5 prueba", 0, LocalDate.of(2025, Month.MARCH, 3));
        sistem.getListaSala().insertarEnPos(1, new Sala("S5", 40));
        sistem.listarSalas();
        sistem.listarEventos();
        sistem.registrarCliente("234", "Cliente 1");
        sistem.registrarCliente("134", "Cliente 2");
        sistem.registrarCliente("137", "Cliente 3");
        sistem.registrarCliente("240", "Cliente 3");
        sistem.listarClientes();
        System.out.println(sistem.getListaEvento().cantidadElementos());
//        sistem.registrarEvento("E1", "Evento 1", 15, LocalDate.of(2025, 5, 3));
//        sistem.registrarEvento("E2", "Evento 2", 15, LocalDate.of(2025, 5, 3));
//        System.out.println("Sala 1: " + sistem.getListaSala().obtenerElemento(0).getListaEventos().cantidadElementos());
//        System.out.println("Sala 2: " + sistem.getListaSala().obtenerElemento(1).getListaEventos().cantidadElementos());
//        sistem.getListaSala().obtenerElemento(0).getListaEventos().mostrar();
//        sistem.getListaSala().obtenerElemento(1).getListaEventos().mostrar();
    }
}
