package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class IssueDTO {

    private String issueId, reason;

    public IssueDTO(String issueId, String reason) {
        this.issueId = issueId;
        this.reason = reason;
    }

    /* Accessors and Mutators */
    public String getIssueId() { return this.issueId; }
    public String getReason() { return this.reason; }
}
