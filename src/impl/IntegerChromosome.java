package impl;

import ga.IChromosome;
import model.ChessBoard;

/**
 * Created by Vitalij on 27/02/2016.
 */
public class IntegerChromosome implements IChromosome {

    private int[] chromosomeValue;
    private int fitnessFunctionValue;


    public IntegerChromosome(int[] value) {
        this.chromosomeValue = value;
        setFitnessFunctionValue(ChessBoard.calculateFitnessFunction(value));
    }

    public IntegerChromosome() {
    }

    @Override
    public int getFitnessFunctionValue() {
        return fitnessFunctionValue;
    }

    @Override
    public Object getChromosomeValue() {
        return chromosomeValue;
    }

    public void setFitnessFunctionValue(int fitnessFunctionValue) {
        this.fitnessFunctionValue = fitnessFunctionValue;
    }

}
