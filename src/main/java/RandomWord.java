import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String champion = null;
        var n = 0;
        while (!StdIn.isEmpty()) {
            n++;
            var candidate = StdIn.readString();
            if (StdRandom.bernoulli((double) 1 / n)) {
                champion = candidate;
            }
        }
        if (champion != null) {
            StdOut.println(champion);
        }
    }
}
