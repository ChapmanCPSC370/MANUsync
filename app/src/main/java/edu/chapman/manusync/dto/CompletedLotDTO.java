package edu.chapman.manusync.dto;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/21/15.
 *
 *  A DTO class used to store data about a completed lot.
 */
public class CompletedLotDTO extends LotDTO {

    private long totalTime;
    private double averageTime;
    private boolean finished;

    public CompletedLotDTO(ProductionLineDTO productionLine, int workstationNumber, PartDTO partNumber,
                           String lotNumber, int quantity) {
        super(productionLine, workstationNumber, partNumber, lotNumber, quantity);
        this.totalTime = 0;
        this.averageTime = 0.0;
    }

    public CompletedLotDTO(ProductionLineDTO productionLine, int workstationNumber, PartDTO partNumber,
                           String lotNumber, int quantity, long totalTime) {
        super(productionLine, workstationNumber, partNumber, lotNumber, quantity);
        this.totalTime = totalTime/1000;
        averageTime = ((double)this.totalTime/this.quantity);
    }

    public CompletedLotDTO(LotDTO lot, long totalTime, boolean finished) {
        super(lot);
        this.totalTime = totalTime;
        this.finished = finished;
    }

    /* Acessors and mutators */

    /* We divide times by 1000 because they are hosted in miliseconds */
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime/1000;
        averageTime = ((double)this.totalTime/quantity);
    }
    public long getTotalTime() { return this.totalTime; }
    public double getAverageTime(){ return this.averageTime; }
    public double getTotalTimeSeconds() { return (double)this.totalTime/1000.0; }
    public boolean getIsFinished() { return this.finished; }
}
