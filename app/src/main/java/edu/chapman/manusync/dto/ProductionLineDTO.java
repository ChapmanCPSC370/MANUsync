package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/28/15.
 *
 * A DTO class that encapsulates any data pertaining to production lines.
 */
public class ProductionLineDTO {

    private int numWorkstations;
    private String parseId, productionLineId, productCreated;

    public ProductionLineDTO(){
        this.parseId = null;
        this.numWorkstations = -1;
        this.productionLineId = "";
        this.productCreated = "";
    }

    public ProductionLineDTO(String parseId, String productionLineId, int numWorkstations, String productCreated){
        this.parseId = parseId;
        this.productionLineId = productionLineId;
        this.numWorkstations = numWorkstations;
        this.productCreated = productCreated;
    }

    /* Accessors and Mutators */

    public String getParseId() { return this.parseId; }
    public String getProductionLineId() { return this.productionLineId; }
    public void setProductionLineId(String productionLineId) { this.productionLineId = productionLineId; }
    public String getProductCreated() { return this.productCreated; }
    public void setProductCreated(String productCreated) { this.productCreated = productCreated; }
    public int getNumWorkstations() { return this.numWorkstations; }
    public void setNumWorkstations(int numWorkstations) { this.numWorkstations = numWorkstations; }
}
