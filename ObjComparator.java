
import java.util.Comparator;

public class ObjComparator implements Comparator<Obj> {
  Point p;
  Vector v;
  public ObjComparator(Point p, Vector v) {
    this.p = p;
    this.v = v;
  }

  public int compare(Obj a, Obj b) {
    double ans = a.dist2POV(this.p, this.v) - b.dist2POV(this.p, this.v);
    if (ans - 0 < 0) {
      return -1;
    } else if (ans > 0) {
      return 1;
    } else {
      return 0;
    }
  }
}
