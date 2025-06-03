package dsa.upc.edu.listapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Objeto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioActivity extends AppCompatActivity {

    private RecyclerView rv;
    private InventarioAdapter adapter;
    private ApiService api;
    private String idPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        idPartida = getIntent().getStringExtra("idPartida");
        if (idPartida == null) {
            Toast.makeText(this, "Partida no especificada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> finish());

        FloatingActionButton fabOpenMenu = findViewById(R.id.fabOpenMenu);
        fabOpenMenu.setOnClickListener(v -> {
            NavigationBottomSheet.showNavigationMenu(this, idPartida);
        });

        rv = findViewById(R.id.rvInventario);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventarioAdapter(false);
        rv.setAdapter(adapter);

        api = ApiClient.getClient(InventarioActivity.this).create(ApiService.class);
        cargarInventario();
    }


    private void cargarInventario() {
        api.getInventario(idPartida)
                .enqueue(new Callback<List<Objeto>>() {
                    @Override
                    public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> resp) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            List<Objeto> inv = resp.body();
                            adapter.setData(inv);
                        } else {
                            Toast.makeText(InventarioActivity.this,
                                    "Error cargando inventario", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Objeto>> call, Throwable t) {
                        Toast.makeText(InventarioActivity.this,
                                "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
