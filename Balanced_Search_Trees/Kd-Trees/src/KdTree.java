import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size = 0;
    private Node root = null;

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        root = insert(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node insert(Node n, Point2D p, boolean vertical, RectHV rect) {
        if (n == null) {
            size++;
            n = new Node(p);
            n.rect = rect;
            return n;
        }

        if (p.equals(n.p))
            return n;

        if (vertical) {
            if (p.x() > n.p.x())
                n.rt = insert(n.rt, p, !vertical, new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax()));
            else
                n.lb = insert(n.lb, p, !vertical, new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax()));
        }
        else {
            if (p.y() > n.p.y())
                n.rt = insert(n.rt, p, !vertical, new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax()));
            else
                n.lb = insert(n.lb, p, !vertical, new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y()));
        }

        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node n, boolean vertical) {
        if (n == null)
            return false;

        if (n.p.equals(p))
            return true;

        if (vertical && p.x() > n.p.x() || !vertical && p.y() > n.p.y())
            return contains(p, n.rt, !vertical);
        else
            return contains(p, n.lb, !vertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node n, boolean vertical) {
        if (n == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            Point2D start = new Point2D(n.p.x(), n.rect.ymax());
            Point2D finish = new Point2D(n.p.x(), n.rect.ymin());
            start.drawTo(finish);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D start = new Point2D(n.rect.xmax(), n.p.y());
            Point2D finish = new Point2D(n.rect.xmin(), n.p.y());
            start.drawTo(finish);

        }

        draw(n.lb, !vertical);
        draw(n.rt, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        ArrayList<Point2D> range = new ArrayList<Point2D>();

        if (!isEmpty())
            range(rect, root, true, range);

        return range;
    }

    private void range(RectHV rect, Node n, boolean vertical, ArrayList<Point2D> range) {
        if (n.rt != null && n.rt.rect.intersects(rect))
            range(rect, n.rt, !vertical, range);

        if (n.lb != null && n.lb.rect.intersects(rect))
            range(rect, n.lb, !vertical, range);

        if (rect.contains(n.p))
            range.add(n.p);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        if (isEmpty())
            return null;

        return nearest(p, root, true);
    }

    private Point2D nearest(Point2D p, Node n, boolean vertical) {
        if (n == null)
            return null;

        Point2D nearest = n.p;

        Point2D candidat;
        Node oppositeNode = null;
        if (vertical && p.x() < n.p.x() || !vertical && p.y() < n.p.y()) {
            candidat = nearest(p, n.lb, !vertical);
            oppositeNode = n.rt;
        }
        else {
            candidat = nearest(p, n.rt, !vertical);
            oppositeNode = n.lb;
        }

        if (candidat != null && candidat.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = candidat;

        if (oppositeNode != null && oppositeNode.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            candidat = nearest(p, oppositeNode, !vertical);

        if (candidat != null && candidat.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = candidat;

        return nearest;

    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public static void main(String[] args) {
        KdTree kt = new KdTree();

        kt.insert(new Point2D(0.5, 0.5));
        kt.insert(new Point2D(0.5, 0.5));
        kt.insert(new Point2D(0.75, 0.25));
        kt.insert(new Point2D(0.25, 0.75));
        kt.insert(new Point2D(0.25, 0.25));
        System.out.println(kt.size());

        System.out.println(kt.contains(new Point2D(0.5, 0.5)));
        System.out.println(kt.contains(new Point2D(0.25, 0.75)));
        System.out.println(kt.contains(new Point2D(0.75, 0.25)));
        System.out.println(kt.contains(new Point2D(0.75, 0.75)));
    }
}
