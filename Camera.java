public class Camera {
  Point p;
  int w, h;
  Vector d, x, y;
  public Camera(Point p, int w, int h, Vector d, Vector x, Vector y) {
    this.p = p;
    this.w = w;
    this.h = h;
    this.d = d;
    this.x = x;
    this.y = y;
  }
  public Point im2vp(double imx, double imy) {
    Point pp = p.sumwith(d);
    pp = pp.sumwith(x.muled(-0.5 + imx / w));
    pp = pp.sumwith(y.muled(-0.5 + imy / h));
    return pp;
  }
}