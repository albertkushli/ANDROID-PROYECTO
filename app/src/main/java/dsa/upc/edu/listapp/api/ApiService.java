package dsa.upc.edu.listapp.api;

import java.util.List;

import dsa.upc.edu.listapp.models.CategoriaObjeto;
import dsa.upc.edu.listapp.models.Insignia;
import dsa.upc.edu.listapp.models.MonedasResponse;
import dsa.upc.edu.listapp.models.Objeto;
import dsa.upc.edu.listapp.models.Partida;
import dsa.upc.edu.listapp.models.Usuario;
import retrofit2.Call;
import retrofit2.http.*;


public interface ApiService {

    // Usuario
    @PUT("usuarios")
    Call<Usuario> updateUsuario(@Body Usuario usuario);
    //Para poder actualizar el nombre

    // Partida

    @POST("partidas")
    Call<Partida> addPartidaSinDatos();
    // Se crea una partida con 3 vidas, 100 monedas, 0 puntuacion
    // a partir de id_usuario, que se envia en el token

    @GET("partidas")
    Call<List<Partida>> getPartidas();

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

    @POST("inventario/{id_partida}/{id_objeto}")
    Call<Void> agregarObjeto(@Path("id_partida") String id_partida,
                             @Path("id_objeto") String id_objeto);

    @GET("inventario/{id_partida}")
    Call<List<Objeto>> getInventario(@Path("id_partida") String id_partida);

    @DELETE("inventario/{id_partida}/{id_objeto}")
    Call<Void> eliminarObjeto(@Path("id_partida") String id_partida,
                              @Path("id_objeto") String id_objeto);

    @DELETE("inventario/{id_partida}")
    Call<Void> eliminarTodosLosObjetos(@Path("id_partida") String id_partida);

}


