package edu.chapman.manusync.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;

import edu.chapman.manusync.R;
import edu.chapman.manusync.adapter.IssueListAdapter;
import edu.chapman.manusync.dao.IssueDAO;
import edu.chapman.manusync.dto.IssueDTO;
import edu.chapman.manusync.dto.LotDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class IssueDialog extends Dialog {

    private ListView issueListView;
    private IssueDAO issueProvider;
    private IssueListAdapter adapter;
    private LotDTO lot;

    public IssueDialog(Context context, LotDTO lot) {
        super(context);
        this.lot = lot;

        /* this does not let the user cancel the dialog when clicked outside, you must select an issue */
        setCanceledOnTouchOutside(false);

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
        issueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IssueDTO issue = (IssueDTO) issueListView.getItemAtPosition(position);
                issueProvider.logIssue(IssueDialog.this.lot, issue);
                IssueDialog.this.dismiss();
            }
        });
    }
}
