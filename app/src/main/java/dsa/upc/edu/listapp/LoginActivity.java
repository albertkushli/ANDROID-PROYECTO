package dsa.upc.edu.listapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dsa.upc.edu.listapp.api.*;

import dsa.upc.edu.listapp.models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView btnGoToRegister;
    private ApiService api;
    private CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername      = findViewById(R.id.etLoginUsername);
        etPassword      = findViewById(R.id.etLoginPassword);
        btnLogin        = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        cbRememberMe    = findViewById(R.id.cbRememberMe); // NUEVO

        api = ApiClient.getClient(LoginActivity.this).create(ApiService.class);

        CheckBox checkboxMostrarPassword = findViewById(R.id.checkboxMostrarPassword);

        checkboxMostrarPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Para mantener el cursor al final del texto
            etPassword.setSelection(etPassword.length());
        });
        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor completa ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario login = new Usuario(null, user, pass);
            api.loginUser(login).enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        // Guardar token
                        editor.putString("token", response.body().getToken());

                        // CAMBIO 1: Guardar el username
                        editor.putString("username", user);

                        // --- AQUI GUARDAMOS EL NOMBRE DE USUARIO SI EL CHECKBOX ESTÁ MARCADO ---
                        if (cbRememberMe.isChecked()) {
                            editor.putString("usuarioRecordado", user);
                        } else {
                            editor.remove("usuarioRecordado"); // Si no, borramos cualquier recordatorio anterior
                        }

                        editor.apply();

                        // CAMBIO 2: Obtener el ID del usuario antes de continuar
                        obtenerDatosUsuario(user);

                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    // CAMBIO 3: Nuevo método para obtener el ID del usuario
    private void obtenerDatosUsuario(String username) {
        api.getUsuarioPorNombre(username).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();

                    // Guardar el ID del usuario Y EL USERNAME
                    SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                    prefs.edit()
                            .putString("userId", usuario.getId_usuario())
                            .putString("username", usuario.getNombre())  // <-- ESTA LÍNEA FALTABA
                            .apply();

                    // Ahora sí, ir a la siguiente actividad
                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, PartidasMenuActivity.class);
                    intent.putExtra("nombre", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Aunque falle, permitir continuar pero sin ID
                Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, PartidasMenuActivity.class);
                intent.putExtra("nombre", username);
                startActivity(intent);
                finish();
            }
        });
    }
}