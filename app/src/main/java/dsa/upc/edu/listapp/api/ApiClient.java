package dsa.upc.edu.listapp.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;

import dsa.upc.edu.listapp.models.Denuncia;
import dsa.upc.edu.listapp.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = Constants.FULL_API_URL;

    public static Retrofit getClient(Context context) {
        // Añadir logging interceptor para depuración
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
                        String token = prefs.getString("token", null);

                        Request.Builder requestBuilder = original.newBuilder();
                        if (token != null) {
                            requestBuilder.header("Authorization", "Bearer " + token);
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        // Configurar Gson con deserializador personalizado para Denuncia
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Denuncia.class, new DenunciaDeserializer())
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    // Deserializador personalizado para Denuncia
    static class DenunciaDeserializer implements JsonDeserializer<Denuncia> {
        @Override
        public Denuncia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            Denuncia denuncia = new Denuncia();

            // Obtener campos básicos
            if (jsonObject.has("id_denuncia")) {
                denuncia.setId_denuncia(jsonObject.get("id_denuncia").getAsString());
            }

            if (jsonObject.has("titulo")) {
                denuncia.setTitulo(jsonObject.get("titulo").getAsString());
            }

            if (jsonObject.has("mensaje")) {
                denuncia.setMensaje(jsonObject.get("mensaje").getAsString());
            }

            if (jsonObject.has("id_usuario")) {
                denuncia.setId_usuario(jsonObject.get("id_usuario").getAsString());
            }

            // Manejar la fecha que puede venir como objeto complejo
            if (jsonObject.has("fecha")) {
                JsonElement fechaElement = jsonObject.get("fecha");

                if (fechaElement.isJsonPrimitive()) {
                    // Si es un string simple
                    denuncia.setFecha(fechaElement.getAsString());
                } else if (fechaElement.isJsonObject()) {
                    // Si es un objeto LocalDateTime
                    JsonObject fechaObj = fechaElement.getAsJsonObject();
                    try {
                        int year = fechaObj.get("year").getAsInt();
                        int month = fechaObj.get("monthValue").getAsInt();
                        int day = fechaObj.get("dayOfMonth").getAsInt();
                        int hour = fechaObj.has("hour") ? fechaObj.get("hour").getAsInt() : 0;
                        int minute = fechaObj.has("minute") ? fechaObj.get("minute").getAsInt() : 0;

                        String fechaFormateada = String.format("%04d-%02d-%02d %02d:%02d",
                                year, month, day, hour, minute);
                        denuncia.setFecha(fechaFormateada);
                    } catch (Exception e) {
                        denuncia.setFecha("Fecha no disponible");
                    }
                }
            }

            return denuncia;
        }
    }
}