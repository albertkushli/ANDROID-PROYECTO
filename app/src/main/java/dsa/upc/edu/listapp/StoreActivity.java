package dsa.upc.edu.listapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.CategoriaObjeto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {
    private String idPartida;
    private SwipeRefreshLayout swipe;
    private SectionAdapter adapter;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // IMPORTANTE: Asegúrate de que este sea el layout correcto
        // Si el XML que mostraste se llama activity_store.xml, cámbialo aquí
        setContentView(R.layout.activity_main); // o R.layout.activity_store si ese es el nombre

        // Inicializar API primero
        api = ApiClient.getClient(StoreActivity.this).create(ApiService.class);

        // Obtener idPartida
        idPartida = getIntent().getStringExtra("idPartida");

        // Configurar FAB
        FloatingActionButton fabOpenMenu = findViewById(R.id.fabOpenMenu);
        fabOpenMenu.setOnClickListener(v -> {
            NavigationBottomSheet.showNavigationMenu(this, idPartida);
        });

        // Botón volver
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> finish());

        // Botón Random
        findViewById(R.id.btnRandom).setOnClickListener(view -> {
            Intent intent = new Intent(StoreActivity.this, RandomActivity.class);
            intent.putExtra("idPartida", idPartida);
            startActivity(intent);
        });

        // Configurar RecyclerView
        RecyclerView rv = findViewById(R.id.rvSections);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Configurar adapter con listener para clicks
        adapter = new SectionAdapter(categoria -> {
            // Aquí es donde navegamos a SectionActivity
            Intent i = new Intent(StoreActivity.this, SectionActivity.class);
            i.putExtra("categoria", categoria);
            i.putExtra("idPartida", idPartida);
            startActivity(i);
        });
        rv.setAdapter(adapter);

        // Configurar SwipeRefresh
        swipe = findViewById(R.id.swipeRefreshLayout);
        swipe.setOnRefreshListener(this::loadSections);

        // Cargar secciones
        loadSections();
    }

    private void loadSections() {
        swipe.setRefreshing(true);

        api.getAllCategorias().enqueue(new Callback<List<CategoriaObjeto>>() {
            @Override
            public void onResponse(Call<List<CategoriaObjeto>> call, Response<List<CategoriaObjeto>> resp) {
                swipe.setRefreshing(false);
                if (resp.isSuccessful() && resp.body() != null) {
                    adapter.setDataFromStrings(resp.body());
                } else {
                    Toast.makeText(StoreActivity.this, "Error al cargar secciones", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CategoriaObjeto>> call, Throwable t) {
                swipe.setRefreshing(false);
                Toast.makeText(StoreActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}