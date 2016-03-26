package ga;

import model.GaParameters;

import java.util.List;

/**
 * Created by Vitalij on 27/02/2016.
 */
public interface IGeneticAlgorithm {

    List<IChromosome> selection(List<IChromosome> chromosomeList, int populationSize);

    IChromosome mutation(IChromosome chromosome, int probability);

    List<IChromosome> crossover(IChromosome first,IChromosome second);

    List<IChromosome> generatePopulation(GaParameters parameters);

    List<IChromosome> run (GaParameters params);
}
