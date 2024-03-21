package src.primitives;

public class Point4 {

  private double x;
  private double y;
  private double z;
  private double w;

  public Point4(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
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

  public double getW() {
    return w;
  }

  @Override
  public String toString() {
    return "Point4{" +
        "x=" + x +
        ", y=" + y +
        ", z=" + z +
        ", w=" + w +
        '}';
  }

  public void normalizeW() {
    if (w == 0) {
      return;
    }
    this.x /= w;
    this.y /= w;
    this.z /= w;
    this.w = 1;
  }

}
