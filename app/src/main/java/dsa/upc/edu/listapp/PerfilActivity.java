package dsa.upc.edu.listapp;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Insignia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {
    private static final String TAG = "PerfilActivity";

    private TextView tvUsername;
    private TextView tvNoBadges;
    private ImageView ivUserAvatar;
    private RecyclerView rvBadges;
    private Button btnBack;
    private FrameLayout avatarContainer;

    private ApiService api;
    private BadgeAdapter badgeAdapter;
    private String userId;

    // Array de avatares disponibles
    private final int[] avatares = {
            R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6
    };
    private int avatarActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar API
        api = ApiClient.getClient(this).create(ApiService.class);

        // Obtener referencias a las vistas
        tvUsername = findViewById(R.id.tvUsername);
        tvNoBadges = findViewById(R.id.tvNoBadges);
        ivUserAvatar = findViewById(R.id.ivUserAvatar);
        rvBadges = findViewById(R.id.rvBadges);
        btnBack = findViewById(R.id.btnBack);
        avatarContainer = findViewById(R.id.avatarContainer);

        // Configurar botón volver
        btnBack.setOnClickListener(v -> finish());

        // Configurar cambio de avatar
        avatarContainer.setOnClickListener(v -> cambiarAvatar());

        // Configurar RecyclerView para las insignias
        rvBadges.setLayoutManager(new GridLayoutManager(this, 3)); // 3 columnas
        badgeAdapter = new BadgeAdapter();
        rvBadges.setAdapter(badgeAdapter);

        // Cargar datos del usuario
        cargarDatosUsuario();

        // Cargar insignias
        cargarInsignias();
    }

    private void cargarDatosUsuario() {
        // Obtener datos del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String username = prefs.getString("username", "Usuario");
        userId = prefs.getString("userId", "");
        int avatarIndex = prefs.getInt("avatarIndex", 0);

        // Mostrar datos
        tvUsername.setText(username.toUpperCase());
        avatarActual = avatarIndex;
        ivUserAvatar.setImageResource(avatares[avatarActual]);

        Log.d(TAG, "Usuario cargado - Username: " + username + ", ID: " + userId);
    }

    private void cambiarAvatar() {
        // Crear diálogo con el layout personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_avatar_selection, null);
        builder.setView(dialogView);

        // Referencias a los avatares en el diálogo
        FrameLayout avatar1 = dialogView.findViewById(R.id.avatar1);
        FrameLayout avatar2 = dialogView.findViewById(R.id.avatar2);
        FrameLayout avatar3 = dialogView.findViewById(R.id.avatar3);
        FrameLayout avatar4 = dialogView.findViewById(R.id.avatar4);
        FrameLayout avatar5 = dialogView.findViewById(R.id.avatar5);
        FrameLayout avatar6 = dialogView.findViewById(R.id.avatar6);

        AlertDialog dialog = builder.create();

        // Click listeners para cada avatar
        avatar1.setOnClickListener(v -> {
            seleccionarAvatar(0);
            dialog.dismiss();
        });

        avatar2.setOnClickListener(v -> {
            seleccionarAvatar(1);
            dialog.dismiss();
        });

        avatar3.setOnClickListener(v -> {
            seleccionarAvatar(2);
            dialog.dismiss();
        });

        avatar4.setOnClickListener(v -> {
            seleccionarAvatar(3);
            dialog.dismiss();
        });

        avatar5.setOnClickListener(v -> {
            seleccionarAvatar(4);
            dialog.dismiss();
        });

        avatar6.setOnClickListener(v -> {
            seleccionarAvatar(5);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void seleccionarAvatar(int index) {
        avatarActual = index;
        ivUserAvatar.setImageResource(avatares[avatarActual]);

        // Guardar la selección
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        prefs.edit().putInt("avatarIndex", avatarActual).apply();

        Toast.makeText(this, "Avatar actualizado", Toast.LENGTH_SHORT).show();
    }

    private void cargarInsignias() {
        Log.d(TAG, "Cargando insignias del usuario...");

        // Primero intentamos con el endpoint que usa el token
        api.getMisInsignias().enqueue(new Callback<List<Insignia>>() {
            @Override
            public void onResponse(Call<List<Insignia>> call, Response<List<Insignia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Insignia> insignias = response.body();
                    Log.d(TAG, "Insignias recibidas: " + insignias.size());

                    // Log para debug
                    for (Insignia insignia : insignias) {
                        Log.d(TAG, "Insignia: " + insignia.getNombre() +
                                ", Avatar: " + insignia.getAvatar());
                    }

                    mostrarInsignias(insignias);
                } else {
                    // Si falla con token, intentar con ID
                    Log.e(TAG, "Error al cargar insignias con token: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cargarInsigniasConId();
                }
            }

            @Override
            public void onFailure(Call<List<Insignia>> call, Throwable t) {
                Log.e(TAG, "Error de red al cargar insignias", t);
                cargarInsigniasConId();
            }
        });
    }

    private void cargarInsigniasConId() {
        if (userId.isEmpty()) {
            Log.e(TAG, "ID de usuario no disponible");
            mostrarErrorInsignias();
            return;
        }

        Log.d(TAG, "Intentando cargar insignias con ID: " + userId);

        api.getInsigniasDelUsuario(userId).enqueue(new Callback<List<Insignia>>() {
            @Override
            public void onResponse(Call<List<Insignia>> call, Response<List<Insignia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Insignia> insignias = response.body();
                    Log.d(TAG, "Insignias recibidas (con ID): " + insignias.size());
                    mostrarInsignias(insignias);
                } else {
                    Log.e(TAG, "Error al cargar insignias con ID: " + response.code());
                    mostrarErrorInsignias();
                }
            }

            @Override
            public void onFailure(Call<List<Insignia>> call, Throwable t) {
                Log.e(TAG, "Error de red al cargar insignias con ID", t);
                mostrarErrorInsignias();
            }
        });
    }

    private void mostrarInsignias(List<Insignia> insignias) {
        if (insignias.isEmpty()) {
            // No hay insignias
            tvNoBadges.setVisibility(View.VISIBLE);
            rvBadges.setVisibility(View.GONE);
        } else {
            // Mostrar insignias
            tvNoBadges.setVisibility(View.GONE);
            rvBadges.setVisibility(View.VISIBLE);
            badgeAdapter.setInsignias(insignias);
        }
    }

    private void mostrarErrorInsignias() {
        tvNoBadges.setText("ERROR AL CARGAR INSIGNIAS");
        tvNoBadges.setVisibility(View.VISIBLE);
        rvBadges.setVisibility(View.GONE);
    }
}