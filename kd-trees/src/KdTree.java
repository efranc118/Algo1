import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;

public class KdTree {

  private Node root;
  private int count;

  private static class Node {
    private Point2D point;
    private RectHV rect;
    private Node lb;
    private Node rt;
  }


  public KdTree() {
    root = null;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    if (root == null) return 0;
    return count;
  }

  public void insert(Point2D point) {
    if (point == null) throw new NullPointerException();
    root = insertNode(root, point, false, new RectHV(0, 0, 1, 1));
  }


  private Node insertNode(Node position, Point2D value, boolean isHorizontal, RectHV rectHV) {
    if (position == null) {
      Node newNode = new Node();
      newNode.lb = null;
      newNode.rt = null;
      newNode.rect = rectHV;
      newNode.point = value;
      count++;
      return newNode;
    }

    if (position.point.equals(value)) return position;
    if (isHorizontal) {
      if (position.point.y() <= value.y()) {
        position.rt = insertNode(position.rt, value, false, position.rt != null ? position.rt.rect : new RectHV(rectHV.xmin(), position.point.y(), rectHV.xmax(), rectHV.ymax()));
      } else if (position.point.y() > value.y()) {
        position.lb = insertNode(position.lb, value, false, position.lb != null ? position.lb.rect : new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), position.point.y()));
      }
    } else {
      if (position.point.x() <= value.x()) {
        position.rt = insertNode(position.rt, value, true, position.rt != null ? position.rt.rect : new RectHV(position.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
      } else if (position.point.x() > value.x()) {
        position.lb = insertNode(position.lb, value, true, position.lb != null ? position.lb.rect : new RectHV(rectHV.xmin(), rectHV.ymin(), position.point.x(), rectHV.ymax()));
      }
    }
    return position;
  }


  public boolean contains(Point2D point) {
    if (point == null) throw new NullPointerException();
    return checkPoint(root, point, false);
  }

  private boolean checkPoint(Node position, Point2D point, boolean isHoriztonal) {
    if (position == null) return false;
    if (position.point.equals(point)) return true;
    if (isHoriztonal) {
      if (position.point.y() > point.y()) {
        return checkPoint(position.lb, point, false);
      } else if (position.point.y() <= point.y()) {
        return checkPoint(position.rt, point, false);
      }
    } else {
      if (position.point.x() > point.x()) {
        return checkPoint(position.lb, point, true);
      } else if (position.point.x() <= point.x()) {
        return checkPoint(position.rt, point, true);
      }
    }
    return false;
  }

  public void draw() {
    drawNodes(root, false);
  }

  private void drawNodes(Node node, boolean isHorizontal) {
    if (node == null) return;
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.point(node.point.x(), node.point.y());
    if (!isHorizontal) {
      StdDraw.setPenColor(Color.RED);
      StdDraw.setPenRadius();
      StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
    }
    if (isHorizontal) {
      StdDraw.setPenColor(Color.BLUE);
      StdDraw.setPenRadius();
      StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
    }
    drawNodes(node.lb, !isHorizontal);
    drawNodes(node.rt, !isHorizontal);
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (root == null) return new ArrayList<>();
    return checkRange(root, rect);
  }

  private ArrayList<Point2D> checkRange(Node position, RectHV rect) {
    ArrayList<Point2D> points = new ArrayList<>();
    if (position.point.x() >= rect.xmin() && position.point.x() <= rect.xmax()) {
      if (position.point.y() >= rect.ymin() && position.point.y() <= rect.ymax()) {
        points.add(position.point);
      }
    }
    if (position.rt != null && position.rt.rect.intersects(rect)) {
      points.addAll(checkRange(position.rt, rect));
    }
    if (position.lb != null && position.lb.rect.intersects(rect)) {
      points.addAll(checkRange(position.lb, rect));
    }
    return points;
  }

  public Point2D nearest(Point2D checkPoint) {
    if (root == null) return null;
    return findNearestNode(root, checkPoint, root.point);
  }

  private Point2D findNearestNode(Node position, Point2D checkpoint, Point2D min) {
    if (checkpoint.distanceSquaredTo(position.point) < checkpoint.distanceSquaredTo(min)) {
      min = position.point;
    }

    if (position.lb == null && position.rt == null) return min;
    Double rightTopDistance = Double.MAX_VALUE;
    Double leftBottomDistance = Double.MAX_VALUE;
    if (position.rt != null) {
      rightTopDistance = position.rt.rect.distanceSquaredTo(checkpoint);
    }
    if (position.lb != null) {
      leftBottomDistance = position.lb.rect.distanceSquaredTo(checkpoint);
    }
    if (rightTopDistance < leftBottomDistance) {
      if (checkpoint.distanceSquaredTo(min) > rightTopDistance) {
        min = findNearestNode(position.rt, checkpoint, min);
      }
      if (checkpoint.distanceSquaredTo(min) > leftBottomDistance) {
        min = findNearestNode(position.lb, checkpoint, min);
      }
    } else {
      if (checkpoint.distanceSquaredTo(min) > leftBottomDistance) {
        min = findNearestNode(position.lb, checkpoint, min);
      }
      if (checkpoint.distanceSquaredTo(min) > rightTopDistance) {
        min = findNearestNode(position.rt, checkpoint, min);
      }
    }
    return min;
  }
//
//  public Point2D nearest(Point2D checkPoint) {
//    if (checkPoint == null) throw new NullPointerException();
//    Point2D nearest = pointSet.first();
//    double shortestDistance = nearest.distanceTo(checkPoint);
//    for (Point2D point : pointSet) {
//      if (point.distanceTo(checkPoint) < shortestDistance) {
//        nearest = point;
//        shortestDistance = point.distanceTo(checkPoint);
//      }
//    }
//    return nearest;
//  }

  public static void main(String[] args) {

  }

}
