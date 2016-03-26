package test;

import impl.IntegerChromosome;
import model.ChessBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vitalij on 27/02/2016.
 */
public class TestFitnessFunction {

    @Test
    public void testFF0() {
        Random rnd = new Random();
        int[] chrValue = new int[8];
        chrValue = new int[]{0, 0, 2, 1, 3, 3, 3, 1};
        IntegerChromosome chromosome = new IntegerChromosome(chrValue);
        Assert.assertEquals(0, chromosome.getFitnessFunctionValue());
    }

    @Test
    public void testFF1() {
        Random rnd = new Random();
        int[] chrValue = new int[8];
        chrValue = new int[]{0, 0, 2, 1, 3, 2, 1, 2};
        IntegerChromosome chromosome = new IntegerChromosome(chrValue);
        Assert.assertEquals(1, chromosome.getFitnessFunctionValue());
    }

    @Test
    public void testFF2() {
        Random rnd = new Random();
        int[] chrValue = new int[10];
        chrValue = new int[]{2, 0, 0, 3, 3, 2, 3, 4, 4, 4};
        IntegerChromosome chromosome = new IntegerChromosome(chrValue);
        Assert.assertEquals(2, chromosome.getFitnessFunctionValue());
    }
    @Test
    public void testFF4() {
        int[] chrValue = new int[]{0,1,1,3,2,0,3,2};
        IntegerChromosome chromosome = new IntegerChromosome(chrValue);
        assertEquals(4, chromosome.getFitnessFunctionValue());
    }
    @Test
    public void testFF8() {
        Random rnd = new Random();
        int[] chrValue = new int[16];
        chrValue = new int[]{4, 0, 0, 1, 3, 2, 5, 3, 7, 4, 1, 5, 6, 6, 2, 7};
        IntegerChromosome chromosome = new IntegerChromosome(chrValue);
        Assert.assertEquals(8, chromosome.getFitnessFunctionValue());
    }

}
