package br.com.barrsoft.cryptocurrency.events;

public class CryptoErrorEvent {


    private final String errorMessage;

    public CryptoErrorEvent(String MensagemErro) {
        this.errorMessage = MensagemErro;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
