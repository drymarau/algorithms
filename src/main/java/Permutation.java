import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        var k = Integer.parseInt(args[0]);
        if (k <= 0) {
            return;
        }
        var queue = new RandomizedQueue<String>();
        var n = 0;
        while (!StdIn.isEmpty()) {
            n++;
            var item = StdIn.readString();
            if (queue.size() < k) {
                queue.enqueue(item);
            } else if (StdRandom.uniformInt(0, n) < k) {
                queue.dequeue();
                queue.enqueue(item);
            }
        }
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}
