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

import sv.edu.usam.deer.AlbumContenido;
import sv.edu.usam.deer.Models.AlbumsArt;
import sv.edu.usam.deer.R;

public class ContenidoAlbumnesAdapter extends RecyclerView.Adapter<ContenidoAlbumnesAdapter.AlbumByArtistViewHolder> {

    private Context context;
    private List<AlbumsArt> contenidoList;

    public ContenidoAlbumnesAdapter(Context context, List<AlbumsArt> contenidoList) {
        this.context = context;
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public AlbumByArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_albums_by_artists, null);
        return new AlbumByArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumByArtistViewHolder holder, int position) {
        AlbumsArt albumsArt = contenidoList.get(position);
        //loading the image
        Glide.with(context)
                .load(albumsArt.getPortada())
                .into(holder.imPortada);
        holder.txtArtista.setText(albumsArt.getAlbum());
    }

    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class AlbumByArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView imPortada;
        TextView txtArtista;

        public AlbumByArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            imPortada = itemView.findViewById(R.id.imgAlbumArtist);
            txtArtista = itemView.findViewById(R.id.tvNombreAlbumArtist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AlbumContenido.class);
                    intent.putExtra("artistaName", contenidoList.get(getAdapterPosition()).getArtista());
                    intent.putExtra("albumName", contenidoList.get(getAdapterPosition()).getAlbum());
                    context.startActivity(intent);
                }
            });
        }
    }
}
