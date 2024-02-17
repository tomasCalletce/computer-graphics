package src.primitives;

public class Matrix4x4 {

  private double[][] matrix;

  public Matrix4x4(double[][] matrix) {
    if (matrix.length != 4 ||
        matrix[0].length != 4 ||
        matrix[1].length != 4 ||
        matrix[2].length != 4) {
      throw new IllegalArgumentException("Matrix must be 4x4.");
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

  public static Point4 mul(Point4 point, Matrix4x4 matrix) {
    double[][] m = matrix.getMatrix();

    double pointX = point.getX();
    double pointY = point.getY();
    double pointZ = point.getZ();
    double pointW = point.getW();

    double x = pointX * m[0][0] + pointY * m[0][1] + pointZ * m[0][2] + pointW * m[0][3];
    double y = pointX * m[1][0] + pointY * m[1][1] + pointZ * m[1][2] + pointW * m[1][3];
    double z = pointX * m[2][0] + pointY * m[2][1] + pointZ * m[2][2] + pointW * m[2][3];
    double w = pointX * m[3][0] + pointY * m[3][1] + pointZ * m[3][2] + pointW * m[3][3];

    return new Point4(x, y, z, w);
  }

  public static Matrix4x4 mul(Matrix4x4 a, Matrix4x4 b) {
    double[][] aMatrix = a.getMatrix();
    double[][] bMatrix = b.getMatrix();

    double[][] result = new double[4][4];

    for (int i = 0; i < aMatrix.length; i++) {
      for (int j = 0; j < bMatrix[i].length; j++) {
        result[i][j] = aMatrix[i][0] * bMatrix[0][j] +
            aMatrix[i][1] * bMatrix[1][j] +
            aMatrix[i][2] * bMatrix[2][j] +
            aMatrix[i][3] * bMatrix[3][j];
      }
    }

    return new Matrix4x4(result);
  }

}
