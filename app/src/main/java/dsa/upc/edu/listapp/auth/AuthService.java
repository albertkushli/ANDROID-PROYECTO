package dsa.upc.edu.listapp.auth;

import dsa.upc.edu.listapp.models.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthService {

    @POST("usuarios/register")
    Call<Void> registerUser(@Body Usuario usuario);

    @POST("usuarios/login")
    Call<TokenResponse> loginUser(@Body Usuario usuario);



}