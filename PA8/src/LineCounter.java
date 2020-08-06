/*
 * NAME: Milon Lonappan
 * PID: Milon Lonappan
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Milon Lonappan
 * @since
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;

    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable

        for (int i = 0; i < numArgs; i++) {

            HashTable newhash = new HashTable();
            File file = new File(args[i]);
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String read = scanner.nextLine();
                    newhash.insert(read);
                }
                scanner.close();
                tableList[i] = newhash;
            }
            catch (Exception ignored) {

            }
        }

        // Find similarities across files

        for (int i = 0; i < numArgs; i++) {

            printFileName(args[i]);
            for (int y = 0; y< numArgs; y++) {
                double countSim = 0;
                double numLines = 0;
                HashTable current = tableList[y];
                File file = new File(args[i]);
                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        numLines++;
                        String read = scanner.nextLine();
                        if (current.lookup(read)){
                            countSim++;
                        }
                    }
                    scanner.close();
                    int percent = (int)(countSim / numLines * 100);
                    if (y != i){
                        printStatistics(args[y], percent);
                    }
                }
                catch (Exception ignored){

                }
            }

        }
    }

}