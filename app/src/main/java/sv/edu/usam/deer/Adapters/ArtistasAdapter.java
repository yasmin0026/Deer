package sv.edu.usam.deer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.usam.deer.AlbumsByArtista;
import sv.edu.usam.deer.Models.Artista;
import sv.edu.usam.deer.R;

public class ArtistasAdapter extends RecyclerView.Adapter<ArtistasAdapter.ArtistViewHolder> {

    private Context context;
    private List<Artista> contenidoList;

    public ArtistasAdapter(Context context, List<Artista> contenidoList) {
        this.context = context;
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public ArtistasAdapter.ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_all_artistas, null);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistasAdapter.ArtistViewHolder holder, int position) {
        Artista artista = contenidoList.get(position);
        //loading the image
        Glide.with(context)
                .load(artista.getPortada())
                .into(holder.imCover);
        holder.txtNomArtista.setText(artista.getArtista());
    }

    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView imCover;
        TextView txtNomArtista;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imCover = itemView.findViewById(R.id.imArtistaCover);
            txtNomArtista = itemView.findViewById(R.id.tvNombreArtist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AlbumsByArtista.class);
                    intent.putExtra("artista", contenidoList.get(getAdapterPosition()).getArtista());
                    context.startActivity(intent);
                }
            });
        }
    }
}
