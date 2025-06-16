package dsa.upc.edu.listapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class NavigationBottomSheet {

    public static void showNavigationMenu(Context context, String idPartida) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View sheetView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet, null);
        bottomSheetDialog.setContentView(sheetView);

        // Referencias de los botones
        View btnGoPartidas = sheetView.findViewById(R.id.btnGoPartidas);
        View btnGoStore    = sheetView.findViewById(R.id.btnGoStore);
        View btnGoCart     = sheetView.findViewById(R.id.btnGoCart);
        View btnGoInventory = sheetView.findViewById(R.id.btnGoInventario);
        View btnGoVideos   = sheetView.findViewById(R.id.btnGoVideos);
        View btnLogout     = sheetView.findViewById(R.id.btnLogout);
        View btnReportDenuncia = sheetView.findViewById(R.id.btnReportDenuncia);
        View btnViewDenuncias = sheetView.findViewById(R.id.btnViewDenuncias);

        // Si idPartida es nulo (estás en el menú principal), ocultamos todo excepto "Cerrar sesión" y "Videos"
        if (idPartida == null) {
            btnGoPartidas.setVisibility(View.GONE);
            btnGoStore.setVisibility(View.GONE);
            btnGoCart.setVisibility(View.GONE);
            btnGoInventory.setVisibility(View.GONE);
        } else {
            // Botón para ir a Partidas
            btnGoPartidas.setOnClickListener(v -> {
                context.startActivity(new Intent(context, PartidasMenuActivity.class));
                bottomSheetDialog.dismiss();
            });

            // Botón para ir a Tienda
            btnGoStore.setOnClickListener(v -> {
                context.startActivity(new Intent(context, StoreActivity.class)
                        .putExtra("idPartida", idPartida));
                bottomSheetDialog.dismiss();
            });

            // Botón para ir al Carrito
            btnGoCart.setOnClickListener(v -> {
                context.startActivity(new Intent(context, CarritoActivity.class)
                        .putExtra("idPartida", idPartida));
                bottomSheetDialog.dismiss();
            });

            // Botón para ir al Inventario
            btnGoInventory.setOnClickListener(v -> {
                context.startActivity(new Intent(context, InventarioActivity.class)
                        .putExtra("idPartida", idPartida));
                bottomSheetDialog.dismiss();
            });
        }

        // Botón para ir a Videos (siempre visible)
        btnGoVideos.setOnClickListener(v -> {
            context.startActivity(new Intent(context, VideoActivity.class));
            bottomSheetDialog.dismiss();
        });

        // Botón para cerrar sesión (siempre visible)
        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            prefs.edit().remove("usuarioRecordado").apply();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            bottomSheetDialog.dismiss();
        });
        if (btnReportDenuncia != null) {
            btnReportDenuncia.setOnClickListener(v -> {
                context.startActivity(new Intent(context, ReportDenunciaActivity.class));
                bottomSheetDialog.dismiss();
            });
        }

        if (btnViewDenuncias != null) {
            btnViewDenuncias.setOnClickListener(v -> {
                context.startActivity(new Intent(context, DenunciaListActivity.class));
                bottomSheetDialog.dismiss();
            });
        }
        bottomSheetDialog.show();
    }
}