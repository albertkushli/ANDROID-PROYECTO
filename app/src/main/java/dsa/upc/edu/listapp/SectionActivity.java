package dsa.upc.edu.listapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.CategoriaObjeto;
import dsa.upc.edu.listapp.models.Objeto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProductAdapter adapter;
    private SwipeRefreshLayout swipe;
    private ApiService api;
    private CategoriaObjeto categoria;
    private String idPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        api = ApiClient.getClient(SectionActivity.this).create(ApiService.class);
        FloatingActionButton fabOpenMenu = findViewById(R.id.fabOpenMenu);
        fabOpenMenu.setOnClickListener(v -> {
            NavigationBottomSheet.showNavigationMenu(this, idPartida);
        });
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Recupera sección e ID de partida
        categoria = (CategoriaObjeto) getIntent().getSerializableExtra("categoria");
        idPartida  = getIntent().getStringExtra("idPartida");
        if (categoria == null || idPartida == null) {
            Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setTitle(categoria.getNombre());

        // Botón Carrito
        ImageButton btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            Intent i = new Intent(SectionActivity.this, CarritoActivity.class);
            i.putExtra("idPartida", idPartida);
            startActivity(i);
        });

        // RecyclerView + SwipeRefresh
        rv    = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        swipe = findViewById(R.id.swipeRefreshProducts);

        // Adapter “Comprar”
        adapter = new ProductAdapter();
        adapter.setOnBuyClickListener(producto -> {
            String idObjeto = producto.getId_objeto();
            if (idObjeto == null || idObjeto.isEmpty()) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            } else {
                api.agregarObjetoACarrito(idPartida ,idObjeto)
                        .enqueue(new Callback<Void>() {
                            @Override public void onResponse(Call<Void> c, Response<Void> r) {
                                Toast.makeText(SectionActivity.this,
                                        r.isSuccessful() ? "Añadido" : "Fallo al añadir",
                                        Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onFailure(Call<Void> c, Throwable t) {
                                Toast.makeText(SectionActivity.this,
                                        "Error red", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        rv.setAdapter(adapter);

        swipe.setOnRefreshListener(this::loadProducts);
        loadProducts();
    }

    private void loadProducts() {
        swipe.setRefreshing(true);
        api.getProductosPorCategoria(categoria.getId_categoria()).enqueue(new Callback<List<Objeto>>() {
            @Override public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> r) {
                swipe.setRefreshing(false);
                if (r.isSuccessful() && r.body() != null) {
                    adapter.setData(r.body());
                } else {
                    Toast.makeText(SectionActivity.this, "Error cargando", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<List<Objeto>> c, Throwable t) {
                swipe.setRefreshing(false);
                Toast.makeText(SectionActivity.this, "Error red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

