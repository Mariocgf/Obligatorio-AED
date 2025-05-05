/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sistemaAutogestion;

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
    }

    @Test
    public void testCrearSistemaDeGestion() {
        //Completar para primera entrega
    }

    @Test
    public void testRegistrarSala() {
        //Completar para primera entrega
    }

    @Test
    public void testEliminarSala() {
        //Completar para primera entrega
    }

    @Test
    public void testRegistrarEvento() {
        //Completar para primera entrega
    }

    @Test
    public void testRegistrarCliente() {
        //Completar para primera entrega
    }

    @Test
    public void testListarSalas() {
        //Completar para primera entrega
    }

    @Test
    public void testListarEventos() {
        //Completar para primera entrega
    }

    @Test
    public void testListarClientes() {
        //Completar para primera entrega
    }

    @Test
    public void testEsSalaOptima() {
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
        assertEquals(Retorno.ok("Es optimo").resultado, r.resultado);
    }

}
