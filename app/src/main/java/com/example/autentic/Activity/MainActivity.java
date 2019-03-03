package com.example.autentic.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autentic.Classes.Usuario;
import com.example.autentic.DAO.configuracaoFirebase;
import com.example.autentic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private Button   btnLogin;
    private Button   btnCancelar;
    private Button   btncadastrese;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = (EditText) findViewById(R.id.edtemail);
        edtSenhaLogin = (EditText) findViewById(R.id.edtsenha);
        btnLogin      = (Button)   findViewById(R.id.btncadastro);
        btnCancelar   = (Button)   findViewById(R.id.btncancelar);
        btncadastrese = (Button)   findViewById(R.id.btncadastrese);

        btncadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edtEmailLogin.getText().toString().equals("") && !edtSenhaLogin.getText().toString().equals("")){

                    usuario  = new Usuario();

                    usuario.setEmail(edtEmailLogin.getText().toString());
                    usuario.setSenha(edtSenhaLogin.getText().toString());

                    ValidarLogin();

                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos de E-mail e Senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void ValidarLogin(){

        autenticacao = configuracaoFirebase.getFirebaseAuth();

        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    abrirTelaAdministrador();

                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    edtEmailLogin.setText("");
                    edtSenhaLogin.setText("");


                }else {
                    Toast.makeText(MainActivity.this, "Usuario ou senha invalidos!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private  void abrirTelaAdministrador(){

        Intent intent = new Intent(MainActivity.this, principalActivity.class);
        startActivity(intent);
        finish();


    }

}
