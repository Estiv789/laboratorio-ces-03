
package pe.edu.unsch;

public class CuentaBancaria {

    private double saldo;
    private boolean bloqueada;

    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
        this.bloqueada = false;
    }

    public void depositar(double monto) {
        if (bloqueada || !Double.isFinite(monto) || monto <= 0) {
            return;
        }

        saldo += monto;
    }

    public void retirar(double monto) {
        if (bloqueada || !Double.isFinite(monto) || monto <= 0) {
            return;
        }

        if (monto > saldo) {
            bloqueada = true;
            return;
        }

        saldo -= monto;
    }

    public void transferir(CuentaBancaria destino, double monto) {

        if (bloqueada || destino == null ||
                destino == this || destino.bloqueada ||
                !Double.isFinite(monto) || monto <= 0) {
            return;
        }

        if (monto > saldo) {
            bloqueada = true;
            return;
        }

        this.saldo -= monto;
        destino.saldo += monto;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public double getSaldo() {
        return saldo;
    }
}
