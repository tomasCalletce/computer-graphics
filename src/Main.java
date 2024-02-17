package src;

import src.primitives.Matrix3x3;

public class Main {

  public static void main(String[] args) {
    double[][] matrix = {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };

    double[][] matrix2 = {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };

    Matrix3x3 m0 = new Matrix3x3(matrix);
    Matrix3x3 m1 = new Matrix3x3(matrix2);
    Matrix3x3 result = Matrix3x3.mul(m0, m1);

    System.out.println(result);
  }

}
