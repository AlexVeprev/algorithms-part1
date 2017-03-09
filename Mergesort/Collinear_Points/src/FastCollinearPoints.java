import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] arr;
    private int numOfSegments = 0;
    private LineSegment[] segments = new LineSegment[0];

    public FastCollinearPoints(Point[] points) {
        arr = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException();

            if (Arrays.asList(arr).contains(points[i]))
                throw new IllegalArgumentException();

            arr[i] = points[i];
        }

        findSegments();
    }

    private void findSegments() {
        for (int i = 0; i < arr.length - 3; i++) {
            Arrays.sort(arr);
            Arrays.sort(arr, i + 1, arr.length, arr[i].slopeOrder());

            int collPoints = 2;
            double currentSlope = arr[i].slopeTo(arr[i + 1]);
            for (int j = i + 2; j < arr.length; j++) {
                if (arr[i].slopeTo(arr[j]) == currentSlope) {
                    collPoints++;
                }
                else {
                    if (collPoints > 3) {
                        LineSegment ls = new LineSegment(arr[i], arr[j - 1]);
                        resizeLsArray(numOfSegments + 1);
                        segments[numOfSegments++] = ls;
                    }
                    currentSlope = arr[i].slopeTo(arr[j]);
                    collPoints = 2;
                }
            }

            if (collPoints > 3) {
                LineSegment ls = new LineSegment(arr[i], arr[arr.length - 1]);
                resizeLsArray(numOfSegments + 1);
                segments[numOfSegments++] = ls;
            }
        }
    }

    private void resizeLsArray(int size) {
        LineSegment[] newLsArray = new LineSegment[size];

        for (int i = 0; i < segments.length; i++) {
            newLsArray[i] = segments[i];
        }

        segments = newLsArray;
    }

    public int numberOfSegments() {
        // the number of line segments
        return numOfSegments;
    }

    public LineSegment[] segments() {
        // the line segments
        return segments.clone();
    }
}