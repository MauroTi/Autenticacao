package com.example.autenticacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // Variáveis SharedPreferences

    private EditText ed1;
    // CHAVE DO NOME DE USUÁRIO EM SHARED PREFERENCES
    private static final String NOME_USUARIO = "nome-usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Login loginTeste = new Login("mauro", "oi");
        //loginController.cadastroLogin(loginTeste);
        // SharedPreferences

        ed1 = findViewById(R.id.etNome);
        String msgNome = com.example.orcamentodomestico.Utils.carregarDadosComponente(NOME_USUARIO, this);
        ed1.setText(msgNome);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Toast.makeText(MainActivity.this, "Usuário já conectado.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Usuário não conectado.", Toast.LENGTH_SHORT).show();
        }
    }


    public void gravar(View view) {
        com.example.orcamentodomestico.Utils.salvarDadosComponente(NOME_USUARIO, ed1.getText().toString(), view.getContext());
    }

    // Fim SharedPreferences

    Login login;
    LoginController loginController = new LoginController();

    String usuario;
    String senha;

    public void cadastrar(View view) {

        EditText etNome = findViewById(R.id.etNome);
        usuario = etNome.getText().toString();
        EditText etSenha = findViewById(R.id.etSenha);
        senha = etSenha.getText().toString();

        login = new Login(usuario, senha);
        login.setUsuario(usuario);
        login.setSenha(senha);
        if (!(etNome.getText().toString().equals("")) && !(etSenha.getText().toString().equals(""))) {

            List<Login> lista = loginController.reLogin();
            int contatoExisteLogin = 0;

            for (int i = 0; i < loginController.reLogin().size(); i++) {
                if (usuario.equals(lista.get(i).getUsuario()) && senha.equals(lista.get(i).getSenha())) {
                    contatoExisteLogin = 1;
                }
            }
            if (contatoExisteLogin != 1) {
                loginController.cadastroLogin(login);
                Toast.makeText(MainActivity.this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Usuário já existente!!", Toast.LENGTH_SHORT).show();
            }
        }

        gravar(view);

        mAuth.createUserWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void logar(View view) {

        // Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        // startActivity(intent);
        // finish();


        EditText etNome = findViewById(R.id.etNome);
        EditText etSenha = findViewById(R.id.etSenha);

        String nome = etNome.getText().toString();
        String codigo = etSenha.getText().toString();
        String teste = loginController.exibeLogin();
        String valida = (nome + codigo);


        if ((etNome.getText().toString().equals("")) && (etSenha.getText().toString().equals(""))) {
            Toast.makeText(MainActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
                    .show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "O campo nome deve estar preenchido.", Toast.LENGTH_LONG)
                    .show();
        } else if (etSenha.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
                    .show();
        } else if (!(etNome.getText().toString().equals(""))
                && !(etSenha.getText().toString().equals(""))) {

            List<Login> lista = loginController.reLogin();
            int contatoExiste = 0;

            for (int i = 0; i < loginController.reLogin().size(); i++) {
                if (nome.equals(lista.get(i).getUsuario()) && codigo.equals(lista.get(i).getSenha())) {
                    contatoExiste = 1;
                }
            }
            if (contatoExiste == 1) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            } else {

                new AlertDialog.Builder(this)
                        .setTitle("Dados não conferem.")
                        .setMessage("Nome ou senha não encontrados, tente novamente.")
                        .setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                        .show();
                Toast.makeText(
                        MainActivity.this,
                        "Nome ou senha não encontrados, tente novamente.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }

        mAuth.signInWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
