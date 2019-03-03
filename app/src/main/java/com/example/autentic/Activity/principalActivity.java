package com.example.autentic.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.autentic.Classes.Usuario;
import com.example.autentic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class principalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference referenceFirebase;
    private TextView tipoUsuario;
    private Usuario usuario;
    private String tipoUsuarioEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tipoUsuario = (TextView )  findViewById(R.id.txttipoUsuario);

        autenticacao = FirebaseAuth.getInstance();

        //recebendo email do usuario logado no momento
        String email = autenticacao.getCurrentUser().toString();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //recebendo email do usuario logado no momento
        String email = autenticacao.getCurrentUser().toString();

        referenceFirebase = FirebaseDatabase.getInstance().getReference();

        referenceFirebase.child("usuarios").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postDataSnapshot: dataSnapshot.getChildren()){

                    tipoUsuarioEmail = postDataSnapshot.child("tipoUsuario").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_add_usuario){

            abritelaCadastroUsuario();

        }else if(id == R.id.action_sair_admin){

            deslogarUsuario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void abritelaCadastroUsuario(){
        Intent intent = new Intent(principalActivity.this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }

    private void deslogarUsuario(){

        autenticacao.signOut();

        Intent intent = new Intent(principalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
