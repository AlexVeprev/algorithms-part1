import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] arr;
    private int numOfSegments = 0;
    private LineSegment[] segments = new LineSegment[0];

    public BruteCollinearPoints(Point[] points) {
        arr = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException();

            if (Arrays.asList(arr).contains(points[i]))
                throw new IllegalArgumentException();

            arr[i] = points[i];
        }

        for (int i0 = 0; i0 < arr.length - 3; i0++) {
            for (int i1 = i0 + 1; i1 < arr.length - 2; i1++) {
                for (int i2 = i1 + 1; i2 < arr.length - 1; i2++) {
                    if (arr[i0].slopeTo(arr[i1]) != arr[i0].slopeTo(arr[i2])) {
                        continue;
                    }

                    for (int i3 = i2 + 1; i3 < arr.length; i3++) {
                        if (arr[i0].slopeTo(arr[i1]) == arr[i0].slopeTo(arr[i3])) {
                            newSegment(new Point[]{arr[i0], arr[i1], arr[i2], arr[i3]});
                        }
                    }
                }
            }
        }
    }

    private void newSegment(Point[] points) {
        Point minP = points[0];
        Point maxP = points[0];

        for (Point p : points) {
            if (p.compareTo(minP) < 0) {
                minP = p;
                continue;
            }

            if (p.compareTo(maxP) > 0) {
                maxP = p;
                continue;
            }
        }

        resizeLsArray(numOfSegments + 1);

        LineSegment ls = new LineSegment(minP, maxP);
        segments[numOfSegments++] = ls;
    }

    private void resizeLsArray(int size) {
        LineSegment[] newLsArray = new LineSegment[size];

        for (int i = 0; i < segments.length; i++) {
            newLsArray[i] = segments[i];
        }

        segments = newLsArray;
    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }
}