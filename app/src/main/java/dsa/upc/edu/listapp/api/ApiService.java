package dsa.upc.edu.listapp.api;

import java.util.List;

import dsa.upc.edu.listapp.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // Usuario

    @POST("usuarios/register")
    Call<Void> registerUser(@Body Usuario usuario);

    @POST("usuarios/login")
    Call<TokenResponse> loginUser(@Body Usuario usuario);

    @GET("usuarios/nombre/{nombre}")
    Call<Usuario> getUsuarioPorNombre(@Path("nombre") String nombre);

    // Partida

    @POST("partidas")
    Call<Partida> addPartidaSinDatos();
    // Se crea una partida con 3 vidas, 100 monedas, 0 puntuacion
    // a partir de id_usuario, que se envia en el token

    @GET("partidas")
    Call<List<Partida>> getPartidas();

    @GET("partidas/{id_partida}")
    Call<Partida> getPartida(@Path("id_partida") String id_partida);
    // En principio no se usa porque usamos intent para pasar el objeta
    // partida entero, en clase partida esta Serializable

    @GET("partidas/{id_partida}/monedas")
    Call<MonedasResponse> getMonedasDePartida(@Path("id_partida") String id_partida);

    @PUT("partidas")
    Call<Partida> updatePartida(@Body Partida partida);
    // Quizas para unity

    @DELETE("partidas/{id_partida}")
    Call<Void> deletePartida(@Path("id_partida") String id_partida);

    // CategoriaObjeto

    @GET("categoria")
    Call<List<CategoriaObjeto>> getAllCategorias();
    // Obtenemos una lista con todas las categorias de los objetos

    // Tienda que usa Objeto
    @GET("tienda/productos")
    Call<List<Objeto>> getAllProductos();

    @GET("tienda/producto/aleatorio")
    Call<Objeto> getProductoAleatorio();

    @GET("tienda/productos/categoria/{id_categoria}")
    Call<List<Objeto>> getProductosPorCategoria(@Path("id_categoria") String id_categoria);

    // Carrito
    // Falta comprar objeto aleatorio

    @POST("carrito/{id_partida}/{id_objeto}")
    Call<Void> agregarObjetoACarrito(@Path("id_partida") String id_partida,
                                     @Path("id_objeto") String id_objeto);

    @POST("carrito/comprar/{id_partida}")
    Call<Void> realizarCompra(@Path("id_partida") String id_partida);

    @GET("carrito/{id_partida}")
    Call<List<Objeto>> getObjetosDelCarrito(@Path("id_partida") String id_partida);

    @DELETE("carrito/{id_partida}/{id_objeto}")
    Call<Void> eliminarObjetoDelCarrito(@Path("id_partida") String id_partida,
                                        @Path("id_objeto") String id_objeto);

    @DELETE("carrito/vaciar/{id_partida}")
    Call<Void> vaciarCarrito(@Path("id_partida") String id_partida);

    // Inventario

    @POST("inventario/aleatorio/{id_partida}/{id_objeto}")
    Call<Void> comprarObjetoAleatorio(@Path("id_partida") String id_partida,
                                      @Path("id_objeto") String id_objeto);

    @GET("inventario/{id_partida}")
    Call<List<Objeto>> getInventario(@Path("id_partida") String id_partida);

    @DELETE("inventario/{id_partida}/{id_objeto}")
    Call<Void> eliminarObjeto(@Path("id_partida") String id_partida,
                              @Path("id_objeto") String id_objeto);

    @DELETE("inventario/{id_partida}")
    Call<Void> eliminarTodosLosObjetos(@Path("id_partida") String id_partida);

    // Insignias

    @GET("insignia")
    Call<List<Insignia>> getAllInsignias();

    @GET("insignia/{id_insignia}")
    Call<Insignia> getInsignia(@Path("id_insignia") String id_insignia);

    // Insignias del usuario

    @GET("Usuario_Insignia/{id_usuario}/insignias")
    Call<List<Insignia>> getInsigniasDelUsuario(@Path("id_usuario") String id_usuario);

    // Alternativa: si el backend usa el token para identificar al usuario
    @GET("Usuario_Insignia")
    Call<List<Insignia>> getMisInsignias();

    // FAQs
    @GET("faqs")
    Call<List<Faq>> getFaqs();

    // Denuncias
    @POST("denuncias")
    Call<Denuncia> reportDenuncia(@Body Denuncia denuncia);

    @GET("denuncias")
    Call<List<Denuncia>> getDenuncias();

    @GET("denuncias/usuario")
    Call<List<Denuncia>> getMisDenuncias();
    @POST("consultas")
    Call<Consulta> addConsulta(@Body Consulta consulta);
}