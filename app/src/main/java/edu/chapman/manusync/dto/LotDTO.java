package edu.chapman.manusync.dto;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * A DTO class that stores information about current lots.
 */
public class LotDTO {

    protected String lotNumber, parseID;
    protected ProductionLineDTO productionLine;
    protected PartDTO part;
    protected int workstationNumber, quantity, finishedParts;

    public LotDTO(ProductionLineDTO productionLine, int workstationNumber,
                  PartDTO part, String lotNumber, int quantity){
        this.productionLine = productionLine;
        this.workstationNumber = workstationNumber;
        this.part = part;
        this.lotNumber = lotNumber;
        this.quantity = quantity;
        this.finishedParts = 0;
    }

    public LotDTO(String parseID, String lotNumber, ProductionLineDTO productionLine,
                  int workstationNumber, PartDTO part, int quantity) {
        this.parseID = parseID;
        this.lotNumber = lotNumber;
        this.productionLine = productionLine;
        this.workstationNumber = workstationNumber;
        this.part = part;
        this.quantity = quantity;
    }

    public LotDTO(LotDTO lotDTO) {
        this.parseID = lotDTO.parseID;
        this.lotNumber = lotDTO.lotNumber;
        this.productionLine = lotDTO.productionLine;
        this.workstationNumber = lotDTO.workstationNumber;
        this.part = lotDTO.part;
        this.quantity = lotDTO.quantity;
        this.finishedParts = lotDTO.finishedParts;
    }

    /* Accessors and mutators */
    public String getParseID() { return this.parseID; }
    public void setParseID(String parseID) { this.parseID = parseID; }
    public ProductionLineDTO getProductionLineNumber() { return this.productionLine; }
    public int getWorkstationNumber() { return this.workstationNumber; }
    public PartDTO getPart() { return this.part; }
    public String getLotNumber() { return this.lotNumber; }
    public int getQuantity() { return this.quantity; }
    public double getTotalTaktTime() { return this.quantity * this.part.getTaktTime(); }
    public int getFinishedParts() { return this.finishedParts; }
    public void setFinishedParts(int finishedParts) { this.finishedParts = finishedParts; }

    /* Acessors and mutators for string data */
    public String getProductionLineNumberString() { return productionLine.getProductionLineId(); }
    public String getWorkstationNumberString() { return Integer.toString(this.workstationNumber); }
    public String getPartNumberString() { return part.getPartNumber(); }
    public String getQuantityString() { return Integer.toString(this.quantity); }
}
