package cs_tcp_03_calcolatrice;



public class Calcolatrice {
    // Metodo per la somma di due numeri
    public double addizione(double a, double b) {
        return a + b;
    }

    // Metodo per la sottrazione di due numeri
    public double sottrazione(double a, double b) {
        return a - b;
    }

    // Metodo per la moltiplicazione di due numeri
    public double moltiplicazione(double a, double b) {
        return a * b;
    }

    // Metodo per la divisione di due numeri
    public double divisione(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Divisione per zero!");
        }
        return a / b;
    }
}

