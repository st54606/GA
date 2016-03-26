package test;

import impl.IntegerChromosome;
import io.DataExport;
import model.ChessBoard;
import org.junit.Test;
import util.RandomUtils;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vitalij on 28/02/2016.
 */
public class TestUtils {

    @Test
    public void testProbability() {
        assertFalse(RandomUtils.makeChanges(0));
        assertTrue(RandomUtils.makeChanges(100));
    }

    @Test
    public void testDataExport() {
        int[] chrValue0 = new int[]{0, 0, 2, 1, 3, 3, 3, 1};
        String resutl = ChessBoard.convertIntegerArrayToString(chrValue0, ";");
        File file = new File("result.txt");
        DataExport.exportChromosome(new IntegerChromosome(chrValue0), file);
        assertTrue(file.exists());
    }

}
