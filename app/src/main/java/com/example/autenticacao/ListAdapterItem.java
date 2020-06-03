package com.example.autenticacao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapterItem extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> lista;

    public ListAdapterItem(Context context, ArrayList<Item> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public void add(Item object) {
        lista.add(object);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item itemPosicao = this.lista.get(position);
        final int pos = position;

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.item_lista, null, false);

        TextView etNome = convertView.findViewById(R.id.tvDespesa);
        TextView etValor = convertView.findViewById(R.id.tvValor);
        Button btnRemover = convertView.findViewById(R.id.btnRemoverItem);

        etNome.setText(itemPosicao.nome);
        etValor.setText(itemPosicao.valor + "");

        btnRemover.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Item item = lista.get(pos);
                        lista.remove(item);
                        try {
                            ((Main2Activity) context).updateAdapter();
                        } catch (Exception e) {

                        }
                        try {
                            ((Main4Activity) context).updateAdapter();
                        } catch (Exception e) {

                        }
                    }
                });

        return convertView;
    }
}
