import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> points = null;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.size() == 0;
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        points.add(new Point2D(p.x(), p.y()));
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        for (Point2D point : points)
            if (p.equals(point))
                return true;

        return false;
    }

    public void draw() {
        for (Point2D p : points) p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        ArrayList<Point2D> range = new ArrayList<Point2D>();

        if (rect.xmin() > 0.1) {
            rect.xmin();
        }

        for (Point2D p : points)
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin() && p.y() <= rect.ymax() && p.y() >= rect.ymin())
                range.add(p);

        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        if (isEmpty())
            return null;

        Point2D nearest = points.max();

        for (Point2D point : points)
            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest))
                nearest = point;

        return nearest;
    }
}
