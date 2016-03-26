package io;

import impl.IntegerChromosome;
import model.ChessBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by St54606 on 2016.03.19..
 */
public class DataExport {

    public static void exportChromosome(IntegerChromosome data, File file) {
        try {
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            out.print(ChessBoard.convertIntegerArrayToString((int[]) data.getChromosomeValue(), ";"));
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportIterationData(List<Integer> list, File file) {
        try {
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            int i = 1;
            for (Integer integer : list) {
                out.println(i++ + " " + integer);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

