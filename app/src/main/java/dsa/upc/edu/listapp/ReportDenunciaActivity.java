package dsa.upc.edu.listapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Denuncia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDenunciaActivity extends AppCompatActivity {
    private EditText etFecha, etTitulo, etMensaje, etRemitente;
    private Button btnEnviar, btnVolver;
    private ApiService api;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_denuncia);

        // Guardar contexto
        context = this;

        // Inicializar vistas
        etFecha = findViewById(R.id.etFecha);
        etTitulo = findViewById(R.id.etTitulo);
        etMensaje = findViewById(R.id.etMensaje);
        etRemitente = findViewById(R.id.etRemitente);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializar API
        api = ApiClient.getClient(this).create(ApiService.class);

        // Establecer fecha actual
        SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        etFecha.setText(sdfDisplay.format(new Date()));
        etFecha.setEnabled(false);

        // Configurar botón volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Configurar botón enviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDenuncia();
            }
        });
    }

    private void enviarDenuncia() {
        String titulo = etTitulo.getText().toString().trim();
        String mensaje = etMensaje.getText().toString().trim();

        // Validaciones
        if (titulo.isEmpty()) {
            etTitulo.setError("El título es obligatorio");
            etTitulo.requestFocus();
            return;
        }
        if (mensaje.isEmpty()) {
            etMensaje.setError("El mensaje es obligatorio");
            etMensaje.requestFocus();
            return;
        }

        // Deshabilitar botón mientras se envía
        btnEnviar.setEnabled(false);
        btnEnviar.setText("ENVIANDO...");

        // Crear objeto denuncia
        Denuncia denuncia = new Denuncia(titulo, mensaje);

        // Enviar al backend
        api.reportDenuncia(denuncia).enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                btnEnviar.setEnabled(true);
                btnEnviar.setText("ENVIAR");

                if (response.isSuccessful()) {
                    Toast.makeText(context,
                            "Denuncia enviada correctamente",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String errorMsg = "Error al enviar la denuncia";
                    if (response.code() == 409) {
                        errorMsg = "Ya existe una denuncia con ese ID";
                    } else if (response.code() == 401) {
                        errorMsg = "No autorizado. Por favor, inicia sesión";
                    }
                    Toast.makeText(context,
                            errorMsg + " (Código: " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                btnEnviar.setEnabled(true);
                btnEnviar.setText("ENVIAR");
                Toast.makeText(context,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}