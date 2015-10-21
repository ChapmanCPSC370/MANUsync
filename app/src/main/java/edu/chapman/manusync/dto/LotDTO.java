package edu.chapman.manusync.dto;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * A DTO class that stores information about current lots.
 */
public class LotDTO {

    private int productionLineNumber, workstationNumber, partNumber, lotNumber, quantity;

    public LotDTO(int productionLineNumber, int workstationNumber,
                  int partNumber, int lotNumber, int quantity){
        this.productionLineNumber = productionLineNumber;
        this.workstationNumber = workstationNumber;
        this.partNumber = partNumber;
        this.lotNumber = lotNumber;
        this.quantity = quantity;
    }

    /* Accessors and mutators */
    public void setProductionLineNumber(int productionLineNumber) { this.productionLineNumber = productionLineNumber; }
    public int getProductionLineNumber() { return this.productionLineNumber; }
    public void setWorkstationNumber(int workstationNumber) { this.workstationNumber = workstationNumber; }
    public int getWorkstationNumber() { return this.workstationNumber; }
    public void setPartNumber(int partNumber) { this.partNumber = partNumber; }
    public int getPartNumber() { return this.partNumber; }
    public void setLotNumber(int lotNumber) { this.lotNumber = lotNumber; }
    public int getLotNumber() { return this.lotNumber; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getQuantity() { return this.quantity; }

    /* Acessors and mutators for string data */
    public void setProductionLineNumber(String productionLineNumber) { this.productionLineNumber = Integer.parseInt(productionLineNumber); }
    public String getProductionLineNumberString() { return Integer.toString(this.productionLineNumber); }
    public void setWorkstationNumber(String workstationNumber) { this.workstationNumber = Integer.parseInt(workstationNumber); }
    public String getWorkstationNumberString() { return Integer.toString(this.workstationNumber); }
    public void setPartNumber(String partNumber) { this.partNumber = Integer.parseInt(partNumber); }
    public String getPartNumberString() { return Integer.toString(this.partNumber); }
    public void setLotNumber(String lotNumber) { this.lotNumber = Integer.parseInt(lotNumber); }
    public String getLotNumberString() { return Integer.toString(this.lotNumber); }
    public void setQuantity(String quantity) { this.quantity = Integer.parseInt(quantity); }
    public String getQuantityString() { return Integer.toString(this.quantity); }
}
