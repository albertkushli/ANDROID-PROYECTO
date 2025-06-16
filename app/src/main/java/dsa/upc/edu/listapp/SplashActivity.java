package dsa.upc.edu.listapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Iniciar la música
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_music);
        mediaPlayer.setLooping(false); // Cambiar a true si quieres que se repita
        mediaPlayer.start();

        // Hacer parpadear el título ADVENTURE
        //TextView gameTitle = findViewById(R.id.gameTitle);
        //Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_animation);
        //gameTitle.startAnimation(blinkAnimation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Detener la música antes de cambiar de activity
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
            String usuarioRecordado = prefs.getString("usuarioRecordado", null);

            if (usuarioRecordado != null) {
                // El usuario eligió recordar
                Intent intent = new Intent(SplashActivity.this, PartidasMenuActivity.class);
                intent.putExtra("nombre", usuarioRecordado);
                startActivity(intent);
            } else {
                // No hay usuario recordado, ir al login
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Asegurarse de liberar el MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}