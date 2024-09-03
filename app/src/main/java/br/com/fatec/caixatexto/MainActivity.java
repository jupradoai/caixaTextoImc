package br.com.fatec.caixatexto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private Double numImc; // Variável para armazenar o valor do IMC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void calcular(View view) {
        TextInputEditText campoNome = findViewById(R.id.textInputEditNome);
        TextView resultado = findViewById(R.id.tituloResultado);
        TextInputEditText campoPeso = findViewById(R.id.textInputEditPeso);
        TextInputEditText campoAltura = findViewById(R.id.textInputEditAltura);

        // Recupera e valida as entradas
        String nome = campoNome.getText().toString();
        String pesoStr = campoPeso.getText().toString();
        String alturaStr = campoAltura.getText().toString();

        if (nome.isEmpty() || pesoStr.isEmpty() || alturaStr.isEmpty()) {
            resultado.setText("Erro: Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            Double numPeso = Double.parseDouble(pesoStr);
            Double numAltura = Double.parseDouble(alturaStr);

            if (numPeso <= 0 || numAltura <= 0) {
                resultado.setText("Erro: Peso e altura devem ser maiores que zero.");
                return;
            }

            numImc = numPeso / (numAltura * numAltura);

            // Exibe o IMC calculado
            resultado.setText(String.format("IMC: %.2f", numImc));

            // Atualiza a classificação
            classificaIMC(view);

        } catch (NumberFormatException e) {
            resultado.setText("Erro: Dados inválidos.");
        }
    }

    public void limpar(View view) {
        TextView resultado = findViewById(R.id.tituloResultado);
        resultado.setText("");

        TextInputEditText campoNome = findViewById(R.id.textInputEditNome);
        TextInputEditText campoPeso = findViewById(R.id.textInputEditPeso);
        TextInputEditText campoAltura = findViewById(R.id.textInputEditAltura);

        campoNome.setText("");
        campoPeso.setText("");
        campoAltura.setText("");
    }

    public void classificaIMC(View view) {
        TextView resultado1 = findViewById(R.id.textResultado1);
        TextView resultado2 = findViewById(R.id.textResultado2);

        if (numImc != null) {
            String classificacao;

            if (numImc < 18.5) {
                classificacao = "Baixo Peso";
            } else if (numImc >= 18.5 && numImc < 25) {
                classificacao = "Peso Normal";
            } else if (numImc >= 25 && numImc < 30) {
                classificacao = "Sobrepeso";
            } else if (numImc >= 30 && numImc < 35) {
                classificacao = "Obesidade Grau 1";
            } else if (numImc >= 35 && numImc < 40) {
                classificacao = "Obesidade Grau 2";
            } else {
                classificacao = "Obesidade Extrema";
            }

            resultado1.setText("Classificação: " + classificacao);
            resultado2.setText(String.format("IMC: %.2f", numImc));
        } else {
            resultado1.setText("Erro: Calcule o IMC primeiro.");
            resultado2.setText("Entrada inválida.");
        }
    }
}
