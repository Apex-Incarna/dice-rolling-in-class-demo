package edu.guilford;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DiceRolling {
    public static void main(String[] args) {
        // We need a Random object when we roll dice
        Random rand = new Random();
        // Let's also define how many dice we have and how many faces each die has
        final int NDICE = 100;
        final int NFACES = 10000;
        // *final* means that the program cannot change the value
        // use that for constants; and use identifiers with all capital letters

        // declare and create an array of dice values
        int[] dieValues = new int[NDICE];

        // but now we want random values for each die; use a counting loop
        // this is another way of initializing values in an array
        // using an algorithm
        int index = 0;
        while (index < NDICE) { // while index is an allowed value
            dieValues[index] = rand.nextInt(NFACES) + 1;
            index = index + 1;
        }

        // Let's see the results using a Java utility class Arrays
        System.out.println(Arrays.toString(dieValues));

        // Find the maximum value in the array
        // Set the max value to the first value in the array
        int maxValue = dieValues[0];

        // Loop over each element in the array
        // If the value of that element is larger, then set the max value
        // to that new larger value; use a for-each loop
        for (int value : dieValues) { // loop over each element and store in value
            if (value > maxValue) {
                maxValue = value;
            }
        }

        // When the loop is done, print out the result
        System.out.println("The maximum value is " + maxValue);

        // Build a histogram
        final int NBINS = 10;
        // We need a count array with that number of bins
        int[] count = new int[NBINS];

        // We need to know how big each bin is
        int binSize = NFACES / NBINS;
        // This works well if NBINS evenly divides NFACES

        // Use a for-each loop to look at each die value and assign it to
        // the appropriate bin
        for (int value : dieValues) {
            int bin = (value - 1) / binSize; // this is the bin the value goes in
            // Example: value = 83; (value - 1) = 82; 82 / 100 = 8 in Java
            count[bin] = count[bin] + 1; // add 1 to the bin value belongs to
        }

        // Print out the result
        System.out.println("Histogram: " + Arrays.toString(count));

        // Now let's work with files!

        // Writing to a file with a FileWriter object
        // First, we define a File object with the name of the file
        File file = new File("histogram.txt");

        // Instantiate a FileWriter object that has the task of writing to that file
        try { // This is a try-catch block
              // Try this code to see if it runs without serious error
            FileWriter writer = new FileWriter(file, true); // By default, FileWRiter objects overwrite what's in the
                                                            // file we created; using "true" as the second parameter
                                                            // prevents this and instead appends any new data we try to
                                                            // write
            writer.write("Bin\tHistogram\n"); // This line writes the strings we pass to it to the file

            // Let's write out the values in the count array using a while loop
            int bin = 0;
            while (bin < count.length) {
                writer.write(bin + "\t" + count[bin] + "\n");
                bin++;
            }

            writer.close(); // We have to close the file to flush out everything we wrote to the file
        } catch (Exception e) {
            // Do this stuff if specific exception occurs
            e.printStackTrace();
        }

        // To read text files that have tokens or lines, we use Scanner
        // Instantiate a Scanner object that is connected to the file
        try {
            Scanner scan = new Scanner(file);

            // We want to read the lines in the file and print them out as long as the file
            // still has data in it
            // For a file consisting of lines of data, we use the hasNextLine method of
            // Scanner, which returns true if there is another line in the file and false if
            // not
            while (scan.hasNextLine()) {
                String theLine = scan.nextLine();
                String[] theLineArray = theLine.split("\t");
                System.out.println(Arrays.toString(theLineArray));
            }
            scan.close(); // We want to make sure we close scan once we're done with it
        } catch (FileNotFoundException e) { // Notice how this exception is different from the one in the try-catch
                                            // block above; the specific exception this try-catch block is worried about
                                            // is if the file we're trying to read from cannot be found
            e.printStackTrace();
        }

    }
}