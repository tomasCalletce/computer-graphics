package src.primitives;

public class Matrix3x3 {

  private double[][] matrix;

  public Matrix3x3(double[][] matrix) {
    if (matrix.length != 3 ||
        matrix[0].length != 3 ||
        matrix[1].length != 3 ||
        matrix[2].length != 3) {
      throw new IllegalArgumentException("Matrix must be 3x3.");
    }

    this.matrix = matrix;
  }

  public double[][] getMatrix() {
    return matrix;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        sb.append(matrix[i][j]).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  // STATIC METHODS

  public static Point3 mul(Point3 point, Matrix3x3 matrix) {
    double[][] m = matrix.getMatrix();

    double pointX = point.getX();
    double pointY = point.getY();
    double pointZ = point.getZ();

    double x = pointX * m[0][0] + pointY * m[0][1] + pointZ * m[0][2];
    double y = pointX * m[1][0] + pointY * m[1][1] + pointZ * m[1][2];
    double z = pointX * m[2][0] + pointY * m[2][1] + pointZ * m[2][2];

    return new Point3(x, y, z);
  }

  public static Matrix3x3 mul(Matrix3x3 a, Matrix3x3 b) {
    double[][] aMatrix = a.getMatrix();
    double[][] bMatrix = b.getMatrix();

    double[][] result = new double[3][3];

    for (int i = 0; i < aMatrix.length; i++) {
      for (int j = 0; j < bMatrix[i].length; j++) {
        result[i][j] = aMatrix[i][0] * bMatrix[0][j] +
            aMatrix[i][1] * bMatrix[1][j] +
            aMatrix[i][2] * bMatrix[2][j];
      }
    }

    return new Matrix3x3(result);
  }

}
