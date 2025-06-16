package dsa.upc.edu.listapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import dsa.upc.edu.listapp.models.Partida;

public class PartidaActivity extends AppCompatActivity {

    private String idPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        // Obtener idPartida del intent
        idPartida = getIntent().getStringExtra("idPartida");

        // Referencias a las vistas del layout
        TextView tvUsuario = findViewById(R.id.tvUsuario);
        TextView tvVidas = findViewById(R.id.tvVidas);
        TextView tvMonedas = findViewById(R.id.tvMonedas);
        TextView tvPuntuacion = findViewById(R.id.tvPuntuacion);

        // Botones (son LinearLayout en tu XML, no ImageButton)
        LinearLayout btnTienda = findViewById(R.id.btnTienda);
        LinearLayout btnInventario = findViewById(R.id.btnInventario);

        // Botón volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(view -> finish());

        // Botón FAQ
        View btnFaq = findViewById(R.id.btnFaq);
        btnFaq.setOnClickListener(v -> {
            Intent intent = new Intent(PartidaActivity.this, FaqActivity.class);
            startActivity(intent);
        });

        // Recuperar el objeto Partida
        Partida partida = (Partida) getIntent().getSerializableExtra("partida");

        if (partida != null) {
            // Actualizar los TextViews con la información de la partida
            tvUsuario.setText("HEROE: " + partida.getId_usuario());
            tvVidas.setText(String.valueOf(partida.getVidas()));
            tvMonedas.setText(String.valueOf(partida.getMonedas()));
            tvPuntuacion.setText(String.valueOf(partida.getPuntuacion()));

            String idPartida = partida.getId_partida();

            // Configurar click listener para Tienda
            btnTienda.setOnClickListener(v -> {
                Intent intent = new Intent(PartidaActivity.this, StoreActivity.class);
                intent.putExtra("idPartida", idPartida);
                startActivity(intent);
            });

            // Configurar click listener para Inventario
            btnInventario.setOnClickListener(v -> {
                Intent intent = new Intent(PartidaActivity.this, InventarioActivity.class);
                intent.putExtra("idPartida", idPartida);
                startActivity(intent);
            });

        } else {
            // Si no hay partida, mostrar valores por defecto
            tvUsuario.setText("HEROE: Sin usuario");
            tvVidas.setText("0");
            tvMonedas.setText("0");
            tvPuntuacion.setText("0");

            // Deshabilitar botones
            btnTienda.setEnabled(false);
            btnInventario.setEnabled(false);
            btnTienda.setAlpha(0.5f);
            btnInventario.setAlpha(0.5f);
        }
    }
}