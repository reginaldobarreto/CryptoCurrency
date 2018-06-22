package br.com.barrsoft.cryptocurrency.events;

public class CryptoEventButton {

    private final int numero;

    public CryptoEventButton(int numberRemove) {
        this.numero = numberRemove;
    }

    public int getNumero() {
        return numero;
    }
}
