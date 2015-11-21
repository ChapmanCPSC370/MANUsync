package edu.chapman.manusync.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.chapman.manusync.R;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/21/15.
 */
public class TaktMenuListAdapter extends ArrayAdapter<String> {

    private List<String> objects;
    private int resource;
    private Context context;

    public TaktMenuListAdapter(Context context, int resource, List<String> objects) {
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

        String menuItem = objects.get(position);

        if(menuItem != null) {
            TextView textView = (TextView) view.findViewById(R.id.spinner_tv);
            textView.setText(menuItem);
        }
        return view;
    }
}
