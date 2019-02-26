package com.example.autentic.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.autentic.R;
import com.google.firebase.auth.FirebaseAuth;

public class principalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        autenticacao = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
