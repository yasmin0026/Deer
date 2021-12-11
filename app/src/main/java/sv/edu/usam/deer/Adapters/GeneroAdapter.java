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

import java.util.List;

import sv.edu.usam.deer.CancionByGeneroActivity;
import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.R;
import sv.edu.usam.deer.VideoContenido;


public class GeneroAdapter  extends RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>{
    private Context context;
    private List<Contenido> contenidoList;

    public GeneroAdapter(Context context, List<Contenido> contenidoList) {
        this.context = context;
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public GeneroAdapter.GeneroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_genero_contenido, null);
        return new GeneroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneroViewHolder holder, int position) {
        Contenido contenido = contenidoList.get(position);


        holder.txtGene.setText(contenido.getGenero());
    }



    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class GeneroViewHolder extends RecyclerView.ViewHolder {

        TextView txtGene;

        public GeneroViewHolder(@NonNull View itemView) {
            super(itemView);


            txtGene = itemView.findViewById(R.id.tvGene);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CancionByGeneroActivity.class);
                    intent.putExtra("genero", contenidoList.get(getAdapterPosition()).getGenero());
                    context.startActivity(intent);
                }
            });
        }
    }
}
