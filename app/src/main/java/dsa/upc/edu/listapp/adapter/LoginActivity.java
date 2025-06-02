//package dsa.upc.edu.listapp.adapter;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import dsa.upc.edu.listapp.R;
//import dsa.upc.edu.listapp.auth.*;
//
//import dsa.upc.edu.listapp.auth.Usuario;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private EditText etUsername, etPassword;
//    private Button btnLogin, btnGoToRegister;
//    private ApiService api;
//    private CheckBox cbRememberMe;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        etUsername      = findViewById(R.id.etLoginUsername);
//        etPassword      = findViewById(R.id.etLoginPassword);
//        btnLogin        = findViewById(R.id.btnLogin);
//        btnGoToRegister = findViewById(R.id.btnGoToRegister);
//        cbRememberMe    = findViewById(R.id.cbRememberMe); // NUEVO
//
//        api = ApiClient.getClient(LoginActivity.this).create(ApiService.class);
//
//        CheckBox checkboxMostrarPassword = findViewById(R.id.checkboxMostrarPassword);
//
//        checkboxMostrarPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                etPassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            } else {
//                etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            }
//            // Para mantener el cursor al final del texto
//            etPassword.setSelection(etPassword.length());
//        });
//        btnLogin.setOnClickListener(v -> {
//            String user = etUsername.getText().toString().trim();
//            String pass = etPassword.getText().toString();
//
//            if (user.isEmpty() || pass.isEmpty()) {
//                Toast.makeText(LoginActivity.this, "Por favor completa ambos campos", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Usuario login = new Usuario(user, pass);
//            api.loginUser(login).enqueue(new Callback<TokenResponse>() {
//                @Override
//                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
//                        prefs.edit().putString("token", response.body().getToken()).apply();
//
//                        // --- AQUI GUARDAMOS EL NOMBRE DE USUARIO SI EL CHECKBOX ESTÁ MARCADO ---
//                        if (cbRememberMe.isChecked()) {
//                            prefs.edit().putString("usuarioRecordado", user).apply();
//                        } else {
//                            prefs.edit().remove("usuarioRecordado").apply(); // Si no, borramos cualquier recordatorio anterior
//                        }
//
//                        Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, PartidasMenuActivity.class);
//                        intent.putExtra("nombreUsu", user);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TokenResponse> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//
//        btnGoToRegister.setOnClickListener(v -> {
//            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//        });
//    }
//}
