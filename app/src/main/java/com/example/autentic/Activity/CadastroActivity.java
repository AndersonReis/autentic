package com.example.autentic.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.autentic.Classes.Usuario;
import com.example.autentic.DAO.configuracaoFirebase;
import com.example.autentic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha1;
    private EditText senha2;
    private EditText nome;

    private RadioButton Rbadmin;
    private RadioButton Rbatend;

    private Button btncadastrar;
    private Button btncancelar;

    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email = ( EditText ) findViewById(R.id.cademail);
        senha1 = ( EditText ) findViewById(R.id.cadsenha1);
        senha2 = ( EditText ) findViewById(R.id.cadsenha2);
        nome = ( EditText ) findViewById(R.id.edtnome);
        Rbadmin = ( RadioButton ) findViewById(R.id.radioAdm);
        Rbatend = ( RadioButton ) findViewById(R.id.radioAtendente);
        btncadastrar = ( Button ) findViewById(R.id.btncadastrar);
        btncancelar = ( Button ) findViewById(R.id.btncancelar);

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (senha1.getText().toString().equals(senha2.getText().toString())) {
                    usuario = new Usuario();

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    usuario.setNome(nome.getText().toString());

                    if (Rbadmin.isChecked()) {
                        usuario.setTipoUsuario("Administrador");
                    } else if (Rbatend.isChecked()) {
                        usuario.setTipoUsuario("Atendente");
                    }

                    cadastrarUsuario();

                }else{

            }

                Toast.makeText(CadastroActivity.this, "As senhas não se correspondem!", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void cadastrarUsuario() {

        autenticacao = configuracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(

                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    insereUsuario(usuario);

                } else {

                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é invalido, digite outro e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já esta cadastrado!";
                    } catch (Exception e) {
                        erroExcecao = "erro ao efetuar o cadastro! ";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private boolean insereUsuario(Usuario usuario) {

        try {

            reference = configuracaoFirebase.getFirebase().child("Usuarios");
            reference.push().setValue(usuario);
            Toast.makeText(CadastroActivity.this, "Usuario cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            return true;


        } catch (Exception e) {
            Toast.makeText(CadastroActivity.this, "Erro ao gravar o Usuario!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }


    }

}
