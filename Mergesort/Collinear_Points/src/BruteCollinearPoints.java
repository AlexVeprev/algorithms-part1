import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] points;
    private int numOfSegments = 0;
    private LineSegment[] segments = new LineSegment[1];

    private void newSegment(int[] pointIds) {
        int minId = pointIds[0];
        int maxId = pointIds[0];

        for (int i : pointIds) {
            if (points[i].compareTo(points[minId]) < 0) {
                minId = i;
                continue;
            }

            if (points[i].compareTo(points[maxId]) > 0) {
                maxId = i;
                continue;
            }
        }

        if (segments.length == numOfSegments) {
            resizeLsArray(numOfSegments + 1);
        }

        LineSegment ls = new LineSegment(points[minId], points[maxId]);
        segments[numOfSegments++] = ls;
    }

    private void resizeLsArray(int size) {
        LineSegment[] newLsArray = new LineSegment[size];

        for (int i = 0; i < segments.length; i++) {
            newLsArray[i] = segments[i];
        }

        segments = newLsArray;
    }

    public BruteCollinearPoints(Point[] points) {
        this.points = points;

        for (int i0 = 0; i0 < points.length - 3; i0++) {
            for (int i1 = i0 + 1; i1 < points.length - 2; i1++) {
                for (int i2 = i1 + 1; i2 < points.length - 1; i2++) {
                    if (points[i0].slopeTo(points[i1]) != points[i0].slopeTo(points[i2])) {
                        continue;
                    }

                    for (int i3 = i2 + 1; i3 < points.length; i3++) {
                        if (points[i0].slopeTo(points[i1]) == points[i0].slopeTo(points[i3])) {
                            newSegment(new int[]{i0, i1, i2, i3});
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }
}