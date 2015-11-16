package edu.chapman.manusync.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.ProductionLineDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/15/15.
 */
public class ProductionLineAdapter extends ArrayAdapter<ProductionLineDTO> {

    private Context context;
    private int resource;
    private List<ProductionLineDTO> objects;

    public ProductionLineAdapter(Context context, int resource, List<ProductionLineDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        ProductionLineDTO pl = objects.get(position);

        if(pl != null) {
            TextView textView = (TextView) view.findViewById(R.id.production_line_tv);
            textView.setText(pl.getProductionLineId());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        ProductionLineDTO pl = objects.get(position);

        if(pl != null) {
            TextView textView = (TextView) view.findViewById(R.id.production_line_tv);
            textView.setText(pl.getProductionLineId());
        }

        return view;
    }

    @Override
    public ProductionLineDTO getItem(int position) {
        return objects.get(position);
    }
}
