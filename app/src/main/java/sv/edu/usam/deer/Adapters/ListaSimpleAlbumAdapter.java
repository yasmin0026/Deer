package sv.edu.usam.deer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.R;
import sv.edu.usam.deer.VideoContenido;

public class ListaSimpleAlbumAdapter extends RecyclerView.Adapter<ListaSimpleAlbumAdapter.listaAlbumViewHolder> {

    private Context context;
    private List<Contenido> contenidoList;

    public ListaSimpleAlbumAdapter(Context context, List<Contenido> contenidoList) {
        this.context = context;
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public listaAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_simple_canciones, null);
        return new listaAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaAlbumViewHolder holder, int position) {
        Contenido contenido = contenidoList.get(position);
        holder.txtTitulo.setText(contenido.getNombre_cancion());
    }

    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class listaAlbumViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;

        public listaAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.tituloCancion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VideoContenido.class);
                    intent.putExtra("video_id", contenidoList.get(getAdapterPosition()).getLink_video());
                    context.startActivity(intent);
                }
            });
        }
    }
}
