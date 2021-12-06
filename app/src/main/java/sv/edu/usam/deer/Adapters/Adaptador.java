package sv.edu.usam.deer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.R;

public class Adaptador extends ArrayAdapter<Contenido> {
    Context context;
    List<Contenido> arrayListEmployee;

    public Adaptador(@NonNull Context context, List<Contenido> arrayListEmployee) {
        super(context, R.layout.custom_list_item ,arrayListEmployee);

        this.context = context;
        this.arrayListEmployee = arrayListEmployee;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);

        TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvName = view.findViewById(R.id.txtArtista);
        TextView tvCanc = view.findViewById(R.id.txtCancion);

        tvID.setText(arrayListEmployee.get(position).getId_contenido());
        tvName.setText(arrayListEmployee.get(position).getArtista());
        tvCanc.setText(arrayListEmployee.get(position).getNombre_cancion());

        return view;

    }
}
