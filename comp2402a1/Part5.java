 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
class comp implements Comparator<String> {
  public int compare(String o1, String o2) {
    if (o1.length() > o2.length()) {
      return 1;
    } else if (o1.length() < o2.length()) {
      return -1;
    } else {
      return 0;
    }
  }
}

public class Part5 {
    
    /**
     * Your code goes here - see Part0 for an example
     * @param r the reader to read from
     * @param w the writer to write to
     * @throws IOException
     */
    public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
        List<String> s = new ArrayList<String>();
        
        for(String line = r.readLine(); line != null; line = r.readLine())
        {
            s.add(line);
        }
        //String[] array = s.toArray(new String[0]);
        Collections.sort(s);
        Collections.sort(s,new comp());
        for(String text:s)
        {
            w.println(text);
        }
    }

    /**
     * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
     * and System.out or from filenames specified on the command line, then call doIt.
     * @param args
     */
    public static void main(String[] args) {
        try {
            BufferedReader r;
            PrintWriter w;
            if (args.length == 0) {
                r = new BufferedReader(new InputStreamReader(System.in));
                w = new PrintWriter(System.out);
            } else if (args.length == 1) {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(System.out);                
            } else {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(new FileWriter(args[1]));
            }
            long start = System.nanoTime();
            doIt(r, w);
            w.flush();
            long stop = System.nanoTime();
            System.out.println("Execution time: " + 10e-9 * (stop-start));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
}
