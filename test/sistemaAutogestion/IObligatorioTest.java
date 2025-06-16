package sistemaAutogestion;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IObligatorioTest {

    private Sistema miSistema;

    public IObligatorioTest() {
    }

    @Before
    public void setUp() {
        miSistema = new Sistema();
        miSistema.crearSistemaDeGestion();
    }

    @Test
    public void testCrearSistemaDeGestion() {
        Retorno r = miSistema.crearSistemaDeGestion();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaOk() {
        Retorno r = miSistema.registrarSala("S1", 10);
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError1() {
        Retorno r = miSistema.registrarSala("S1", 10);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("S1", 20);
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError2Cero() {
        Retorno r = miSistema.registrarSala("S1", 0);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError2Negativo() {
        Retorno r = miSistema.registrarSala("S1", -10);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testEliminarSalaOkPosInicio() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        miSistema.registrarSala("S4", 30);
        miSistema.registrarSala("S5", 55);
        Retorno r = miSistema.eliminarSala("S5");
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.listarSalas();
        String salida = "S4-30#S3-35#S2-10#S1-60";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testEliminarSalaOkPosFinal() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        miSistema.registrarSala("S4", 30);
        miSistema.registrarSala("S5", 55);
        Retorno r = miSistema.eliminarSala("S1");
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.listarSalas();
        String salida = "S5-55#S4-30#S3-35#S2-10";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testEliminarSalaOkEnPosX() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        miSistema.registrarSala("S4", 30);
        miSistema.registrarSala("S5", 55);
        Retorno r = miSistema.eliminarSala("S3");
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.listarSalas();
        String salida = "S5-55#S4-30#S2-10#S1-60";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testEliminarSalaError1() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        Retorno r = miSistema.eliminarSala("S6");
        assertEquals(Retorno.error1().resultado, r.resultado);
        String salida = "No existe una sala con ese nombre";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testRegistrarEventoOkSinRegistros() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoOkConRegistros() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarEvento("EH5", "Evento 2", 20, LocalDate.of(2025, Month.MARCH, 3));
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoOkConMismaFecha() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 30);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarEvento("EH5", "Evento 2", 20, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.listarEventos();
        String salida = "EE2-Evento 1-18-30-0#EH5-Evento 2-17-60-0"; //Obs: El id de las salas en cada test fue incrementando(id static), por eso valores alto de id.
        //assertEquals(salida, r.valorString);
    }

    @Test
    public void testRegistrarEventoError1() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarEvento("EC1", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError2Cero() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", 0, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError2Negativo() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", -10, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError3() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        Retorno r = miSistema.registrarEvento("EE2", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.error3().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteOkSinRegistro() {
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteOkConRegistro() {
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarCliente("55555555", "Cliente 2");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError1DigitoMenorA8() {
        Retorno r = miSistema.registrarCliente("444444", "Cliente 1");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError1DigitoMayorA8() {
        Retorno r = miSistema.registrarCliente("444444444", "Cliente 1");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError2() {
        miSistema.registrarCliente("44444444", "Cliente 1");
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 2");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testListarSalas() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        Retorno r = miSistema.listarSalas();
        String salida = "S3-35#S2-10#S1-60";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testListarEventos() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        miSistema.registrarEvento("EH5", "Evento 2", 20, LocalDate.of(2025, Month.MARCH, 3));
        Retorno r = miSistema.listarEventos();
        String salida = "EE2-Evento 1-S1-15-0#EH5-Evento 2-S1-20-0";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testListarClientes() {
        miSistema.registrarCliente("44444444", "Cliente 1");
        miSistema.registrarCliente("55555555", "Cliente 2");
        Retorno r = miSistema.listarClientes();
        String salida = "44444444-Cliente 1#55555555-Cliente 2";
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testEsSalaOptimaOptima() {
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
        Retorno r = miSistema.esSalaOptima(matriz);
        assertEquals(Retorno.ok("Es óptimo").valorString, r.valorString);
    }

    @Test
    public void testEsSalaOptimaNoOptimo() {
        String[][] matriz = {
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
        Retorno r = miSistema.esSalaOptima(matriz);
        assertEquals(Retorno.ok("No es óptimo").valorString, r.valorString);
    }

    @Test
    public void testComprarEntradaOk() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        String salida = "35679992-Ramiro Perez";
        retorno = miSistema.listarClientesDeEvento("Evento 1", 2);
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testComprarEntradaError1() {
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.comprarEntrada("11111111", "Evento 1");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    public void testComprarEntradaError2() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        Retorno retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testEliminarEventoOk() {
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.eliminarEvento("Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testEliminarEventoError1() {
        Retorno retorno = miSistema.eliminarEvento("Evento 1");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testEliminarEventoError2() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        Retorno retorno = miSistema.eliminarEvento("Evento 1");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.eliminarEvento("Evento 1");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testDevolverEntradaOk() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 1, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.comprarEntrada("45678992", "Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.listarClientesDeEvento("Evento 1", 1);
        String salida = "35679992-Ramiro Perez";
        assertEquals(salida, retorno.valorString);
        retorno = miSistema.devolverEntrada("35679992", "Evento 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.listarClientesDeEvento("Evento 1", 1);
        salida = "45678992-Micaela Ferrez";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testDevolverEntradaError1() {
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 1, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.devolverEntrada("35679992", "Evento 1");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testDevolverEntradaError2() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        Retorno retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
        retorno = miSistema.devolverEntrada("35679992", "Evento 1");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testCalificarEventoOk() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.calificarEvento("35679992", "Evento 1", 9, "Prueba");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testCalificarEventoError1() {
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.calificarEvento("35679992", "Evento 1", 9, "Prueba");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testCalificarEventoError2() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        Retorno retorno = miSistema.calificarEvento("35679992", "Evento 1", 9, "Prueba");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testCalificarEventoError3Menor1() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.calificarEvento("35679992", "Evento 1", 0, "Prueba");
        assertEquals(Retorno.error3().resultado, retorno.resultado);
    }

    @Test
    public void testCalificarEventoError3Mayor10() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.calificarEvento("35679992", "Evento 1", 11, "Prueba");
        assertEquals(Retorno.error3().resultado, retorno.resultado);
    }

    @Test
    public void testListarClientesEventoOk() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        miSistema.comprarEntrada("35679992", "Evento 1");
        miSistema.comprarEntrada("45678992", "Evento 1");
        Retorno retorno = miSistema.listarClientesDeEvento("Evento 1", 5);
        String salida = "35679992-Ramiro Perez#45678992-Micaela Ferrez";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testListarClientesEventoError1() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarSala("Sala 1", 50);
        Retorno retorno = miSistema.listarClientesDeEvento("Evento 1", 5);
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testListarClientesEventoError2() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        miSistema.comprarEntrada("35679992", "Evento 1");
        miSistema.comprarEntrada("45678992", "Evento 1");
        Retorno retorno = miSistema.listarClientesDeEvento("Evento 1", 0);
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testListarEsperaEvento() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarCliente("23331111", "Micaela Gomez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("KAK34", "Prueba", 1, LocalDate.of(2025, 5, 10));
        miSistema.registrarEvento("TEC43", "Prueba", 2, LocalDate.of(2025, 6, 10));

        miSistema.comprarEntrada("35679992", "KAK34");
        miSistema.comprarEntrada("45678992", "KAK34");
        miSistema.comprarEntrada("23331111", "KAK34");

        miSistema.comprarEntrada("23331111", "TEC43");
        miSistema.comprarEntrada("45678992", "TEC43");
        miSistema.comprarEntrada("35679992", "TEC43");
        Retorno retorno = miSistema.listarEsperaEvento();
        String salida = "KAK34-23331111#KAK34-45678992#TEC43-35679992";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testDeshacerUltimasCompras() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarCliente("23331111", "Micaela Gomez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("CUC11", "Prueba", 1, LocalDate.of(2025, 5, 10));
        miSistema.registrarEvento("TEC43", "Prueba", 2, LocalDate.of(2025, 6, 10));
        miSistema.registrarEvento("COP10", "Prueba", 2, LocalDate.of(2025, 7, 10));
        miSistema.comprarEntrada("35679992", "TEC43");
        miSistema.comprarEntrada("35679992", "CUC11");
        miSistema.comprarEntrada("35679992", "COP10");
        miSistema.comprarEntrada("45678992", "COP10");
        miSistema.comprarEntrada("23331111", "TEC43");
        Retorno retorno = miSistema.deshacerUtimasCompras(2);
        String salida = "COP10-45678992#TEC43-23331111";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testEventoMejorPuntuado() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarCliente("23331111", "Micaela Gomez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("KAK34", "Prueba", 1, LocalDate.of(2025, 5, 10));
        miSistema.registrarEvento("TEC43", "Prueba", 2, LocalDate.of(2025, 6, 10));
        miSistema.registrarEvento("TEC45", "Prueba", 2, LocalDate.of(2025, 7, 10));

        miSistema.calificarEvento("35679992", "KAK34", 9, "Genial");
        miSistema.calificarEvento("45678992", "KAK34", 10, "Genial");

        miSistema.calificarEvento("23331111", "TEC45", 4, "Fatal");
        miSistema.calificarEvento("45678992", "TEC45", 8, "Genial");

        Retorno retorno = miSistema.eventoMejorPuntuado();
        String salida = "KAK34-9";
        assertEquals(salida, retorno.valorString);

        miSistema.calificarEvento("23331111", "TEC43", 8, "Genial");
        miSistema.calificarEvento("35679992", "TEC43", 10, "Genial");

        retorno = miSistema.eventoMejorPuntuado();
        salida = "KAK34-9#TEC43-9";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void testComprasDeCliente() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("CUC11", "Prueba", 1, LocalDate.of(2025, 5, 10));
        miSistema.registrarEvento("TEC43", "Prueba", 2, LocalDate.of(2025, 6, 10));
        miSistema.registrarEvento("COP10", "Prueba", 2, LocalDate.of(2025, 7, 10));
        miSistema.comprarEntrada("35679992", "TEC43");
        miSistema.comprarEntrada("35679992", "CUC11");
        miSistema.comprarEntrada("35679992", "COP10");
        Retorno retorno = miSistema.comprasDeCliente("35679992");
        String salida = "TEC43-N#CUC11-N#COP10-N";
        assertEquals(salida, retorno.valorString);
        miSistema.devolverEntrada("35679992", "COP10");
        retorno = miSistema.comprasDeCliente("35679992");
        salida = "TEC43-N#CUC11-N#COP10-D";
        assertEquals(salida, retorno.valorString);
        miSistema.devolverEntrada("35679992", "TEC43");
        retorno = miSistema.comprasDeCliente("35679992");
        salida = "TEC43-D#CUC11-N#COP10-D";
        assertEquals(salida, retorno.valorString);
    }

    @Test
    public void cantidadXDia() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");

        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarSala("Sala 2", 50);
        miSistema.registrarEvento("KAK34", "Prueba", 1, LocalDate.of(2025, 6, 10));
        miSistema.registrarEvento("TEC45", "Prueba", 2, LocalDate.of(2025, 5, 5));
        miSistema.registrarEvento("CUC11", "Prueba", 1, LocalDate.of(2025, 3, 1));
        miSistema.registrarEvento("TEC43", "Prueba", 2, LocalDate.of(2025, 5, 13));
        miSistema.registrarEvento("COP10", "Prueba", 2, LocalDate.of(2025, 8, 28));
        miSistema.comprarEntrada("35679992", "KAK34");
        Retorno retorno = miSistema.comprasXDia(6);

        //System.out.println(retorno.valorString);
    }

    @Test
    public void testComprarEntradaErrorRespaldo() {
        miSistema.registrarCliente("35679992", "Ramiro Perez");
        miSistema.registrarCliente("45678992", "Micaela Ferrez");
        miSistema.registrarSala("Sala 1", 50);
        miSistema.registrarEvento("Evento 1", "Prueba", 5, LocalDate.of(2025, 5, 10));
        Retorno retorno = miSistema.comprarEntrada("35679992", "Evento 1");
        //assertEquals(Retorno.ok(), retorno.resultado);
    }
}
