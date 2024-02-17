package src.primitives;

public class Vector3 {

  private double x;
  private double y;
  private double z;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  @Override
  public String toString() {
    return "Vector3{" +
        "x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  // STATIC METHODS

  public static double magnitude(Vector3 v) {
    return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
  }

  public static Vector3 normilize(Vector3 v) {
    double mag = magnitude(v);
    v.x /= mag;
    v.y /= mag;
    v.z /= mag;

    return v;
  }

  public static double dot(Vector3 a, Vector3 b) {
    return a.x * b.x + a.y * b.y + a.z * b.z;
  }

  public static Vector3 cross(Vector3 a, Vector3 b) {
    double x = a.y * b.z - a.z * b.y;
    double y = a.z * b.x - a.x * b.z;
    double z = a.x * b.y - a.y * b.x;

    return new Vector3(x, y, z);
  }

}