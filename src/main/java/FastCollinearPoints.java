import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final LinkedList<LineSegment> lineSegments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
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
        var slopeOrderPoints = Arrays.copyOf(points, points.length);
        for (var point : points) {
            Arrays.sort(slopeOrderPoints, point.slopeOrder().thenComparing(Comparator.naturalOrder()));
            if (slopeOrderPoints[0] != point) continue;
            var i = 1;
            while (i < slopeOrderPoints.length - 2) {
                var slope = point.slopeTo(slopeOrderPoints[i]);
                var j = i + 1;
                while (j < slopeOrderPoints.length && slope == point.slopeTo(slopeOrderPoints[j])) {
                    j++;
                }
                if (j - i > 2) {
                    var auxPoints = new Point[j - i + 1];
                    auxPoints[0] = point;
                    System.arraycopy(slopeOrderPoints, i, auxPoints, 1, j - i);
                    Arrays.sort(auxPoints);
                    var minPoint = auxPoints[0];
                    var maxPoint = auxPoints[auxPoints.length - 1];
                    if (point == minPoint && slopeOrderPoints[j - 1] == maxPoint) {
                        lineSegments.add(new LineSegment(minPoint, maxPoint));
                    }
                }
                do {
                    i++;
                } while (i < length && slope == point.slopeTo(slopeOrderPoints[i]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
