import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private Point[] arr;
    private List<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        arr = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException();

            if (Arrays.asList(arr).contains(points[i]))
                throw new IllegalArgumentException();

            arr[i] = points[i];
        }

        Arrays.sort(arr);

        Point[] slopeSortedArr = arr.clone();

        for (int i = 0; i < arr.length; i++) {
            Arrays.sort(slopeSortedArr, arr[i].slopeOrder());

            int wantedSlopeId = 0;
            int max = wantedSlopeId;
            int min = wantedSlopeId;

            for (int j = wantedSlopeId; j < slopeSortedArr.length; j++) {
                if (arr[i].slopeTo(slopeSortedArr[wantedSlopeId]) != arr[i].slopeTo(slopeSortedArr[j])) {
                    if (j - wantedSlopeId >= 3 && slopeSortedArr[min].compareTo(arr[i]) > 0) {
                        LineSegment ls = new LineSegment(arr[i], slopeSortedArr[max]);
                        segments.add(ls);
                    }

                    wantedSlopeId = max = min = j;
                    continue;
                }

                if (slopeSortedArr[j].compareTo(slopeSortedArr[min]) < 0)
                    min = j;

                if (slopeSortedArr[j].compareTo(slopeSortedArr[max]) > 0)
                    max = j;
            }

            if (slopeSortedArr.length - wantedSlopeId >= 3  && slopeSortedArr[min].compareTo(arr[i]) > 0) {
                LineSegment ls = new LineSegment(arr[i], slopeSortedArr[max]);
                segments.add(ls);
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
