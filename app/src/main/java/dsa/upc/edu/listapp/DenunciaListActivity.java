package dsa.upc.edu.listapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Denuncia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DenunciaListActivity extends AppCompatActivity {
    private static final String TAG = "DenunciaListActivity";
    private RecyclerView recyclerView;
    private DenunciaAdapter adapter;
    private ProgressBar progressBar;
    private Button btnVolver;
    private TextView tvNoData;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia_list);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewDenuncias);
        progressBar = findViewById(R.id.progressBarDenuncias);
        btnVolver = findViewById(R.id.btnVolver);

        // Si tienes un TextView para mostrar cuando no hay datos
        // tvNoData = findViewById(R.id.tvNoData);

        // Configurar botón volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DenunciaAdapter();
        recyclerView.setAdapter(adapter);

        // Inicializar API
        api = ApiClient.getClient(this).create(ApiService.class);

        // Cargar denuncias
        cargarDenuncias();
    }

    private void cargarDenuncias() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        // Cargar todas las denuncias
        api.getDenuncias().enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Denuncia> denuncias = response.body();
                    Log.d(TAG, "Denuncias recibidas: " + denuncias.size());

                    if (denuncias.isEmpty()) {
                        // Mostrar mensaje de que no hay denuncias
                        Toast.makeText(DenunciaListActivity.this,
                                "No hay denuncias registradas", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setDenuncias(denuncias);
                    }
                } else {
                    Log.e(TAG, "Error al cargar denuncias. Código: " + response.code());
                    // Si falla el primer intento, intentar con las denuncias del usuario
                    cargarMisDenuncias();
                }
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                Log.e(TAG, "Error de conexión: " + t.getMessage(), t);
                Toast.makeText(DenunciaListActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();

                // Mostrar lista vacía
                adapter.setDenuncias(new ArrayList<>());
            }
        });
    }

    private void cargarMisDenuncias() {
        api.getMisDenuncias().enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Denuncia> denuncias = response.body();
                    Log.d(TAG, "Mis denuncias recibidas: " + denuncias.size());
                    adapter.setDenuncias(denuncias);
                } else {
                    Log.e(TAG, "Error al cargar mis denuncias. Código: " + response.code());
                    Toast.makeText(DenunciaListActivity.this,
                            "No se pudieron cargar las denuncias", Toast.LENGTH_SHORT).show();
                    adapter.setDenuncias(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                Log.e(TAG, "Error al cargar mis denuncias: " + t.getMessage(), t);
                Toast.makeText(DenunciaListActivity.this,
                        "Error de conexión", Toast.LENGTH_SHORT).show();
                adapter.setDenuncias(new ArrayList<>());
            }
        });
    }
}