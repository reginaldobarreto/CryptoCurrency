package br.com.barrsoft.cryptocurrency.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;
import br.com.barrsoft.cryptocurrency.R;
import br.com.barrsoft.cryptocurrency.events.CryptoErrorEvent;
import br.com.barrsoft.cryptocurrency.events.CryptoEvent;
import br.com.barrsoft.cryptocurrency.events.CryptoEventButton;
import br.com.barrsoft.cryptocurrency.models.CryptoCurrency;
import br.com.barrsoft.cryptocurrency.utils.ViewHolder;


public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    //atributos
    List<CryptoCurrency> list;

    public RecyclerAdapter(List<CryptoCurrency> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CryptoCurrency cryptoCurrency = list.get(position);
        holder.LongName.setText(cryptoCurrency.getLongName());
        holder.SortName.setText(cryptoCurrency.getShortName());
        holder.Price.setText(new DecimalFormat ("000.00000").format(cryptoCurrency.getPrice()) );

        holder.ButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Moeda " + list.get(position).getLongName() + " removida", Toast.LENGTH_SHORT).show();
                list.remove(position);
                EventBus.getDefault().post(new CryptoEventButton(-1));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
