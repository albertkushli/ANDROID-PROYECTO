package dsa.upc.edu.listapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import dsa.upc.edu.listapp.api.ApiClient;
import dsa.upc.edu.listapp.api.ApiService;
import dsa.upc.edu.listapp.models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword,etConfirmPassword;
    private Button btnRegister, btnGoToLogin;
    private ApiService api;

    private EditText etRegisterPassword;
    private CheckBox checkboxMostrarPasswordRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername   = findViewById(R.id.etUsername);
        etPassword   = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister  = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        api = ApiClient.getClient(RegisterActivity.this).create(ApiService.class);

        CheckBox checkboxMostrarPasswordRegister = findViewById(R.id.checkboxMostrarPasswordRegister);

        checkboxMostrarPasswordRegister.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Mantener el cursor al final
            etPassword.setSelection(etPassword.length());
            etConfirmPassword.setSelection(etConfirmPassword.length());
        });
        btnRegister.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();

            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario nuevo = new Usuario(null, user, pass);
            Toast.makeText(this, "Registrando nuevo usuario...", Toast.LENGTH_SHORT).show();
            api.registerUser(nuevo).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(RegisterActivity.this,"Response : "+response.code(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        Log.d("API", "Código HTTP: " + response.code());
                        Log.d("API", "Respuesta: " + response.body().toString());
                        //Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        try {
                            String errorJson = response.errorBody().string();
                            Log.e("API", "Mensaje del servidor: " + errorJson);
                            // Extrae mensaje si es JSON
                            String msg = errorJson;
                            if (errorJson.contains("error")) {
                                int start = errorJson.indexOf(":") + 2;
                                int end = errorJson.lastIndexOf("\"");
                                msg = errorJson.substring(start, end);
                            }
                            Toast.makeText(RegisterActivity.this, "Servidor dice: " + msg, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("API", "Error. Código: " + response.code());
                        Log.e("API", "Error body: " + response.errorBody().toString());
                        //Toast.makeText(RegisterActivity.this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}

