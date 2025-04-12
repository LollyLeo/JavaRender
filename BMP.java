import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class BMP {
  File file;
  int w;
  int h;
  byte[] bmpImage;
  int nullEnd;

  public BMP(int w, int h, String path) {
    this.file = new File(path);
    this.w = w;
    this.h = h;
    this.nullEnd = 4 - ((w * 3) % 4);
    if (this.nullEnd == 4) {
      this.nullEnd = 0;
    }
    int bitmapSize = (h * (w * 3 + this.nullEnd));
    this.bmpImage = new byte[54 + bitmapSize];
    this.prepareBaseHeader();
  }
  public BMP(File file) throws Exception {
    FileInputStream inp = new FileInputStream(file);
    byte[] t = new byte[60];
    inp.read(t);
    int v = Main.readint(Arrays.copyOfRange(t, 22, 27));
    int sh = Main.readint(Arrays.copyOfRange(t, 18, 23));
    this.w = sh;
    this.h = v;
    String s = file.getPath();
    this.nullEnd = 4 - ((w * 3) % 4);
    if (this.nullEnd == 4) {
      this.nullEnd = 0;
    }
    int bitmapSize = (h * (w * 3 + this.nullEnd));
    this.bmpImage = new byte[54 + bitmapSize];
    inp.close();
  }
  public void save() { this.writeByte(bmpImage); }
  static byte[] ColorHexStringToByteArray(String s) {
    return new byte[] {(byte)Integer.parseInt(s.substring(0, 2), 16),
                       (byte)Integer.parseInt(s.substring(2, 4), 16),
                       (byte)Integer.parseInt(s.substring(4), 16)};
  }
  public void writeByte(byte[] bytes) {
    try {

      OutputStream os = new FileOutputStream(this.file);
      os.write(bytes);
      os.close();
    } catch (Exception e) {
      //            System.out.println("Exception: " + e);
    }
  }
  public void prepareBaseHeader() {
    // BM signature
    this.bmpImage[0] = 0x42;
    this.bmpImage[1] = 0x4d;

    // write size
    writeInt4Bytes(this.bmpImage, this.bmpImage.length, 0x2);

    // write offset
    writeInt4Bytes(this.bmpImage, 54, 0xa);

    // dib header size
    writeInt4Bytes(this.bmpImage, 40, 0xe);
    // width
    writeInt4Bytes(this.bmpImage, this.w, 0x12);
    // height
    writeInt4Bytes(this.bmpImage, this.h, 0x16);

    // a bit of trash
    this.bmpImage[0x1a] = 0x1;
    this.bmpImage[0x1c] = 0x18;
    //        writeInt2Bytes(image, 1, 0x1a);
    //        writeInt2Bytes(image, 24, 0x1c);

    // bitmap size
    //        writeInt4Bytes(image, image.length-54, 0x22);

    // a bit of another trash
    //        writeInt4Bytes(image, 2835, 0x26);
    //        writeInt4Bytes(image, 2835, 0x2a);
  }
  static void writeInt4Bytes(byte[] array, int integer, int index) {
    int i = 0;
    //        System.out.println("Integer: " + integer);
    byte[] arr = Int4ToBytes(integer);
    for (byte s : arr) {
      array[index + i] = s;
      i += 1;
      //            System.out.print(s + " ");
    }
    //        System.out.println("");
  }
  static byte[] Int4ToBytes(int n) {
    return ByteBuffer.allocate(4)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putInt(n)
        .array();
  }
  public void fillImage(String hexColor) {
    this.fillRect(0, 0, this.w, this.h, hexColor);
  }
  public void fillRect(int startXPixel, int startYPixel, int rectW, int rectH,
                       String hexColor) {
    for (int y = startYPixel; startYPixel + rectH > y; y++) {
      if (y >= h) {
        continue;
      }
      for (int x = startXPixel; startXPixel + rectW > x; x++) {
        if (x >= w) {
          continue;
        }
        this.fillPixel(x, y, hexColor);
      }
    }
  }
  public void fillPixel(int x, int y, String hexColor) {
    byte[] color = ColorHexStringToByteArray(hexColor.substring(1));
    int startIndex = (this.w * 3 + nullEnd) * (this.h - y - 1) + (x * 3);
    this.bmpImage[54 + startIndex] += color[2];
    this.bmpImage[54 + startIndex + 1] += color[1];
    this.bmpImage[54 + startIndex + 2] += color[0];
  }
  public void fillPixel(int x, int y, int r, int g, int b) {
    int startIndex = (this.w * 3 + nullEnd) * (this.h - y - 1) + (x * 3);
    this.bmpImage[54 + startIndex] += b;
    this.bmpImage[54 + startIndex + 1] += g;
    this.bmpImage[54 + startIndex + 2] += r;
  }
  public void fillPixel(int x, int y, Color c) {
    fillPixel(x, y, c.r, c.g, c.b);
  }
  public Color colorof(int x, int y) {
    int startIndex = (this.w * 3 + this.nullEnd) * (this.h - y - 1) + (x * 3);
    int b, g, r;
    b = this.bmpImage[startIndex + 54];
    g = this.bmpImage[startIndex + 54 + 1];
    r = this.bmpImage[startIndex + 54 + 2];
    return new Color(r, g, b);
  }
  public static boolean comapre(BMP b1, BMP b2) {
    boolean ans = true;
    for (int imx = 0; imx < b1.w; imx++) {
      for (int imy = 0; imy < b1.h; imy++) {
        ans = (ans &&
               Color.aresame(b1.colorof(imx, imy), (b2.colorof(imx, imy))));
      }
    }
    return ans;
  }
}
