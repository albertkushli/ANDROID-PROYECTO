package dsa.upc.edu.listapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import dsa.upc.edu.listapp.models.Video;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView rvVideos;
    private SwipeRefreshLayout swipeRefreshVideos;
    private Button btnBack;
    private FloatingActionButton fabOpenMenu;
    private VideoAdapter videoAdapter;
    private List<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Inicializar vistas
        rvVideos = findViewById(R.id.rvVideos);
        swipeRefreshVideos = findViewById(R.id.swipeRefreshVideos);
        btnBack = findViewById(R.id.btn_back);
        fabOpenMenu = findViewById(R.id.fabOpenMenu);

        // Configurar RecyclerView
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(videos, new VideoAdapter.OnVideoClickListener() {
            @Override
            public void onVideoClick(Video video) {
                abrirVideoYouTube(video.getVideo());
            }
        });

        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        rvVideos.setAdapter(videoAdapter);

        // Cargar videos de ejemplo (preguntas frecuentes)
        cargarVideosFAQ();

        // Configurar SwipeRefresh
        swipeRefreshVideos.setOnRefreshListener(() -> {
            cargarVideosFAQ();
            swipeRefreshVideos.setRefreshing(false);
        });

        // Configurar botones
        btnBack.setOnClickListener(v -> finish());

        fabOpenMenu.setOnClickListener(v -> {
            NavigationBottomSheet.showNavigationMenu(this, null);
        });
    }

    private void cargarVideosFAQ() {
        videos.clear();

        // Preguntas frecuentes con sus videos de YouTube
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo empiezo mi aventura?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo compro items en la tienda?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo gestiono mi inventario?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo obtengo monedas?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo desbloqueo insignias?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo cambio mi avatar?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo funciona el carrito?"));
        videos.add(new Video("https://youtu.be/dQw4w9WgXcQ", "¿Cómo inicio una partida?"));

        videoAdapter.notifyDataSetChanged();
    }

    private void abrirVideoYouTube(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        } catch (Exception e) {
            // Si no tiene YouTube instalado, abrir en navegador
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}