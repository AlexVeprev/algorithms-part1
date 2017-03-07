import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SampleClient {
    public static void main(String[] args) {

        // read the n points from a file
        /*
        In in = new In(args[0]);
        int n = in.readInt();
         */
        int n = 30;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            //int x = in.readInt();
            //int y = in.readInt();
            int x = 32768 / (i + 1);
            int y = 32768 / (i + 1);
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        /*
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }*/
        StdDraw.show();
    }
}
