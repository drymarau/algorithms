import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final LinkedList<LineSegment> lineSegments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null.");
        }
        var length = points.length;
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("A point is null.");
            }
        }
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("A duplicate point.");
                }
            }
        }
        var copyOfPoints = Arrays.copyOf(points, length);
        Arrays.sort(copyOfPoints);
        for (int p = 0; p < length - 3; p++) {
            for (int q = p + 1; q < length - 2; q++) {
                for (int r = q + 1; r < length - 1; r++) {
                    for (int s = r + 1; s < length; s++) {
                        var pp = copyOfPoints[p];
                        var pq = copyOfPoints[q];
                        var pr = copyOfPoints[r];
                        var ps = copyOfPoints[s];
                        if (pp.slopeTo(pq) != pp.slopeTo(pr)) continue;
                        if (pp.slopeTo(pq) != pp.slopeTo(ps)) continue;
                        lineSegments.add(new LineSegment(pp, ps));
                    }
                }
            }

        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
