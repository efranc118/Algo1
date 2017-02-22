import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

  private int[][] blocks;

  public Board(int[][] blocks) {
    this.blocks = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        this.blocks[i][j] = blocks[i][j];
      }
    }


  }

  public int dimension() {
    return this.blocks.length;
  }

  public int hamming() {
    int outOfPlaceCount = 0;
    int correctCount = 1;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] == 0) {
        } //We don't count the blank square
        else if (blocks[i][j] != correctCount) {
          outOfPlaceCount++;
        }
        correctCount++;
      }
    }
    return outOfPlaceCount;
  }

  public int manhattan() {
    int outOfPlaceCount = 0;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        outOfPlaceCount += getManhattanValue(i, j, blocks[i][j]);
      }
    }
    return outOfPlaceCount;
  }

  public boolean isGoal() {
    int correctCount = 1;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (correctCount == Math.pow(blocks.length, 2)) continue;
        if (blocks[i][j] != correctCount) {
          return false;
        }
        correctCount++;
      }
    }
    return true;
  }

  public Board twin() {
    int[][] twin = new int[blocks.length][blocks.length];
    int row1 = -1;
    int row2 = -1;
    int col1 = -1;
    int col2 = -1;

    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] != 0) {
          if (row1 == -1) {
            row1 = i;
            col1 = j;
          } else if (row2 == -1) {
            row2 = i;
            col2 = j;
          }
        }
        twin[i][j] = blocks[i][j];
      }
    }
    int value = twin[row1][col1];

    twin[row1][col1] = twin[row2][col2];
    twin[row2][col2] = value;
    return new Board(twin);
  }

  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;

    Board that = (Board) y;
    if (this.dimension() != that.dimension()) return false;
    for (int i = 0; i < this.dimension(); i++) {
      for (int j = 0; j < this.dimension(); j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) return false;
      }
    }
    return true;
  }


  private int getManhattanValue(int currentRow, int currentColumn, int value) {
    if (value == 0) return 0; //We don't count the blank square
    int totalMoves = 0;
    int correctRow = 0;
    int correctColumn = 0;
    while ((correctRow + 1) * blocks.length < value) correctRow++;
    while (correctRow * blocks.length + (correctColumn + 1) != value) correctColumn++;
    totalMoves += Math.abs(correctRow - currentRow);
    totalMoves += Math.abs(correctColumn - currentColumn);
    return totalMoves;
  }

  public Iterable<Board> neighbors() {
    return new Neighbors();
  }

  private class Neighbors implements Iterable<Board> {

    @Override public Iterator<Board> iterator() {
      return new BoardIterator();
    }
  }

  private class BoardIterator implements Iterator<Board> {

    Board[] boards;
    int currentBoard = 0;

    BoardIterator() {
      ArrayList<Board> boardList = new ArrayList<>();
      int[][] boardCopy = new int[blocks.length][blocks.length];
      int emptyRow = -1;
      int emptyColumn = -1;
      for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks.length; j++) {
          boardCopy[i][j] = blocks[i][j];
          if (blocks[i][j] == 0) {
            emptyColumn = j;
            emptyRow = i;
          }
        }
      }
      if (emptyColumn == -1 || emptyRow == -1) return;//Figure out what's the appropriate response for this
      if (emptyRow - 1 >= 0) {
        boardCopy[emptyRow][emptyColumn] = boardCopy[emptyRow - 1][emptyColumn];
        boardCopy[emptyRow - 1][emptyColumn] = 0;
        boardList.add(new Board(boardCopy));
        boardCopy[emptyRow - 1][emptyColumn] = boardCopy[emptyRow][emptyColumn];
        boardCopy[emptyRow][emptyColumn] = 0;
      }

      if (emptyRow + 1 < dimension()) {
        boardCopy[emptyRow][emptyColumn] = boardCopy[emptyRow + 1][emptyColumn];
        boardCopy[emptyRow + 1][emptyColumn] = 0;
        boardList.add(new Board(boardCopy));
        boardCopy[emptyRow + 1][emptyColumn] = boardCopy[emptyRow][emptyColumn];
        boardCopy[emptyRow][emptyColumn] = 0;
      }
      if (emptyColumn - 1 >= 0) {
        boardCopy[emptyRow][emptyColumn] = boardCopy[emptyRow][emptyColumn - 1];
        boardCopy[emptyRow][emptyColumn - 1] = 0;
        boardList.add(new Board(boardCopy));
        boardCopy[emptyRow][emptyColumn - 1] = boardCopy[emptyRow][emptyColumn];
        boardCopy[emptyRow][emptyColumn] = 0;
      }
      if (emptyColumn + 1 < dimension()) {
        boardCopy[emptyRow][emptyColumn] = boardCopy[emptyRow][emptyColumn + 1];
        boardCopy[emptyRow][emptyColumn + 1] = 0;
        boardList.add(new Board(boardCopy));
        boardCopy[emptyRow][emptyColumn + 1] = boardCopy[emptyRow][emptyColumn];
        boardCopy[emptyRow][emptyColumn] = 0;
      }
      boards = new Board[boardList.size()];
      boards = boardList.toArray(boards);
    }

    @Override public boolean hasNext() {
      return currentBoard < boards.length;
    }

    @Override public Board next() {
      return boards[currentBoard++];
    }
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(dimension()).append("\n");
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        stringBuilder.append(blocks[i][j]).append(" ");
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }


  public static void main(String[] args) {
    int[][] test = new int[2][2];
//    int counter = 0;
//    for (int i = 0; i < 2; i++) {
//      for (int j = 0; j < 2; j++) {
//        test[i][j] = counter++;
//      }
//    }
    test[0][0] = 2;
    test[0][1] = 0;
    test[1][0] = 3;
    test[1][1] = 1;


    Board testBoard = new Board(test);
    Board twin = testBoard.twin();
    System.out.println(testBoard.toString());
    System.out.println(twin.toString());

    Board secondTestBoad = new Board(test);
    System.out.println(testBoard.equals(secondTestBoad));
    System.out.println(testBoard.dimension());
    System.out.println("manhattan value: " + testBoard.manhattan());
    System.out.println("hamming value: " + testBoard.hamming());
    System.out.println(testBoard.toString());
    for (Board neighbor : testBoard.neighbors()) {
      System.out.println(neighbor.toString());
    }
  }
}
