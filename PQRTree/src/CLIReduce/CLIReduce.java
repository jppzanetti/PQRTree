package CLIReduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import pqrtree.PQRTree;

/**
 * A simple example of a CLI application that builds incrementally a PQR tree.
 * 
 * @author Joao
 */
public class CLIReduce {

    public static void main(String[] args) throws IOException {
        // Setting up input from stdin
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        // Array that will hold the restriction from the input
        int tmpRestriction[] = null;

        // Create initial universal tree
        Scanner scanner = new Scanner(in.readLine());
        int elementNumber = scanner.nextInt();
        PQRTree pqrtree = new PQRTree(elementNumber);
        System.out.println(pqrtree.toString());

        // Read restrictions until empty line
        String line = in.readLine();
        while (!line.isEmpty()) {
            Scanner scanLine = new Scanner(line);
            int restriction[] = null;
            int i = 0;

            // Get elements of restriction
            tmpRestriction = new int[elementNumber];
            while (scanLine.hasNextInt()) {
                tmpRestriction[i++] = scanLine.nextInt();
            }

            // Copy tmpRestriction to restriction
            restriction = new int[i];
            for (int j = 0; j < i; j++) {
                restriction[j] = tmpRestriction[j];
            }

            // Reduce tree and print
            pqrtree.reduce(restriction);
            System.out.println(pqrtree.toString());

            // Read next line
            line = in.readLine();
        }
    }
    
}
