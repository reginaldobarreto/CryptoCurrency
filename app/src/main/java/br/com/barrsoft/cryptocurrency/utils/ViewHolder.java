package br.com.barrsoft.cryptocurrency.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.barrsoft.cryptocurrency.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder{

    public @BindView(R.id.textViewLongName) TextView LongName;
    public @BindView(R.id.textViewSortName) TextView SortName;
    public @BindView(R.id.textViewPrice) TextView Price;
    public @BindView(R.id.buttonRemove) Button ButtonRemove;

    public ViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
