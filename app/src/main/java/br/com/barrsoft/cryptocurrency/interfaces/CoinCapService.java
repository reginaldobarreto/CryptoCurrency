package br.com.barrsoft.cryptocurrency.interfaces;

import java.util.List;
import br.com.barrsoft.cryptocurrency.models.CryptoCurrency;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinCapService {

    @GET("front")
    //chamada para a classe model
    Call<List<CryptoCurrency>> getCryptoCurrency();
}
