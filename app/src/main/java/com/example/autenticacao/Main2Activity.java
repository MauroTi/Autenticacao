package com.example.autenticacao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    // criação do adapter e da lista
    ListAdapterItem adapter;
    ArrayList<Item> listaItens;

    // fim criação adapter e lista

    int testeAdd;
    String valor = String.valueOf(0);

    // controles da tela
    EditText txtDespesa;
    EditText txtValor;
    Button btnAdicionar;
    ListView minhaLista;
    MonetaryMask monetaryMask;
    // fim dos controles da tela

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Context context = this;

        listaItens = new ArrayList<Item>();
        adapter = new ListAdapterItem(context, listaItens);

        minhaLista = findViewById(R.id.minhaLista);
        minhaLista.setAdapter(adapter);
        txtDespesa = findViewById(R.id.despesa);
        txtValor = findViewById(R.id.valorDespesa);
        monetaryMask = new MonetaryMask(this.txtValor);
        btnAdicionar = findViewById(R.id.btnAdicionarDespesa);

        Locale mLocale = new Locale("pt", "BR");
        txtValor.addTextChangedListener(new MonetaryMask(txtValor, mLocale));

        btnAdicionar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //  txtValor.setText("0");
                        String despesa = txtDespesa.getText().toString();
                        valor = txtValor.getText().toString();
                        Item novoItem = new Item(despesa, valor);
                        adapter.add(novoItem);
                        adapter.notifyDataSetChanged();
                        txtDespesa.setText("");
                        txtValor.setText("0");

                        // Contador dos itens adicionados
                        TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
                        int itensDespesa = adapter.getCount();
                        testeAdd = itensDespesa;
                        itensAddDespesa.setText(String.valueOf(itensDespesa));
                    }
                });
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
        TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
        int itensDespesa = adapter.getCount();
        testeAdd = itensDespesa;
        itensAddDespesa.setText(String.valueOf(itensDespesa));
    }

    public void proxima(View view) {

        Float pegaValor;
        Float despesas = Float.valueOf(0);

        if ((testeAdd > 0) && (!valor.equals(""))) {
            try {

                for (Iterator<Item> iterator = listaItens.iterator(); iterator.hasNext(); ) {
                    Item item = iterator.next(); // pega o item da lista

                    pegaValor = Float.parseFloat(item.getValor().replaceAll("\\D", ""));
                    despesas = (despesas) + (pegaValor / 100);
                }
                Intent i = new Intent(Main2Activity.this, Main4Activity.class);
                i.putExtra("TotalDespesas", despesas);
                startActivity(i);

                //listaItens = new ArrayList<>(listaItens);
             /* Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
              intent.putExtra("lista", listaItens);
              startActivity(intent);*/


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Nenhum valor!!!")
                    .setMessage("Nenhum valor válido adicionado!!!")
                    .setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                    .show();
        }
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Sair do App?")
                .setMessage("Tem certeza que deseja sair do aplicativo?")
                .setPositiveButton(
                        "sim",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                finish();
                            }
                        })
                .setNegativeButton("não", null)
                .show();
    }
}
