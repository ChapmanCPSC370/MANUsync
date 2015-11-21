package edu.chapman.manusync.dao;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.IssueDTO;
import edu.chapman.manusync.dto.LotDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class IssueDAO {
    private static final String TAG = IssueDAO.class.getSimpleName();

    public IssueDAO() {}

    public List<IssueDTO> getAllIssues() throws ParseException {
        ParseQuery<ParseObject> issueQuery = ParseQuery.getQuery(MANUContract.Issues.TABLE_NAME);
        issueQuery.orderByAscending(MANUContract.Issues.COL_WEIGHT);
        if(!PasserSingleton.getInstance().isConnected())
            issueQuery.fromLocalDatastore();

        List<ParseObject> foundIssues = issueQuery.find();
        List<IssueDTO> allIssues = new ArrayList<>();
        Log.d(TAG, "size : " + foundIssues.size());
        for(ParseObject issue : foundIssues){
            allIssues.add(new IssueDTO(issue.getObjectId(),
                    issue.getString(MANUContract.Issues.COL_REASON)));
        }

        return allIssues;
    }

    public void logIssue(LotDTO lot, IssueDTO issue) {
        ParseObject lotIssue = new ParseObject(MANUContract.LotIssues.TABLE_NAME);
        lotIssue.put(MANUContract.LotIssues.COL_ISSUE_ID, issue.getIssueId());
        lotIssue.put(MANUContract.LotIssues.COL_LOT_ID, lot.getParseID());

        lotIssue.saveEventually();
    }
}
