package edu.chapman.manusync.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */

@Singleton
public class NewLotDataProvider {

    private Context context;
    private List<Integer> partNumbers, workstationNumbers, productionLineNumbers;

    @Inject
    public NewLotDataProvider(Context context) {
        this.context = context;
        this.partNumbers = initPartNumbers();
        this.workstationNumbers = initWorkstationNumbers();
        this.productionLineNumbers = initProductionLineNumbers();
    }

    //TODO: Read from database & return part number data
    private List<Integer> initPartNumbers(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < 10; ++i)
            arr.add(i);

        return arr;
    }

    //TODO: Read from database & return workstation number data
    private List<Integer> initWorkstationNumbers(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < 10; ++i)
            arr.add(i);

        return arr;
    }

    //TODO: Read from database & return production line number data
    private List<Integer> initProductionLineNumbers(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < 10; ++i)
            arr.add(i);

        return arr;
    }

    /* accessors */
    public List<Integer> getPartNumbers() { return this.partNumbers; }
    public List<Integer> getWorkstationNumbers(){ return this.workstationNumbers; }
    public List<Integer> getProductionLineNumbers(){ return this.productionLineNumbers; }
}
