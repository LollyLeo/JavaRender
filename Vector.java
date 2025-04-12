import java.util.Scanner;
public class Vector {
  double x, y, z, l;
  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.l = Math.sqrt((this.z * this.z + this.y * this.y + this.x * this.x));
  }
  public Vector(Point p) {
    this.x = p.x;
    this.y = p.y;
    this.z = p.z;
    this.l = Math.sqrt((this.z * this.z + this.y * this.y + this.x * this.x));
  }
  public Vector(Point p1, Point p2) {
    this.x = p2.x - p1.x;
    this.y = p2.y - p1.y;
    this.z = p2.z - p1.z;
    this.l = Math.sqrt((this.z * this.z + this.y * this.y + this.x * this.x));
  }
  public double scalar(Vector v) {
    return this.x * v.x + this.y * v.y + this.z * v.z;
  }
  public Vector minus(Vector u) {
    return new Vector(this.x - u.x, this.y - u.y, this.z - u.z);
  }
  public Vector muled(double k) {
    return new Vector(this.x * k, this.y * k, this.z * k);
  }
  public String toString() { return this.x + " " + this.y + " " + this.z; }
  public Vector lengthed(double l) {
    return new Vector(this.x / this.l * l, this.y / this.l * l,
                      this.z / this.l * l);
  }
  public double coswith(Vector u) { return this.scalar(u) / this.l / u.l; }
  public double sinwith(Vector u) { return this.vectorp(u) / this.l / u.l; }
  // vector product
  public double vectorp(Vector v) {
    double x1, x2, x3, y1, y2, y3, z1, z2, z3;
    x1 = this.x;
    x2 = v.x;
    y1 = this.y;
    y2 = v.y;
    z1 = this.z;
    z2 = v.z;
    return Math.sqrt(Math.pow(y1 * z2 - z1 * y2, 2) +
                     Math.pow(z1 * x2 - x1 * z2, 2) +
                     Math.pow(x1 * y2 - y1 * x2, 2));
  }
  public Vector reflect(Vector norm) {
    return norm.lengthed(2 * this.l * this.coswith(norm)).minus(this);
    // return (norm.lengthed(2 * this.scalar(norm))).minus(this);
  }
  public static Vector read3(Scanner sc) {
    double x, y, z;
    x = sc.nextDouble();
    y = sc.nextDouble();
    z = sc.nextDouble();
    return new Vector(x, y, z);
  }
}
