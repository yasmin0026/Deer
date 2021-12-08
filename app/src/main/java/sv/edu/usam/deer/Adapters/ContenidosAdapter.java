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
import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.R;
import sv.edu.usam.deer.VideoContenido;

public class ContenidosAdapter extends RecyclerView.Adapter<ContenidosAdapter.ContenidoViewHolder>{

    private Context context;
    private List<Contenido> contenidoList;

    public ContenidosAdapter(Context context, List<Contenido> contenidoList) {
        this.context = context;
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_video_contenido, null);
        return new ContenidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
        Contenido contenido = contenidoList.get(position);
        //loading the image
        Glide.with(context)
                .load(contenido.getPortada())
                .into(holder.imPortada);
        holder.txtTitulo.setText(contenido.getNombre_cancion());
        holder.txtArtista.setText(contenido.getArtista());
    }

    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ImageView imPortada;
        TextView txtTitulo,txtArtista;

        public ContenidoViewHolder(@NonNull View itemView) {
            super(itemView);

            imPortada = itemView.findViewById(R.id.imvPortada);
            txtTitulo = itemView.findViewById(R.id.tvTitulo);
            txtArtista = itemView.findViewById(R.id.tvArtista);

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
