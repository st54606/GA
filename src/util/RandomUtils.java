package util;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Vitalij on 28/02/2016.
 */
public class RandomUtils {
    private static Logger LOGGER = Logger.getLogger(RandomUtils.class.getName());

    public static boolean makeChanges(int probability) {
        Random rnd = new Random();
        int prob = rnd.nextInt((100 - 0) + 0);
        return prob <= probability;
    }

    public static int getRandomValue(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 0);
    }

}
