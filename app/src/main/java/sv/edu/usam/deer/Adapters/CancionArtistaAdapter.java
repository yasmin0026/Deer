package sv.edu.usam.deer.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sv.edu.usam.deer.Models.AlbumsArt;
import sv.edu.usam.deer.Models.CancionGenero;

public class CancionArtistaAdapter extends RecyclerView.Adapter<CancionArtistaAdapter.CancionArtistaViewHolder>{
    private Context context;
    private List<CancionGenero> contenidoList;

    @NonNull
    @Override
    public CancionArtistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CancionArtistaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CancionArtistaViewHolder extends RecyclerView.ViewHolder {
        public CancionArtistaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
