package impl;

import ga.IChromosome;
import ga.IGeneticAlgorithm;
import io.DataExport;
import model.ChessBoard;
import model.GaParameters;
import org.apache.log4j.Logger;
import util.RandomUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by Vitalij on 27/02/2016.
 */
public class SimpleGeneticAlgorithm implements IGeneticAlgorithm {
    private static Logger LOGGER = Logger.getLogger(SimpleGeneticAlgorithm.class.getName());
    private int lastMaxFFvalue = 0;
    private List<Integer> iterationData = new ArrayList<>();

    @Override
    public List<IChromosome> selection(List<IChromosome> chromosomeList, int populationSize) {
        List<IChromosome> newSelection = new ArrayList<IChromosome>();
        for (IChromosome chr : chromosomeList) {
            int secondPlayer = RandomUtils.getRandomValue(0, populationSize);
            newSelection.add(chr.getFitnessFunctionValue() > chromosomeList.get(secondPlayer).getFitnessFunctionValue() ? chr : chromosomeList.get(secondPlayer));
        }
        return newSelection;
    }

    @Override
    public IChromosome mutation(IChromosome chromosome, int probability) {
        int[] chrValue = (int[]) chromosome.getChromosomeValue();
        int[] newChrValue = new int[chrValue.length];
        boolean mutated = false;
        for (int i = 0; i < chrValue.length; i += ChessBoard.GEN_SIZE) {
            if (RandomUtils.makeChanges(probability)) {
                newChrValue[i] = RandomUtils.getRandomValue(0, chrValue.length / ChessBoard.GEN_SIZE);
                newChrValue[i + 1] = RandomUtils.getRandomValue(0, chrValue.length / ChessBoard.GEN_SIZE);
                mutated = true;
            } else {
                newChrValue[i] = chrValue[i];
                newChrValue[i + 1] = chrValue[i + 1];
            }
        }
        return mutated ? new IntegerChromosome(newChrValue) : null;
    }


    @Override
    public List<IChromosome> crossover(IChromosome first, IChromosome second) {
        List<IChromosome> newList = new ArrayList<>();
        int[] firstValue = (int[]) first.getChromosomeValue();
        int[] secondValue = (int[]) second.getChromosomeValue();
        int[] newChromosomeValue1 = new int[firstValue.length];
        int[] newChromosomeValue2 = new int[firstValue.length];
        int randomValue1 = RandomUtils.getRandomValue(0, firstValue.length / ChessBoard.GEN_SIZE);
        int randomValue2 = RandomUtils.getRandomValue(0, firstValue.length / ChessBoard.GEN_SIZE);
        for (int i = 0, j = min(randomValue1, randomValue2) * ChessBoard.GEN_SIZE; i <= max(randomValue1, randomValue2) - min(randomValue1, randomValue2); i++, j += ChessBoard.GEN_SIZE) {
            newChromosomeValue1[j] = secondValue[j];
            newChromosomeValue1[j + 1] = secondValue[j + 1];

            newChromosomeValue2[j] = firstValue[j];
            newChromosomeValue2[j + 1] = firstValue[j + 1];
        }
        newList.add(new IntegerChromosome(newChromosomeValue1).getFitnessFunctionValue() > new IntegerChromosome(newChromosomeValue2).getFitnessFunctionValue() ? new IntegerChromosome(newChromosomeValue1) : new IntegerChromosome(newChromosomeValue2));
        newList.add(first.getFitnessFunctionValue() > second.getFitnessFunctionValue() ? first : second);
        return newList;
    }

    @Override
    public List<IChromosome> generatePopulation(GaParameters parameters) {
        return ChessBoard.generateStartPopulation(parameters.getPopulationSize(), parameters.getN());
    }

