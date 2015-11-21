package edu.chapman.manusync.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.IssueDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class IssueListAdapter extends ArrayAdapter<IssueDTO> {
    private static final String TAG = IssueListAdapter.class.getSimpleName();

    private Context context;
    private int resource;
    private List<IssueDTO> objects;

    public IssueListAdapter(Context context, int resource, List<IssueDTO> objects) {
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

        IssueDTO issue = objects.get(position);

        if(issue != null) {
            Log.d(TAG, "setting item: " + issue.getIssueId());
            TextView textView = (TextView) view.findViewById(R.id.spinner_tv);
            textView.setText(issue.getReason());
        }
        return view;
    }



    @Override
    public IssueDTO getItem(int position) {
        return objects.get(position);
    }
}
