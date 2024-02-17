public class Vector3 {

  double x;
  double y;
  double z;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

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

  public static void main(String[] args) {
    Vector3 v = new Vector3(1, 2, 3);

    System.out.println(magnitude(v));
    System.out.println(normilize(v).x);

  }
}