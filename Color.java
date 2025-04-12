import java.util.Scanner;

class Color {
  int r, g, b;
  public Color(int r, int g, int b) {
    this.r = Math.max(r, 0);
    this.g = Math.max(g, 0);
    this.b = Math.max(b, 0);
  }
  public Color(int q) {
    q = Math.max(q, 0);
    this.r = q;
    this.g = q;
    this.b = q;
  }
  public Color(String c) {
    this.r = Main.hex2int(c.substring(1, 3));
    this.g = Main.hex2int(c.substring(3, 5));
    this.b = Main.hex2int(c.substring(5, 7));
  }
  public String toString() {
    return "#" + Main.tocorrectlength(Main.int2hex(this.r)) +
        Main.tocorrectlength(Main.int2hex(this.g)) +
        Main.tocorrectlength(Main.int2hex(this.b));
  }
  public void mul(double k) {
    this.r = Math.min((int)(this.r * k), 255);
    this.g = Math.min((int)(this.g * k), 255);
    this.b = Math.min((int)(this.b * k), 255);
  }
  public void sum(Color c) {
    this.r = Math.min(c.r + this.r, 255);
    this.g = Math.min(c.g + this.g, 255);
    this.b = Math.min(c.b + this.b, 255);
  }
  public Color sumed(Color c) {
    Color ans = new Color(this.r, this.g, this.b);
    ans.sum(c);
    return ans;
  }
  public Color muled(double k) {
    Color cc = new Color(this.r, this.g, this.b);
    cc.mul(k);
    return cc;
  }
  public void mul(CI ci) {
    this.r = (int)Math.min(ci.r * this.r, 255);
    this.g = (int)Math.min(ci.g * this.g, 255);
    this.b = (int)Math.min(ci.b * this.b, 255);
  }
  public Color muled(CI ci) {
    Color cc = new Color(this.r, this.g, this.b);
    cc.mul(ci);
    return cc;
  }
  public static boolean aresame(Color c1, Color c2) {
    return (c1.r == c2.r && c1.g == c2.g && c1.b == c2.b);
  }
  public static Color read3(Scanner sc) {
    int r, g, b;
    r = sc.nextInt();
    g = sc.nextInt();
    b = sc.nextInt();
    return new Color(r, g, b);
  }
}