    @Override
    public List<IChromosome> run(GaParameters params) {
        List<IChromosome> population = generatePopulation(params);
        int ffValue = 0;
        int iterationCount = 0;
        //TODO  quasi-optimum not implemented
        while (params.getN() > ffValue) {
            iterationCount++;
            List<IChromosome> populationToCross = new ArrayList<>();
            for (int i = 0; i < params.getPopulationSize(); i++) {
                if (RandomUtils.makeChanges(params.getCrossoverProbability())) {
                    populationToCross.add(population.get(i));
                    if (populationToCross.size() > 1) {
                        population.addAll(crossover(populationToCross.get(0), populationToCross.get(1)));
                        population.remove(populationToCross.get(0));
                        population.remove(populationToCross.get(1));
                        populationToCross.clear();
                    }
                }
            }
            int populationCount = population.size();
            for (int i = 0; i < populationCount; i++) {
                IChromosome mutated = mutation(population.get(i), params.getMutationProbability());
                if (mutated != null) {
                    population.add(mutated);
                    population.remove(population.get(i));
                }

            }
            population = new ArrayList<>(selection(population, params.getPopulationSize()));
            ffValue = ChessBoard.getMaxFitnessValue(population);
            iterationData.add(ffValue);
            if (ffValue > lastMaxFFvalue) {
                lastMaxFFvalue = ffValue;
                LOGGER.info("Max fitness function value = " + String.valueOf(ffValue) + ", on " + iterationCount + " iteration");
            }
        }
        DataExport.exportIterationData(iterationData, new File("iterationData.txt"));
        return ChessBoard.getChromosomeListWithMaxFF(population, params.getN());
    }

    public List<IChromosome> crossoverWithScope(IChromosome first, IChromosome second, int min, int max) {
        List<IChromosome> newList = new ArrayList<>();
        int[] firstValue = (int[]) first.getChromosomeValue();
        int[] secondValue = (int[]) second.getChromosomeValue();
        int[] newChromosomeValue1 = new int[firstValue.length];
        int[] newChromosomeValue2 = new int[firstValue.length];
        for (int i = 0; i < firstValue.length; i++) {
            newChromosomeValue1[i] = firstValue[i];
            newChromosomeValue2[i] = secondValue[i];
        }
        if (min > 0) {
            for (int i = min; i < max; i++) {
                newChromosomeValue1[i * ChessBoard.GEN_SIZE] = secondValue[i * ChessBoard.GEN_SIZE];
                newChromosomeValue1[i * ChessBoard.GEN_SIZE + 1] = secondValue[i * ChessBoard.GEN_SIZE + 1];

                newChromosomeValue2[i * ChessBoard.GEN_SIZE] = firstValue[i * ChessBoard.GEN_SIZE];
                newChromosomeValue2[i * ChessBoard.GEN_SIZE + 1] = firstValue[i * ChessBoard.GEN_SIZE + 1];
            }
        } else {
            for (int i = min; i < max; i++) {
                if (i == 0) {
                    newChromosomeValue1[i] = secondValue[i];
                    newChromosomeValue1[i + 1] = secondValue[i + 1];

                    newChromosomeValue2[i] = firstValue[i];
                    newChromosomeValue2[i + 1] = firstValue[i + 1];
                } else {
                    newChromosomeValue1[i * ChessBoard.GEN_SIZE] = secondValue[i * ChessBoard.GEN_SIZE];
                    newChromosomeValue1[i * ChessBoard.GEN_SIZE + 1] = secondValue[i * ChessBoard.GEN_SIZE + 1];

                    newChromosomeValue2[i * ChessBoard.GEN_SIZE] = firstValue[i * ChessBoard.GEN_SIZE];
                    newChromosomeValue2[i * ChessBoard.GEN_SIZE + 1] = firstValue[i * ChessBoard.GEN_SIZE + 1];
                }
            }
        }
        newList.add(new IntegerChromosome(newChromosomeValue1));
        newList.add(new IntegerChromosome(newChromosomeValue2));
        return newList;
    }


    public int[] mutationWithScope(IChromosome chromosome, int probability, int x, int y) {
        int[] chrValue = (int[]) chromosome.getChromosomeValue();
        int[] newChrValue = new int[chrValue.length];

        for (int i = 0; i < chrValue.length; i += 2) {
            if (RandomUtils.makeChanges(probability)) {
                newChrValue[i] = x;
                newChrValue[i + 1] = y;
            } else {
                newChrValue[i] = 0;
                newChrValue[i + 1] = 0;
            }
        }
        return newChrValue;
    }
}
