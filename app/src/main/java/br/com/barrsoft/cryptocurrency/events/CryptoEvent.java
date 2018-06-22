package br.com.barrsoft.cryptocurrency.events;

import java.util.List;

import br.com.barrsoft.cryptocurrency.models.CryptoCurrency;

public class CryptoEvent {


    private final List<CryptoCurrency> cryptoCurrencies;

    public CryptoEvent(List<CryptoCurrency> cryptoCurrencies) {

        this.cryptoCurrencies = cryptoCurrencies;
    }

    public List<CryptoCurrency> getCryptoCurrencies(){
        return cryptoCurrencies;
    }

}
