package edu.chapman.manusync.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.PartDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class PartListAdapter extends ArrayAdapter<PartDTO> {

    private Context context;
    private int resource;
    private List<PartDTO> objects;

    public PartListAdapter(Context context, int resource, List<PartDTO> objects) {
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

        PartDTO part = objects.get(position);

        if(part != null) {
            TextView textView = (TextView) view.findViewById(R.id.spinner_tv);
            textView.setText(part.getPartNumber());
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

        PartDTO part = objects.get(position);

        if(part != null) {
            TextView textView = (TextView) view.findViewById(R.id.spinner_tv);
            textView.setText(part.getPartNumber());
        }

        return view;
    }

    @Override
    public PartDTO getItem(int position) {
        return objects.get(position);
    }
}
