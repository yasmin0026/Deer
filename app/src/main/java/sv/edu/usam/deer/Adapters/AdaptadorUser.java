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

import sv.edu.usam.deer.Models.ContenidoUser;
import sv.edu.usam.deer.R;

public class AdaptadorUser extends ArrayAdapter<ContenidoUser> {
    Context context;
    List<ContenidoUser> arrayListEmployee;

    public AdaptadorUser(@NonNull Context context, List<ContenidoUser> arrayListEmployee) {
        super(context, R.layout.custom_list_item ,arrayListEmployee);

        this.context = context;
        this.arrayListEmployee = arrayListEmployee;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);

        TextView tvID = view.findViewById(R.id.txt_id_user);
        TextView tvName = view.findViewById(R.id.txtUsuario);
        TextView tvCanc = view.findViewById(R.id.txtNombres);

        tvID.setText(arrayListEmployee.get(position).getId());
        tvName.setText(arrayListEmployee.get(position).getUsuario());
        tvCanc.setText(arrayListEmployee.get(position).getNombres());

        return view;

    }
}
