package CLIReduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import pqrtree.PQRTree;

/**
 * A simple example of a CLI application that builds incrementally a PQR tree.
 * 
 * The first line of the input should be an integer setting the number of
 * elements to be considered. After that, each line adds a constraint to the
 * PQR-Tree, with the elements separated by spaces. The tree is updated and
 * 
 * Example (input lines are preceded with a '>'):
 * > 6
 * (0 1 2 3 4 5)
 * > 0 1 2
 * (3 4 5 (0 1 2))
 * > 2 3 4
 * (5 [(3 4) 2 (0 1)])
 * >
 * 
 * @author Joao
 */
public class CLIReduce {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int tmpRestriction[] = null;

        // Create initial universal tree
        int elementNumber = scanner.nextInt();
        PQRTree pqrtree = new PQRTree(elementNumber);
        System.out.println(pqrtree.toString());

        // Read restrictions until empty line
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        while (!line.isEmpty()) {
            Scanner scanLine = new Scanner(line);
            int restriction[] = null;
            int i = 0;

            tmpRestriction = new int[elementNumber];
            while (scanLine.hasNextInt()) {
                tmpRestriction[i++] = scanLine.nextInt();
            }

            restriction = new int[i];
            for (int j = 0; j < i; j++) {
                restriction[j] = tmpRestriction[j];
            }

            pqrtree.reduce(restriction);
            System.out.println(pqrtree.toString());

            line = in.readLine();
        }
    }
    
}
