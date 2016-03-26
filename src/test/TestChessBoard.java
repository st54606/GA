package test;

import ga.IChromosome;
import impl.IntegerChromosome;
import model.ChessBoard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vilo on 03.03.2016..
 */
public class TestChessBoard {
    @Test
    public void testPopulationGeneration() {
        List<IChromosome> list = ChessBoard.generateStartPopulation(5, 4);
        assertEquals(5, list.size());
        int[] value = (int[]) list.get(0).getChromosomeValue();
        assertEquals(8, value.length);
    }

    @Test
    public void testMaxFitnessValue() {
        int[] chrValue0 = new int[]{0, 0, 2, 1, 3, 3, 3, 1};
        IChromosome chromosome0 = new IntegerChromosome(chrValue0);

        int[] chrValue1 = new int[]{0, 0, 2, 1, 3, 2, 1, 2};
        IChromosome chromosome1 = new IntegerChromosome(chrValue1);

        int[] chrValue2 = new int[]{2, 0, 0, 3, 3, 2, 3, 4, 4, 4};
        IChromosome chromosome2 = new IntegerChromosome(chrValue2);
        List<IChromosome> population = new ArrayList<>();
        population.add(chromosome0);
        population.add(chromosome1);
        population.add(chromosome2);
        int maxValue = ChessBoard.getMaxFitnessValue(population);
        assertEquals(2, maxValue);
    }

    @Test
    public void testLeftDiag() {
        boolean res = ChessBoard.queenHitsOnLeftDiag(0, 1, 3, 2, 8);
        assertFalse(res);
    }

    @Test
    public void testLeftDiagHit() {
        boolean res = ChessBoard.queenHitsOnLeftDiag(0, 1, 5, 6, 8);
        assertTrue(res);
    }

    @Test
    public void testRightDiag() {
        boolean res = ChessBoard.queenHitsOnLeftDiag(0, 1, 3, 2, 8);
        assertFalse(res);
    }

    @Test
    public void testRightDiagHit() {
        boolean res = ChessBoard.queenHitsOnLeftDiag(6, 6, 7, 7, 8);
        assertTrue(res);
    }

    @Test
    public void testIntArrayToStringFormater() {
        int[] chrValue0 = new int[]{0, 0, 2, 1, 3, 3, 3, 1};
        String resutl = ChessBoard.convertIntegerArrayToString(chrValue0, ";");
        assertEquals(resutl,"0,0;2,1;3,3;3,1");
        System.out.printf(resutl);

    }


}
