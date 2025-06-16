package dsa.upc.edu.listapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Faq;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FaqAdapter adapter;
    private ProgressBar progressBar;
    private Button btnBack;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewFaqs);
        progressBar = findViewById(R.id.progressBarFaqs);
        btnBack = findViewById(R.id.btnBack);

        // Configurar botón volver
        btnBack.setOnClickListener(v -> finish());

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FaqAdapter();
        recyclerView.setAdapter(adapter);

        // Inicializar API
        api = ApiClient.getClient(this).create(ApiService.class);

        // Cargar FAQs
        cargarFaqs();
    }

    private void cargarFaqs() {
        progressBar.setVisibility(View.VISIBLE);

        api.getFaqs().enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    adapter.setFaqs(response.body());
                } else {
                    Toast.makeText(FaqActivity.this,
                            "Error al cargar preguntas frecuentes",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FaqActivity.this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}