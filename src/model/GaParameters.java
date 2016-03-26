package model;

/**
 * Created by Vitalij on 27/02/2016.
 */
public class GaParameters {
    int mutationProbability;
    int crossoverProbability;
    int populationSize;
    int n;
    boolean quasiOptimum;

    public GaParameters(int populationSize, int crossoverProbability, int mutationProbability, int n, boolean quasiOptimum) {
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
        this.populationSize = populationSize;
        this.n = n;
        this.quasiOptimum = quasiOptimum;
    }

    public int getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(int mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public int getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(int crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public boolean isQuasiOptimum() {
        return quasiOptimum;
    }

    public void setQuasiOptimum(boolean quasiOptimum) {
        this.quasiOptimum = quasiOptimum;
    }
}
