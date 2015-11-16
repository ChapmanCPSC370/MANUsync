package edu.chapman.manusync.dto;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * A DTO class that stores information about current lots.
 */
public class LotDTO {

    protected ProductionLineDTO productionLine;
    protected PartDTO part;
    protected String lotNumber;
    protected int workstationNumber, quantity;

    public LotDTO(ProductionLineDTO productionLine, int workstationNumber,
                  PartDTO part, String lotNumber, int quantity){
        this.productionLine = productionLine;
        this.workstationNumber = workstationNumber;
        this.part = part;
        this.lotNumber = lotNumber;
        this.quantity = quantity;
    }

    /* Accessors and mutators */
    public ProductionLineDTO getProductionLineNumber() { return this.productionLine; }
    public int getWorkstationNumber() { return this.workstationNumber; }
    public PartDTO getPart() { return this.part; }
    public String getLotNumber() { return this.lotNumber; }
    public int getQuantity() { return this.quantity; }

    /* Acessors and mutators for string data */
    public String getProductionLineNumberString() { return productionLine.getProductionLineId(); }
    public String getWorkstationNumberString() { return Integer.toString(this.workstationNumber); }
    public String getPartNumberString() { return part.getPartNumber(); }
    public String getQuantityString() { return Integer.toString(this.quantity); }
}
