package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class PartDTO {

    private String parseId, partNumber;
    private double taktTime;

    public PartDTO(String parseId, String partNumber, double taktTime) {
        this.parseId = parseId;
        this.partNumber = partNumber;
        this.taktTime = taktTime;
    }

    /* Accessors and Mutators */
    public String getParseId() { return this.parseId; }
    public String getPartNumber() { return this.partNumber; }
    public double getTaktTime() { return this.taktTime; }
}
