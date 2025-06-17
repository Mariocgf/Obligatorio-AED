package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class IObligatorioTestDocentes {

    private Sistema sistema;

    public IObligatorioTestDocentes() {
        sistema = new Sistema();
    }

    @Before
    public void setUp() {
        sistema = new Sistema();
        sistema.crearSistemaDeGestion();
    }

    @Test
    public void testCrearSistemaDeGestion() {
        Retorno ret = sistema.crearSistemaDeGestion();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    // ----- SALAS -----

    @Test
    public void testRegistrarSala_OK() {
        Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR1_Duplicada() {
        sistema.registrarSala("Sala A", 50);

        Retorno ret = sistema.registrarSala("Sala A", 100);
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

 	ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR2_CapacidadInvalida() {
        Retorno ret = sistema.registrarSala("Sala B", 0);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

        ret = sistema.registrarSala("Sala C", -10);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

	ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testEliminarSala_OK() {
        sistema.registrarSala("Sala A", 50);
        Retorno ret = sistema.eliminarSala("Sala A");

	ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

	ret = sistema.eliminarSala("Sala B");
  	assertEquals(Retorno.Resultado.OK, ret.resultado);
    
    }


    @Test
    public void testEliminarSala_ERROR1() {
        sistema.registrarSala("Sala A", 50);
	sistema.registrarSala("Sala C", 50);
	sistema.registrarSala("Sala H", 10);
        Retorno ret = sistema.eliminarSala("Sala B");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

	ret = sistema.eliminarSala("Sala A");
  	assertEquals(Retorno.Resultado.OK, ret.resultado);
	ret = sistema.eliminarSala("Sala H");
  	assertEquals(Retorno.Resultado.OK, ret.resultado);
    }




    @Test
    public void testListarSalas_OK() {
        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala B", 70);
        sistema.registrarSala("Sala C", 100);
        Retorno ret = sistema.listarSalas();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("Sala C-100#Sala B-70#Sala A-50", ret.valorString);
    }

    @Test
    public void testListarSalas_Vacio() {
        Retorno ret = sistema.listarSalas();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    // ----- CLIENTES -----

    @Test
    public void testRegistrarCliente_OK() {
        Retorno ret = sistema.registrarCliente("12345678", "Juan Pérez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarCliente_ERROR1_CedulaInvalida() {
        Retorno ret = sistema.registrarCliente("1234", "Juan Pérez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("AA345444", "Martina Perez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testRegistrarCliente_ERROR2_Duplicado() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        Retorno ret = sistema.registrarCliente("12345678", "Otro Nombre");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("22222222", "Rodolfo Perez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testListarClientes_OK() {
        sistema.registrarCliente("45678992", "Micaela Ferrez");
        sistema.registrarCliente("23331111", "Martina Rodríguez");
        sistema.registrarCliente("35679992", "Ramiro Perez");

        Retorno ret = sistema.listarClientes();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("23331111-Martina Rodríguez#35679992-Ramiro Perez#45678992-Micaela Ferrez", ret.valorString);
    }

    @Test
    public void testListarClientes_Vacio() {
        Retorno ret = sistema.listarClientes();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    // ----- EVENTOS -----

    @Test
    public void testRegistrarEvento_OK() {
        sistema.registrarSala("Sala A", 75);
        sistema.registrarSala("Sala B", 100);
        sistema.registrarSala("Sala C", 70);

        LocalDate fecha1 = LocalDate.of(2025, 5, 10);
	    LocalDate fecha2 = LocalDate.of(2025, 5, 4);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 80, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

	ret = sistema.registrarEvento("EVT02", "Arte", 74, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarEvento_Error1() {

        sistema.registrarSala("Sala A", 50);
        LocalDate fecha = LocalDate.of(2025, 5, 10);

 	sistema.registrarSala("Sala B", 20);

        Retorno ret = sistema.registrarEvento("EVT02", "Musica", 40, LocalDate.of(2025, 5, 12));
        assertEquals(Retorno.Resultado.OK, ret.resultado); 

	ret = sistema.registrarEvento("EVT07", "Otro", 10, LocalDate.of(2025, 5, 12));
        assertEquals(Retorno.Resultado.OK, ret.resultado); 

        ret = sistema.registrarEvento("EVT02", "Duplicado", 40, LocalDate.of(2025, 5, 1));
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado); 
    }


    @Test
    public void testRegistrarEvento_Error2() {

        sistema.registrarSala("Sala A", 50);
        LocalDate fecha = LocalDate.of(2025, 5, 10);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 20, fecha);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT03", "Otro", -4, fecha);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }


    @Test
    public void testRegistrarEvento_Error3() {

        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala B", 32);
	sistema.registrarSala("Sala T", 10);
        LocalDate fecha1 = LocalDate.of(2025, 5, 10);
        LocalDate fecha2 = LocalDate.of(2025, 5, 5);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 40, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT02", "Concierto", 38, fecha1);
        assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);

        ret = sistema.registrarEvento("EVT06", "Deportes", 40, fecha2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testListarEventos_OK() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("TEC43", "Seminario de Tecnología", 45, LocalDate.of(2025, 5, 4));
        sistema.registrarEvento("KAK34", "Noche de Rock", 45, LocalDate.of(2025, 5, 6));
        sistema.registrarEvento("CUC22", "Tango Azul", 11, LocalDate.of(2025, 5, 8));

        Retorno ret = sistema.listarEventos();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("CUC22-Tango Azul-Sala A-11-0#KAK34-Noche de Rock-Sala A-45-0#TEC43-Seminario de Tecnología-Sala A-45-0", ret.valorString);
    }

    // ----- SALA ÓPTIMA -----

    @Test
    public void testEsSalaOptima_EsOptimo() {
        String[][] vistaSala = {
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };

        Retorno ret = sistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("Es óptimo", ret.valorString);
    }

    @Test
    public void testEsSalaOptima_NoEsOptimo() {
        String[][] vistaSala = {
            {"#", "#", "#", "#"},
            {"#", "X", "O", "#"},
            {"#", "X", "X", "#"},
            {"#", "O", "X", "#"}
        };

        Retorno ret = sistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("No es óptimo", ret.valorString);
    }
}