package br.com.barrsoft.cryptocurrency.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import br.com.barrsoft.cryptocurrency.R;
import br.com.barrsoft.cryptocurrency.adapters.RecyclerAdapter;
import br.com.barrsoft.cryptocurrency.events.CryptoErrorEvent;
import br.com.barrsoft.cryptocurrency.events.CryptoEvent;
import br.com.barrsoft.cryptocurrency.events.CryptoEventButton;
import br.com.barrsoft.cryptocurrency.models.CryptoCurrency;
import br.com.barrsoft.cryptocurrency.services.CoinCapServiceProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //atributos
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.textViewTotal) TextView textViewTotal;
    @BindView(R.id.floatButton) FloatingActionButton buttonAdicionar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private List<CryptoCurrency> list = new ArrayList<>();
    private List<CryptoCurrency> listAlert = new ArrayList<>();
    private Integer totalMoedas;
    private int opcaoSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(list));

        requestCoinCurrency();

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.setAdapter(new RecyclerAdapter(list));

                prepareListCryptoAlert();

                final String listArrayAlert[] = new String[20];
                for (int x = 0 ; x <= 19; x++){
                    listArrayAlert[x] = listAlert.get(x).getLongName();
                }

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Escolha uma cryptomoeda");
                alertDialog.setSingleChoiceItems(listArrayAlert, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        opcaoSelecionada(which);
                    }
                });

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adicionarMoeda(opcaoSelecionada);

                    }
                });

                alertDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_refresh:

                atualizaRecycler();
                break;
            case R.id.item_about:

                about();
                break;
            case R.id.item_asc:

                ordenarAsc();
                break;
            case R.id.item_des:

                ordenarDes();
                break;
            case R.id.item_search:

                searchCryptoCurrency();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void about() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void opcaoSelecionada(int which) {
        this.opcaoSelecionada = which;
    }

    private void prepareListCryptoAlert() {

        listAlert.clear();

        for (int i = 0; i <= 20; i++){
            Random random = new Random();
            int x = random.nextInt(1000)+1;
            listAlert.add(list.get(x));
        }
    }

    private void searchCryptoCurrency() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText editText = new EditText(MainActivity.this);
        alert.setTitle("Busca de moeda");
        alert.setMessage("Entre com a moedas que deseja localizar");
        alert.setView(editText);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String moeda = String.valueOf(editText.getText());
                buscarMoeda(moeda);
            }
        });
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void buscarMoeda(final String moeda) {

        if(moeda.isEmpty() || moeda.startsWith(" ")){
            Toast.makeText(getApplicationContext(),"Preencha os dados corretamente",Toast.LENGTH_LONG).show();
        }else{
            try{

                CryptoCurrency find = new CryptoCurrency();
                find.setLongName(moeda);
                ordenarAsc();

                int posicaoMoeda = Collections.binarySearch(list, find, new Comparator<CryptoCurrency>() {
                    @Override
                    public int compare(CryptoCurrency o1, CryptoCurrency o2) {
                        return o1.getLongName().compareToIgnoreCase(o2.getLongName());
                    }
                });

                List<CryptoCurrency> novaList = new ArrayList<>();
                CryptoCurrency cryptoCurrency = new CryptoCurrency();
                cryptoCurrency.setLongName(list.get(posicaoMoeda).getLongName());
                cryptoCurrency.setShortName(list.get(posicaoMoeda).getShortName());
                cryptoCurrency.setPrice(list.get(posicaoMoeda).getPrice());
                novaList.add(cryptoCurrency);
                recyclerView.setAdapter(new RecyclerAdapter(novaList));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Moeda não localizada",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ordenarAsc() {

        Collections.sort(list, new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency o1, CryptoCurrency o2) {
                atualizaRecycler();
                return o1.getLongName().compareToIgnoreCase(o2.getLongName());
            }
        });
    }

    private void ordenarDes() {

        Collections.sort(list, new Comparator<CryptoCurrency>() {
            @Override
            public int compare(CryptoCurrency o1, CryptoCurrency o2) {
                atualizaRecycler();
                return o2.getLongName().compareToIgnoreCase(o1.getLongName());
            }
        });

    }

    private void requestCoinCurrency() {
        CoinCapServiceProvider coinCapServiceProvider = new CoinCapServiceProvider();
        coinCapServiceProvider.getCoinCurrency();
    }

    private void adicionarMoeda(int which) {

        list.add(0,listAlert.get(which));
        atualizaQuantidade(+1);
        Toast.makeText(getApplicationContext(),"Moeda adicionada com sucesso",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(new RecyclerAdapter(list));
    }

    private void atualizaQuantidade(int count) {

        totalMoedas =+ totalMoedas + (count);
        textViewTotal.setText(totalMoedas.toString());
    }

    private void atualizaRecycler() {
        recyclerView.setAdapter(new RecyclerAdapter(list));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CryptoEvent cryptoEvent) {
        list = cryptoEvent.getCryptoCurrencies();
        atualizaRecycler();
        totalMoedas = list.size();
        atualizaQuantidade(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CryptoEventButton cryptoEventButton) {
        atualizaQuantidade(cryptoEventButton.getNumero());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CryptoErrorEvent cryptoErrorEvent){
        Toast.makeText(getApplicationContext(),cryptoErrorEvent.getErrorMessage().toString(),Toast.LENGTH_LONG).show();
    }

}