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
        Retorno r = sistem.listarSalas();
        System.out.println(r.valorString);
        //1.3
        sistem.eliminarSala("S3");
        r = sistem.listarSalas();
        System.out.println(r.valorString);
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
        r = sistem.listarEventos();
        System.out.println(r.valorString);
        System.out.println("");
        //1.5
        sistem.registrarCliente("35679992", "Ramiro Perez");
        sistem.registrarCliente("45678992", "Micaela Ferrez");
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

        System.out.println("--- Parte segundo obligatorio ---");
        //1.6
        System.out.println("1.6 - Comprar entrada");
        sistem.comprarEntrada("35679992", "EC1");
        sistem.comprarEntrada("45678992", "EC1");
        //1.7
        System.out.println("1.7 - Eliminar evento:");
        System.out.println("Eliminar evento con entrada: " + sistem.eliminarEvento("EC1").valorString);
        System.out.println("Eliminar evento sin entrada: " + sistem.eliminarEvento("EE2").resultado);
        System.out.println("Eliminar evento ni existente: " + sistem.eliminarEvento("EE22").valorString);
        //1.8
        System.out.println("1.8 - Devolver entrada");
        System.out.println("Devolver entrada a cliente inexistente: " + sistem.devolverEntrada("12345678", "EE1").valorString);
        System.out.println("Devolver entrada a evento inexistente: " + sistem.devolverEntrada("44444444", "EE1").valorString);
        System.out.println("Devolver entrada todo los campos correcto: " + sistem.devolverEntrada("44444444", "EC1").resultado);
        
        //1.9
        System.out.println("1.9 - Calificar evento");
        System.out.println("Calificar evento error 1: " + sistem.calificarEvento("12345678", "EE1", 5, "Primer comentario").valorString);
        System.out.println("Calificar evento error 2: " + sistem.calificarEvento("44444444", "EE1", 5, "Primer comentario").valorString);
        System.out.println("Calificar evento error 3, puntaje menor a 1: " + sistem.calificarEvento("44444444", "EC1", 0, "Primer comentario").resultado);
        System.out.println("Calificar evento error 3, puntaje mayor a 10: " + sistem.calificarEvento("44444444", "EC1", 11, "Primer comentario").valorString);
        System.out.println("Calificar evento con todo correcto: " + sistem.calificarEvento("44444444", "EC1", 9, "Primer comentario").resultado);
        System.out.println("Calificar evento error 4: " + sistem.calificarEvento("44444444", "EC1", 10, "Primer comentario").resultado);
        
        //2.5
        System.out.println("2.5 - Listar clientes de evento");
        System.out.println("Evento EC1 - cantidad mayor al contenido de la lista");
        System.out.println(sistem.listarClientesDeEvento("EC1", 3).valorString);
        System.out.println("Evento EC1 - cantidad menor(1) al contenido de la lista");
        System.out.println(sistem.listarClientesDeEvento("EC1", 1).valorString);
        
        System.out.println("2.9 - Compras de cliente");
        System.out.println(sistem.comprasDeCliente("44444444").valorString);
    }
}
