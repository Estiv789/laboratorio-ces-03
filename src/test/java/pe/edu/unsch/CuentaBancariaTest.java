
package pe.edu.unsch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CuentaBancariaTest {


    // PU-01: Retiro superior al saldo
    @Test
    void retiroInsuficienteBloqueaLaCuenta() {
        CuentaBancaria cuenta = new CuentaBancaria(100);

        cuenta.retirar(150);

        assertTrue(cuenta.isBloqueada());
        assertEquals(100, cuenta.getSaldo());
    }

    // PU-02: Retiro después del bloqueo
    @Test
    void cuentaBloqueadaNoPermiteRetirar() {
        CuentaBancaria cuenta = new CuentaBancaria(100);

        cuenta.retirar(150);
        cuenta.retirar(20);

        assertTrue(cuenta.isBloqueada());
        assertEquals(100, cuenta.getSaldo());
    }

    // PU-03: Depósito después del bloqueo
    @Test
    void cuentaBloqueadaNoPermiteDepositar() {
        CuentaBancaria cuenta = new CuentaBancaria(100);

        cuenta.retirar(150);
        cuenta.depositar(200);

        assertEquals(100, cuenta.getSaldo());
        assertTrue(cuenta.isBloqueada());
    }

    // PU-04: Retiro con saldo suficiente
    @Test
    void retiroValidoNoBloqueaLaCuenta() {
        CuentaBancaria cuenta = new CuentaBancaria(100);

        cuenta.retirar(30);

        assertFalse(cuenta.isBloqueada());
        assertEquals(70, cuenta.getSaldo());
    }

    // PU-05: Retiro con monto negativo
    @Test
    void montoNegativoNoProduceBloqueo() {
        CuentaBancaria cuenta = new CuentaBancaria(100);

        cuenta.retirar(-20);

        assertFalse(cuenta.isBloqueada());
        assertEquals(100, cuenta.getSaldo());
    }

    // PU-06: Transferencia con saldo insuficiente
    @Test
    void transferenciaInsuficienteBloqueaOrigenSinAlterarDestino() {
        CuentaBancaria origen = new CuentaBancaria(100);
        CuentaBancaria destino = new CuentaBancaria(60);

        origen.transferir(destino, 150);

        assertTrue(origen.isBloqueada());
        assertEquals(100, origen.getSaldo());
        assertEquals(60, destino.getSaldo());
    }

    // PU-07: Transferencia a cuenta bloqueada
    @Test
    void transferenciaADestinoBloqueadoConservaLosDosSaldos() {
        CuentaBancaria origen = new CuentaBancaria(1000);
        CuentaBancaria destino = new CuentaBancaria(100);

        destino.retirar(200);
        origen.transferir(destino, 150);

        assertEquals(1000, origen.getSaldo());
        assertEquals(100, destino.getSaldo());
        assertFalse(origen.isBloqueada());
        assertTrue(destino.isBloqueada());
    }
}
