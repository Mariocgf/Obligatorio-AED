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
        System.out.println("----------------------");
        System.out.println("Test registrar sala ok");
        System.out.println("Sala a registrar [Nombre, cantidad]: S1, 10");
        System.out.println("Lista de sala: " + miSistema.listarSalas().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError1() {
        Retorno r = miSistema.registrarSala("S1", 10);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("S1", 20);
        System.out.println("----------------------");
        System.out.println("Test registro de sala error 1");
        System.out.println("Sala ya esta registrada: S1-10");
        System.out.println("Sala a registrar [Nombre, cantidad]: S1, 20");
        System.out.println("Resultado esperado: " + Retorno.error1().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError2Cero() {
        Retorno r = miSistema.registrarSala("S1", 0);
        System.out.println("----------------------");
        System.out.println("Test registro de sala error 2 - cero");
        System.out.println("Sala a registrar[Nombre, cantidad]: S1, 0");
        System.out.println("Resultado esperado: " + Retorno.error2().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarSalaError2Negativo() {
        Retorno r = miSistema.registrarSala("S1", -10);
        System.out.println("----------------------");
        System.out.println("Test registro de sala error 2 - negativo");
        System.out.println("Sala a registrar[Nombre, cantidad]: S1, -10");
        System.out.println("Resultado esperado: " + Retorno.error2().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test eliminar sala ok - al inicio");
        System.out.println("Sala a eliminar: S5");
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test eliminar sala ok - al final");
        System.out.println("Sala a eliminar: S1");
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test eliminar sala ok - en pos 'x'");
        System.out.println("Sala a eliminar: S2");
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test eliminar sala error 1");
        System.out.println("Sala a eliminar: S6");
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testRegistrarEventoOkSinRegistros() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        System.out.println("----------------------");
        System.out.println("Test registrar evento ok - sin registro");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EE2, Evento 1, 15, 2025-03-02");
        System.out.println("Lista de eventos: " + miSistema.listarEventos().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoOkConRegistros() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarEvento("EH5", "Evento 2", 20, LocalDate.of(2025, Month.MARCH, 3));
        System.out.println("----------------------");
        System.out.println("Test registrar evento ok - con registro");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EE2, Evento 1, 15, 2025-03-03");
        System.out.println("Lista de eventos: " + miSistema.listarEventos().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test registrar evento ok - con misma fecha");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EE2, Evento 1, 15, 2025-03-03");
        System.out.println("Evento ya registrado [Codigo, descripcion, aforo, fecha]: EE2, Evento 1, 15, 2025-03-03");
        System.out.println("Lista de eventos: " + miSistema.listarEventos().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testRegistrarEventoError1() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarEvento("EC1", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 2));
        System.out.println("----------------------");
        System.out.println("Test registrar evento error 1");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EC1, Evento 1, 15, 2025-03-03");
        System.out.println("Evento ya registrado [Codigo, descripcion, aforo, fecha]: EC1, Evento 2, 15, 2025-03-02");
        System.out.println("Resultado esperado: " + Retorno.error1().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError2Cero() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", 0, LocalDate.of(2025, Month.MARCH, 1));
        System.out.println("----------------------");
        System.out.println("Test registrar evento error - aforo cero");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EC2, Evento 1, 0, 2025-03-01");
        System.out.println("Resultado esperado: " + Retorno.error2().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError2Negativo() {
        miSistema.registrarSala("S1", 60);
        Retorno r = miSistema.registrarEvento("EC1", "Evento 1", -10, LocalDate.of(2025, Month.MARCH, 1));
        System.out.println("----------------------");
        System.out.println("Test registrar evento error - aforo negativo");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EC2, Evento 1, -10, 2025-03-01");
        System.out.println("Resultado esperado: " + Retorno.error2().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testRegistrarEventoError3() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarEvento("EC1", "Evento 1", 40, LocalDate.of(2025, Month.MARCH, 1));
        Retorno r = miSistema.registrarEvento("EE2", "Evento 2", 15, LocalDate.of(2025, Month.MARCH, 1));
        System.out.println("----------------------");
        System.out.println("Test registrar evento error 3");
        System.out.println("Evento a registrar [Codigo, descripcion, aforo, fecha]: EE2, Evento 2, 15, 2025-03-01");        
        System.out.println("Evento ya registrado [Codigo, descripcion, aforo, fecha]: EC1, Evento 1, 40, 2025-03-01");
        System.out.println("Resultado esperado: " + Retorno.error3().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error3().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteOkSinRegistro() {
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        System.out.println("----------------------");
        System.out.println("Test registrar cliente ok - sin registro");
        System.out.println("Cliente a registrar [Cedula, nombre]: 44444444, Cliente 1");   
        System.out.println("Lista de clientes: " + miSistema.listarClientes().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteOkConRegistro() {
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 1");
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarCliente("55555555", "Cliente 2");
        System.out.println("----------------------");
        System.out.println("Test registrar cliente ok - con registro");
        System.out.println("Cliente a registrar [Cedula, nombre]: 55555555, Cliente 2");        
        System.out.println("Cliente ya registrado [Cedula, nombre]: 44444444, Cliente 1");
        System.out.println("Lista de clientes: " + miSistema.listarClientes().valorString);
        System.out.println("Resultado esperado: " + Retorno.ok().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError1DigitoMenorA8() {
        Retorno r = miSistema.registrarCliente("444444", "Cliente 1");
        System.out.println("----------------------");
        System.out.println("Test registrar cliente error 1 - digito menor a 8");      
        System.out.println("Cliente a registrar [Cedula, nombre]: 444444, Cliente 1");
        System.out.println("Resultado esperado: " + Retorno.error1().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError1DigitoMayorA8() {
        Retorno r = miSistema.registrarCliente("444444444", "Cliente 1");
        System.out.println("----------------------");
        System.out.println("Test registrar cliente error 1 - digito mayor a 8");      
        System.out.println("Cliente a registrar [Cedula, nombre]: 444444444, Cliente 1");
        System.out.println("Resultado esperado: " + Retorno.error1().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }

    @Test
    public void testRegistrarClienteError2() {
        miSistema.registrarCliente("44444444", "Cliente 1");
        Retorno r = miSistema.registrarCliente("44444444", "Cliente 2");
        System.out.println("----------------------");
        System.out.println("Test registrar cliente error 2");      
        System.out.println("Cliente a registrar [Cedula, nombre]: 44444444, Cliente 1");
        System.out.println("Cliente ya registrado [Cedula, nombre]: 44444444, Cliente 2");
        System.out.println("Resultado esperado: " + Retorno.error2().resultado);
        System.out.println("Resultado obtenido: " + r.resultado);
        System.out.println("----------------------");
        assertEquals(Retorno.error2().resultado, r.resultado);
    }

    @Test
    public void testListarSalas() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarSala("S2", 10);
        miSistema.registrarSala("S3", 35);
        Retorno r = miSistema.listarSalas();
        String salida = "S3-35#S2-10#S1-60";
        System.out.println("----------------------");
        System.out.println("Test listar salas");      
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testListarEventos() {
        miSistema.registrarSala("S1", 60);
        miSistema.registrarEvento("EE2", "Evento 1", 15, LocalDate.of(2025, Month.MARCH, 2));
        miSistema.registrarEvento("EH5", "Evento 2", 20, LocalDate.of(2025, Month.MARCH, 3));
        Retorno r = miSistema.listarEventos();
        String salida = "EE2-Evento 1-24-60-0#EH5-Evento 2-24-60-0";
        System.out.println("----------------------");
        System.out.println("Test listar eventos");      
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
        assertEquals(salida, r.valorString);
    }

    @Test
    public void testListarClientes() {
        miSistema.registrarCliente("44444444", "Cliente 1");
        miSistema.registrarCliente("55555555", "Cliente 2");
        Retorno r = miSistema.listarClientes();
        String salida = "44444444-Cliente 1#55555555-Cliente 2";
        System.out.println("----------------------");
        System.out.println("Test listar clientes");      
        System.out.println("Resultado esperado: " + salida);
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test sala óptimo - óptimo");      
        System.out.println("Resultado esperado: Es óptimo");
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
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
        System.out.println("----------------------");
        System.out.println("Test sala óptimo - no óptimo");      
        System.out.println("Resultado esperado: No óptimo");
        System.out.println("Resultado obtenido: " + r.valorString);
        System.out.println("----------------------");
        assertEquals(Retorno.ok("No es óptimo").valorString, r.valorString);
    }

}
