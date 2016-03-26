import ga.IChromosome;
import ga.IGeneticAlgorithm;
import impl.SimpleGeneticAlgorithm;
import model.ChessBoard;
import model.GaParameters;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter crossover probability");
        int crossoverProability = scanner.nextInt();
        System.out.println("Enter queen count");
        int queenCount = scanner.nextInt();
        GaParameters gaParameters = new GaParameters(queenCount * 10, crossoverProability, queenCount / 2, queenCount, false);
        IGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        long start = System.currentTimeMillis();
        List<IChromosome> result = ga.run(gaParameters);
        for (IChromosome chr : result) {
            ChessBoard.printChromosome(chr);
        }
        long end = System.currentTimeMillis();
        long spentTime = end - start;
        System.out.println("Finished! " + "Time spent : " + spentTime + " milliseconds " + "(" + spentTime / 1000 + " seconds" + ")");
    }
}
