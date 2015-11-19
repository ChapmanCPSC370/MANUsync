package edu.chapman.manusync.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseException;

import edu.chapman.manusync.R;
import edu.chapman.manusync.adapter.IssueListAdapter;
import edu.chapman.manusync.dao.IssueDAO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class IssueDialog extends Dialog {

    private ListView issueListView;
    private IssueDAO issueProvider;
    private IssueListAdapter adapter;

    public IssueDialog(Context context) {
        super(context);

        setContentView(R.layout.dialog_issue);
        issueProvider = new IssueDAO();
        try {
            adapter = new IssueListAdapter(context,
                    R.layout.item_spinner, issueProvider.getAllIssues());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        issueListView = (ListView) findViewById(R.id.issue_lv);
        issueListView.setAdapter(adapter);
    }

}
