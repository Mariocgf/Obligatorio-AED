/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sistemaAutogestion;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pesce
 */
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
        miSistema.registrarSala("S1", 10);
        Retorno r = miSistema.registrarSala("S1", 10);
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
    @Test
    public void testRegistrarSalaError2Cero() {
        Retorno r = miSistema.registrarSala("S1", 0);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }
    @Test
    public void testRegistrarSalaError2Negativo() {
        Retorno r = miSistema.registrarSala("S1", 0);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testEliminarSalaOk() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        miSistema.registrarSala("S4", 30);
        miSistema.registrarSala("S5", 55);
        Retorno r = miSistema.eliminarSala("S3");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
    @Test
    public void testEliminarSalaError1() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        Retorno r = miSistema.eliminarSala("S6");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoOk() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
    @Test
    public void testRegistrarEventoError1() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        Retorno r = miSistema.registrarEvento("EC1", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
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
    public void testRegistrarClienteOk() {
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
    @Test
    public void testRegistrarClienteError1() {
        Retorno r = miSistema.registrarCliente("444444", "Cliente 1");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
    @Test
    public void testRegistrarClienteError2() {
        miSistema.registrarCliente("44444444", "Cliente 1");
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testListarSalas() {
        Retorno r = miSistema.listarSalas();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testListarEventos() {
        Retorno r = miSistema.listarEventos();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testListarClientes() {
        Retorno r = miSistema.listarClientes();
        assertEquals(Retorno.ok().resultado, r.resultado);
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
        assertEquals(Retorno.ok("Es optimo").valorString, r.valorString);
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
        assertEquals(Retorno.ok("No es optimo").valorString, r.valorString);
    }

}
