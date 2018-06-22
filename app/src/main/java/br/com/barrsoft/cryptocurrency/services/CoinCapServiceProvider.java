package br.com.barrsoft.cryptocurrency.services;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.com.barrsoft.cryptocurrency.events.CryptoErrorEvent;
import br.com.barrsoft.cryptocurrency.events.CryptoEvent;
import br.com.barrsoft.cryptocurrency.interfaces.CoinCapService;
import br.com.barrsoft.cryptocurrency.models.CryptoCurrency;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinCapServiceProvider {

    //https://github.com/CoinCapDev/CoinCap.io
    private static final String BASE_URL = "http://coincap.io/";
    private static final String TAG = CoinCapServiceProvider.class.getName();
    private Retrofit retrofit;

    private Retrofit getretrofit(){

        //verificar existencia de instancia retrofit
        if(this.retrofit == null){
            //construir o retrofit
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getCoinCurrency(){

        //criar a interface de servico
        CoinCapService coinCapService = getretrofit().create(CoinCapService.class);
        Call<List<CryptoCurrency>> coinCapData = coinCapService.getCryptoCurrency();
        //consulta
        coinCapData.enqueue(new Callback<List<CryptoCurrency>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"OnResponse " +response.code());
                }else{
                    List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
                    cryptoCurrencies = response.body();
                        Log.i(TAG, "OnResponse "+ response.body().size());
                        EventBus.getDefault().post(new CryptoEvent(cryptoCurrencies));
                }
            }

            @Override
            public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                Log.i(TAG, "OnFailure " + t.getMessage());
                EventBus.getDefault().post(new CryptoErrorEvent("Não foi possivel conectar ao servidor"));
            }
        });
    }
}