package dsa.upc.edu.listapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Consulta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.UUID;

public class ConsultasActivity extends AppCompatActivity {

    private EditText etTitulo, etMensaje;
    private Button btnEnviarConsulta;
    private ImageButton btnBack;
    private ProgressBar progressBar;
    private ApiService api;
    private static final String CHANNEL_ID = "consultas_channel";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        // Inicializar vistas
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        etTitulo = findViewById(R.id.etTitulo);
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviarConsulta = findViewById(R.id.btnEnviarConsulta);
        progressBar = findViewById(R.id.progressBar);

        // Inicializar API
        api = ApiClient.getClient(ConsultasActivity.this).create(ApiService.class);

        // Crear canal de notificaciones
        createNotificationChannel();

        // Configurar botón enviar
        btnEnviarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarConsulta();
            }
        });
    }

    private void enviarConsulta() {
        String titulo = etTitulo.getText().toString().trim();
        String mensaje = etMensaje.getText().toString().trim();

        // Validar campos
        if (titulo.isEmpty()) {
            etTitulo.setError("El título es requerido");
            return;
        }

        if (mensaje.isEmpty()) {
            etMensaje.setError("El mensaje es requerido");
            return;
        }

        // Deshabilitar botón y mostrar progress
        btnEnviarConsulta.setEnabled(false);
        btnEnviarConsulta.setText("ENVIANDO...");
        progressBar.setVisibility(View.VISIBLE);

        // Crear objeto Consulta
        Consulta consulta = new Consulta(titulo, mensaje);

        // Hacer llamada a la API
        api.addConsulta(consulta).enqueue(new Callback<Consulta>() {
            @Override
            public void onResponse(Call<Consulta> call, Response<Consulta> response) {
                Toast.makeText(ConsultasActivity.this,"Response : "+response.code(), Toast.LENGTH_SHORT).show();
                btnEnviarConsulta.setEnabled(true);
                btnEnviarConsulta.setText("ENVIAR");
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    // Mostrar notificación
                    mostrarNotificacion();

                    // Limpiar campos
                    etTitulo.setText("");
                    etMensaje.setText("");

                    // Mostrar toast y cerrar actividad
                    Toast.makeText(ConsultasActivity.this, "Consulta enviada exitosamente", Toast.LENGTH_SHORT).show();

                    // Cerrar la actividad después de 2 segundos
                    etTitulo.postDelayed(() -> finish(), 2000);
                } else {
                    Toast.makeText(ConsultasActivity.this, "Error al enviar la consulta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Consulta> call, Throwable t) {
                btnEnviarConsulta.setEnabled(true);
                btnEnviarConsulta.setText("ENVIAR");
                progressBar.setVisibility(View.GONE);
                Log.e("CONSULTA_ERROR", "Error de conexión", t); // Esto imprimirá stacktrace completo
                Toast.makeText(ConsultasActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal de Consultas";
            String description = "Notificaciones sobre el estado de las consultas";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void mostrarNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Usa tu propio icono aquí
                .setContentTitle("Consulta Recibida")
                .setContentText("Tu consulta será atendida lo antes posible")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}