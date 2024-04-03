package src.primitives;

public class Vector4 {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4(double x, double y, double z, double w) {
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
        return "Vector4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

    // STATIC METHODS

    public static double magnitude(Vector4 v) {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
    }

    public static Vector4 normilize(Vector4 v) {
        double mag = magnitude(v);
        v.x /= mag;
        v.y /= mag;
        v.z /= mag;
        v.w /= mag;

        return v;
    }

    public static double dot(Vector4 a, Vector4 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    public static Vector4 cross(Vector4 a, Vector4 b) {
        double x = a.y * b.z - a.z * b.y;
        double y = a.z * b.x - a.x * b.z;
        double z = a.x * b.y - a.y * b.x;

        return new Vector4(x, y, z, 1);
    }

    public static Vector4 add(Vector4 a, Vector4 b) {
        return new Vector4(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
    }

    public static Vector4 sub(Vector4 a, Vector4 b) {
        return new Vector4(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
    }

    public static Vector4 negative(Vector4 v) {
        return new Vector4(-v.x, -v.y, -v.z, -v.w);
    }
}
