package test;

import ga.IChromosome;
import ga.IGeneticAlgorithm;
import impl.IntegerChromosome;
import impl.SimpleGeneticAlgorithm;
import model.ChessBoard;
import model.GaParameters;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by vilo on 02.03.2016..
 */
public class TestGa {
    private static Logger LOGGER = Logger.getLogger(TestGa.class.getName());

    @Test
    public void testSelection() {
        List<IChromosome> population = ChessBoard.generateStartPopulation(10, 4);
        IGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        List<IChromosome> pop = ga.selection(population, 10);
        assertEquals(10, pop.size());
    }

    @Test
    public void crossoverWithScope() {
        IGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        int[] chrValue1 = new int[]{2, 0, 2, 1, 3, 3, 3, 1};
        IntegerChromosome first = new IntegerChromosome(chrValue1);
        int[] chrValue2 = new int[]{0, 2, 3, 1, 3, 2, 1, 2};
        IntegerChromosome second = new IntegerChromosome(chrValue2);
        SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm();
        List<IChromosome> chrList = simpleGeneticAlgorithm.crossoverWithScope(first, second, 0, 3);
        IChromosome newFirst = new IntegerChromosome((int[]) chrList.get(0).getChromosomeValue());
        IChromosome newSecond = new IntegerChromosome((int[]) chrList.get(1).getChromosomeValue());

        int[] new1ChromosomeValue = (int[]) newFirst.getChromosomeValue();
        int[] new2ChromosomeValue = (int[]) newSecond.getChromosomeValue();

        int[] expected1 = new int[]{0, 2, 3, 1, 3, 2, 3, 1};
        int[] expected2 = new int[]{2, 0, 2, 1, 3, 3, 1, 2};

        assertArrayEquals(expected1, new1ChromosomeValue);
        assertArrayEquals(expected2, new2ChromosomeValue);

    }

    @Test
    public void testMutation() {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        int[] chrValue1 = new int[]{3, 0, 2, 1, 3, 3, 3, 1};
        IntegerChromosome first = new IntegerChromosome(chrValue1);
        int[] expected1 = new int[]{3, 1, 3, 1, 3, 1, 3, 1};
        assertArrayEquals(expected1, ga.mutationWithScope(first, 100, 3, 1));

        int[] chrValue2 = new int[]{2, 0, 0, 3, 3, 2, 3, 4};
        IntegerChromosome second = new IntegerChromosome(chrValue2);
        int[] expected2 = new int[]{0, 1, 0, 1, 0, 1, 0, 1};
        assertArrayEquals(expected2, ga.mutationWithScope(second, 100, 0, 1));
    }

    @Test
    public void testGaPopulationGeneration() {
        IGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        GaParameters params = new GaParameters(4, 70, 50, 8, false);
        List<IChromosome> list = ga.generatePopulation(params);
        assertEquals(params.getPopulationSize(), list.size());
        int[] iChrValue = (int[]) list.get(0).getChromosomeValue();
        assertEquals(params.getN(), iChrValue.length / ChessBoard.GEN_SIZE);
    }

    @Test
    public void testGa() {
        GaParameters params = new GaParameters(80, 70, 4, 8, false);
        IGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        long start = System.currentTimeMillis();
        List<IChromosome> result = ga.run(params);
        long end = System.currentTimeMillis();
        long spentTime = end - start;
        for (IChromosome chr : result) {
            ChessBoard.printChromosome(chr);
        }
        LOGGER.info("Finished! " + "Time spent : " + spentTime + " milliseconds " + "(" + spentTime / 1000 + " seconds" + ")");
    }
}
