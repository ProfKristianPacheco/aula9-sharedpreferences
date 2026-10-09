package com.example.aula9_exer7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText nomeUsuario;
    private EditText distanciaPercorrida;
    private Button registrarDistancia;
    private TextView resultado;
    private float distanciaTotal;
    private String nome;
    private SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        nomeUsuario = findViewById(R.id.editTextNome);
        distanciaPercorrida = findViewById(R.id.editTextDistancia);
        registrarDistancia = findViewById(R.id.buttonRegistrar);
        resultado = findViewById(R.id.textViewResultado);
        preferencias = getSharedPreferences("appCorrida", MODE_PRIVATE);
        nome = preferencias.getString("chaveNome", "");
        distanciaTotal = preferencias.getFloat("chaveDistancia", 0);
        resultado.setText("Prezado (a) " + nome + ", você percorreu no total: " + distanciaTotal + " Kms!");
        nomeUsuario.setText(nome);

        registrarDistancia.setOnClickListener(new View.OnClickListener() { //registrando a distância
            @Override
            public void onClick(View view) {
                registrarAtividade();
            }
        });
        //TODO - CRIAR UM NOVO BUTTON PARA LIMPAR O PROGRESSO DO USUÁRIO
        //COMO FAZER ISSO? USANDO O MÉTODO clear DA CLASSE SHARED PREFERENCES

        //registrarDistancia.setOnClickListener(view -> {registrarAtividade();}); // exemplo com lambda
    }

    private void registrarAtividade() {
        nome = nomeUsuario.getText().toString();
        String distancia = distanciaPercorrida.getText().toString();

        if(nome.isBlank() || distancia.isBlank()){
            Toast.makeText(this, "Preencha nome e distância!", Toast.LENGTH_SHORT).show();
            return;
        }
        float distanciaParcial = Float.parseFloat(distancia);
        distanciaTotal += distanciaParcial;
        resultado.setText("Prezado (a) " + nome + ", você percorreu no total: " + distanciaTotal + " Kms");
        distanciaPercorrida.setText("");
        SharedPreferences.Editor meuEditor = preferencias.edit();
        meuEditor.putString("chaveNome", nome);
        meuEditor.putFloat("chaveDistancia", distanciaTotal);
        //meuEditor.clear();
        meuEditor.apply();
    }
}