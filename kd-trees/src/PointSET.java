import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
  private TreeSet<Point2D> pointSet;

  public PointSET() {
    pointSet = new TreeSet<>();
  }

  public boolean isEmpty() {
    return pointSet.isEmpty();
  }

  public int size() {
    return pointSet.size();
  }

  public void insert(Point2D point) {
    if (point == null) throw new NullPointerException();
    if (!pointSet.contains(point)) {
      pointSet.add(point);
    }
  }

  public boolean contains(Point2D point) {
    if (point == null) throw new NullPointerException();
    return pointSet.contains(point);
  }

  public void draw() {
    for (Point2D point : pointSet) {
      StdDraw.point(point.x(), point.y());
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if(rect == null ) throw new NullPointerException();
    ArrayList<Point2D> points = new ArrayList<>();
    for (Point2D point : pointSet) {
      if (point.x() >= rect.xmin() && point.x() <= rect.xmax()) {
        if (point.y() >= rect.ymin() && point.y() <= rect.ymax()) {
          points.add(point);
        }
      }
    }
    return points;
  }

  public Point2D nearest(Point2D checkPoint) {
    if (checkPoint == null) throw new NullPointerException();
    if(isEmpty()) return null;
    Point2D nearest = pointSet.first();
    double shortestDistance = nearest.distanceTo(checkPoint);
    for (Point2D point : pointSet) {
      if (point.distanceTo(checkPoint) < shortestDistance) {
        nearest = point;
        shortestDistance = point.distanceTo(checkPoint);
      }
    }
    return nearest;
  }


}
