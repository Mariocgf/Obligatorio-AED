package sistemaAutogestion;

import java.time.LocalDate;
import java.time.Month;

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
        String[][] matriz2 = {
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "X", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };
        //1.1
        sistem.crearSistemaDeGestion();
        //1.2
        System.out.println(sistem.registrarSala("S1", 50).resultado);
        sistem.registrarSala("S1", 60);
        sistem.registrarSala("S2", 10);
        sistem.registrarSala("S3", 35);
        sistem.registrarSala("S4", 30);
        sistem.registrarSala("S5", 55);
        sistem.registrarSala("S6", 0);
        sistem.registrarSala("S7", -10);
        //2.1
        System.out.println("2.1 - Listado de salas:");
        sistem.listarSalas();
        //1.3
        sistem.eliminarSala("S3");
        sistem.listarSalas();
        System.out.println("");
        //1.4
        sistem.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        sistem.registrarEvento("EE2", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
        sistem.registrarEvento("EB3", "Evento 3", 25, LocalDate.of(2025, Month.MARCH, 2));
        sistem.registrarEvento("EA4", "Evento 4", 10, LocalDate.of(2025, Month.MARCH, 3));
        sistem.registrarEvento("EA4", "Evento 4 prueba", 10, LocalDate.of(2025, Month.MARCH, 3));
        sistem.registrarEvento("EA5", "Evento 5 prueba", 0, LocalDate.of(2025, Month.MARCH, 3));
        //2.2
        System.out.println("2.2 - Listado de eventos;");
        sistem.listarEventos();
        System.out.println("");
        //1.5
        sistem.registrarCliente("44444444", "Cliente 1");
        sistem.registrarCliente("44444444", "Cliente 1");
        sistem.registrarCliente("33333333", "Cliente 2");
        sistem.registrarCliente("11111111", "Cliente 3");
        sistem.registrarCliente("22222222", "Cliente 4");
        //2.3
        System.out.println("2.3 - Listado de clientes:");
        sistem.listarClientes();
        System.out.println("");
        //2.4
        System.out.println("2.4 - Sala optima: " + sistem.esSalaOptima(matriz).valorString);
        System.out.println("2.4 - Sala optima: " + sistem.esSalaOptima(matriz2).valorString);
        
//        sistem.registrarEvento("E1", "Evento 1", 15, LocalDate.of(2025, 5, 3));
//        sistem.registrarEvento("E2", "Evento 2", 15, LocalDate.of(2025, 5, 3));
//        System.out.println("Sala 1: " + sistem.getListaSala().obtenerElemento(0).getListaEventos().cantidadElementos());
//        System.out.println("Sala 2: " + sistem.getListaSala().obtenerElemento(1).getListaEventos().cantidadElementos());
//        sistem.getListaSala().obtenerElemento(0).getListaEventos().mostrar();
//        sistem.getListaSala().obtenerElemento(1).getListaEventos().mostrar();
    }
}
