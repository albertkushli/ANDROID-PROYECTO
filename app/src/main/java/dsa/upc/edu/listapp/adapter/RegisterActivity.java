//package dsa.upc.edu.listapp.adapter;
//
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.InputType;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import dsa.upc.edu.listapp.R;
//import dsa.upc.edu.listapp.auth.Usuario;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText etUsername, etPassword,etConfirmPassword;
//    private Button btnRegister, btnGoToLogin;
//    private ApiService api;
//
//    private EditText etRegisterPassword;
//    private CheckBox checkboxMostrarPasswordRegister;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        etUsername   = findViewById(R.id.etUsername);
//        etPassword   = findViewById(R.id.etPassword);
//        etConfirmPassword = findViewById(R.id.etConfirmPassword);
//
//        btnRegister  = findViewById(R.id.btnRegister);
//        btnGoToLogin = findViewById(R.id.btnGoToLogin);
//
//        api = ApiClient.getClient(RegisterActivity.this).create(ApiService.class);
//
//        CheckBox checkboxMostrarPasswordRegister = findViewById(R.id.checkboxMostrarPasswordRegister);
//
//        checkboxMostrarPasswordRegister.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            } else {
//                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            }
//            // Mantener el cursor al final
//            etPassword.setSelection(etPassword.length());
//            etConfirmPassword.setSelection(etConfirmPassword.length());
//        });
//        btnRegister.setOnClickListener(v -> {
//            String user = etUsername.getText().toString().trim();
//            String pass = etPassword.getText().toString();
//            String confirmPass = etConfirmPassword.getText().toString();
//
//            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
//                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (!pass.equals(confirmPass)) {
//                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Usuario nuevo = new Usuario(user, pass);
//            api.registerUser(nuevo).enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if (response.isSuccessful()) {
//                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Toast.makeText(RegisterActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//
//        btnGoToLogin.setOnClickListener(v -> {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        });
//    }
//}
//
