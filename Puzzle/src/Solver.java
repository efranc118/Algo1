import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Solver {
  private Board initialBoard;
  private GameNode finalGameNode;
  private int moves;

  public Solver(Board initial) {
    if (initial == null) throw new NullPointerException();
    initialBoard = initial;
    SearchNode initialSearchNode = new SearchNode(initial, 0, null);
    GameNode initialGameNode = new GameNode(initialSearchNode, null);
    if (isSolvable()) {
      searchAlgo(initialGameNode);
    }
  }

  private void searchAlgo(GameNode initialGameNode) {
    MinPQ<GameNode> priorityQueue = new MinPQ<>();
    priorityQueue.insert(initialGameNode);
    int possibleMoves;
    while (!priorityQueue.isEmpty()) {
      GameNode temp = priorityQueue.delMin();
      if (temp.searchNode.board.isGoal()) {
        moves = temp.searchNode.moves;
        finalGameNode = temp;
        return;
      } else {
        possibleMoves = temp.searchNode.moves + 1;
        for (Board neighbor : temp.searchNode.board.neighbors()) {
          if (!alreadyFound(neighbor, temp)) {
            priorityQueue.insert(new GameNode(new SearchNode(neighbor, possibleMoves, temp.searchNode.board), temp));
          }
        }
      }
    }
  }

  public boolean isSolvable() {
    Board twin = initialBoard.twin();
    SearchNode initialSearchNode = new SearchNode(initialBoard, 0, null);
    SearchNode twinSearchNode = new SearchNode(twin, 0, null);
    GameNode initialGameNode = new GameNode(initialSearchNode, null);
    GameNode twinGameNode = new GameNode(twinSearchNode, null);


    MinPQ<GameNode> twinPriorityQueue = new MinPQ<>();
    MinPQ<GameNode> priorityQueue = new MinPQ<>();
    priorityQueue.insert(initialGameNode);
    twinPriorityQueue.insert(twinGameNode);
    int possibleMoves;
    while (!priorityQueue.isEmpty()) {
      GameNode temp = priorityQueue.delMin();
      if (temp.searchNode.board.isGoal()) {
        moves = temp.searchNode.moves;
        finalGameNode = temp;
        return true;
      } else {
        possibleMoves = temp.searchNode.moves + 1;
        for (Board neighbor : temp.searchNode.board.neighbors()) {
          if (!alreadyFound(neighbor, temp)) {
            priorityQueue.insert(new GameNode(new SearchNode(neighbor, possibleMoves, temp.searchNode.board), temp));
          }
        }
      }
      if (!twinPriorityQueue.isEmpty()) {
        GameNode twinTemp = twinPriorityQueue.delMin();
        if (twinTemp.searchNode.board.isGoal()) {
          moves = -1;
          finalGameNode = null;
          return false;
        } else {
          possibleMoves = twinTemp.searchNode.moves + 1;
          for (Board neighbor : twinTemp.searchNode.board.neighbors()) {
            if (!alreadyFound(neighbor, twinTemp)) {
              twinPriorityQueue.insert(new GameNode(new SearchNode(neighbor, possibleMoves, temp.searchNode.board), twinTemp));
            }
          }
        }
      }
    }
    return false;
  }


  public int moves() {
    return this.moves;
  }

  public Iterable<Board> solution() {
    if (finalGameNode == null) return null;
    return new Steps();
  }

  private boolean alreadyFound(Board board, GameNode foundBoards) {
    GameNode temp = foundBoards;
    while (temp != null) {
      if (temp.searchNode.board.equals(board)) return true;
      temp = temp.parentNode;
    }
    return false;
  }

  private class GameNode implements Comparable<GameNode> {
    SearchNode searchNode;
    GameNode parentNode;

    GameNode(SearchNode searchNode, GameNode parentNode) {
      this.searchNode = searchNode;
      this.parentNode = parentNode;
    }

    @Override public int compareTo(GameNode o) {
      if (this.searchNode.moves + this.searchNode.board.manhattan() < o.searchNode.moves + o.searchNode.board.manhattan())
        return -1;
      else if (this.searchNode.moves + this.searchNode.board.manhattan() > o.searchNode.moves + o.searchNode.board.manhattan())
        return 1;
      else return 0;
    }
  }

  private class SearchNode implements Comparable<SearchNode> {
    Board board;
    int moves;
    Board previousBoard;

    SearchNode(Board board, int moves, Board previousBoard) {
      this.board = board;
      this.moves = moves;
      this.previousBoard = previousBoard;
    }

    @Override public int compareTo(SearchNode o) {
      if (this.board.manhattan() < o.board.manhattan()) return -1;
      if (this.board.manhattan() > o.board.manhattan()) return 1;
      if (this.board.hamming() < o.board.hamming()) return -1;
      if (this.board.hamming() > o.board.hamming()) return 1;
      if (this.board.equals(o.board)) return 0;
      return 1;
    }
  }


  private class Steps implements Iterable<Board> {

    @Override public Iterator<Board> iterator() {
      return new SolverIteration();
    }
  }


  private class SolverIteration implements Iterator<Board> {

    Board[] inOrderSolution;
    int current;

    SolverIteration() {
      GameNode gameNode = finalGameNode;
      inOrderSolution = new Board[moves + 1];
      for (int i = moves; i >= 0; i--) {
        inOrderSolution[i] = gameNode.searchNode.board;
        gameNode = gameNode.parentNode;
      }
      current = 0;
    }

    @Override public boolean hasNext() {
      return current <= moves;
    }

    @Override public Board next() {
      return inOrderSolution[current++];
    }
  }


  public static void main(String[] args) throws FileNotFoundException {

    // create initial board from file
    Scanner scanner = new Scanner(System.in);
    String fileName = scanner.nextLine();
    // read the n points from a file
    //In in = new In(args[0]);
    //int n = in.readInt();
    System.out.println("Dir: " + System.getProperty("user.dir"));
    Scanner scan = new Scanner(new File(fileName));
    int n = scan.nextInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = scan.nextInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}
