package model;

import ga.IChromosome;
import impl.IntegerChromosome;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vitalij on 27/02/2016.
 */
public class ChessBoard {
    private static Logger LOGGER = Logger.getLogger(ChessBoard.class.getName());
    public static final int GEN_SIZE = 2;

    public static int calculateFitnessFunction(int[] chromosoveValue) {
        int offset;
        int ffValue = 0;
        int hitCount = -1;
        int tableSize = chromosoveValue.length / GEN_SIZE;

        for (int i = 0; i < tableSize * GEN_SIZE; i += GEN_SIZE) {
            offset = 0;
            for (; offset < chromosoveValue.length; offset += GEN_SIZE) {
                if (ChessBoard.queenHits(chromosoveValue[i], chromosoveValue[i + 1], chromosoveValue[offset], chromosoveValue[offset + 1], tableSize)) {
                    hitCount++;
                } else {
                }
            }
            if (hitCount == 0) {
                ffValue++;
            }
            hitCount = -1;
        }
        return ffValue;
    }

    private static boolean queenHits(int q1x, int q1y, int q2x, int q2y, int tableSize) {
        if (queenHitsHorOrVert(q1x, q1y, q2x, q2y, tableSize)) {
            return true;
        }
        if (queenHitsOnLeftDiag(q1x, q1y, q2x, q2y, tableSize)) {
            return true;
        }
        if (queenHitsOnRightDiag(q1x, q1y, q2x, q2y, tableSize)) {
            return true;
        }
        return false;
    }

    public static boolean queenHitsOnLeftDiag(int q1x, int q1y, int q2x, int q2y, int tableSize) {
        int startOfLRDiagX = q1x < q1y ? 0 : q1x - q1y;
        int startOfLRDiagY = q1x > q1y ? 0 : q1y - q1x;
        for (; startOfLRDiagX < tableSize; startOfLRDiagX++, startOfLRDiagY++) {
            if (startOfLRDiagX == q2x && startOfLRDiagY == q2y) {
                return true;
            }
        }
        return false;
    }

    public static boolean queenHitsHorOrVert(int q1x, int q1y, int q2x, int q2y, int tableSize) {
        //vertical
        for (int x = q1x, y = 0; y < tableSize; y++) {
            if (x == q2x && y == q2y) {
                return true;
            }
        }
        // horizontal
        for (int y = q1y, x = 0; x < tableSize; x++) {
            if (x == q2x && y == q2y) {
                return true;
            }
        }
        return false;
    }

    protected static boolean queenHitsOnRightDiag(int q1x, int q1y, int q2x, int q2y, int tableSize) {
        for (int x = q1x, y = q1y; y >= 0 && x < tableSize; y--, x++) {
            if (x == q2x && y == q2y) {
                return true;
            }
        }
        for (int x = q1x, y = q1y; x >= 0 && y < tableSize; y++, x--) {
            if (x == q2x && y == q2y) {
                return true;
            }
        }
        return false;
    }

    public static List<IChromosome> generateStartPopulation(int populationSize, int queensCount) {
        List<IChromosome> list = new ArrayList<IChromosome>();
        int genSize = 2;
        int chromosomeSize = genSize * queensCount;
        Random rnd = new Random();
        for (int i = 0; i < populationSize; i++) {
            int[] chrValue = new int[chromosomeSize];
            for (int j = 0; j < chromosomeSize; j++) {
                chrValue[j] = rnd.nextInt((queensCount - 1) + 1);
            }
            list.add(new IntegerChromosome(chrValue));
        }
        return list;
    }

    public static int getMaxFitnessValue(List<IChromosome> population) {
        int maxValue = 0;
        for (int i = 0; i < population.size(); i++) {
            if (maxValue < population.get(i).getFitnessFunctionValue()) {
                maxValue = population.get(i).getFitnessFunctionValue();
            }
        }
        return maxValue;
    }

    public static List<IChromosome> getChromosomeListWithMaxFF(List<IChromosome> list, int maxValue) {
        List<IChromosome> newList = new ArrayList<>();
        for(IChromosome chr : list){
            if(chr.getFitnessFunctionValue()==maxValue && !newList.contains(chr)){
                newList.add(chr);
            }
        }
        return newList;
    }

    public static void printChromosome(IChromosome chromosome) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Resutlt(s): ");
        int[] value = (int[]) chromosome.getChromosomeValue();
        for (int i = 0; i < value.length; i++) {
            buffer.append(value[i]);
            buffer.append(" ");
            if (i % 2 == 1 && i > 0) {
                buffer.append("; ");
            }
        }
        LOGGER.info(buffer.toString());
    }

    public static String convertIntegerArrayToString(int[] array, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(array[i]);
            if (i % 1 == 0 && i % 2 != 1) {
                buffer.append(",");
            }
            if (i % 2 == 1 && i > 0 && i < array.length - 1) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
}