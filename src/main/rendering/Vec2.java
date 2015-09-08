package rendering;

public class Vec2 {

  public final float x;
  public final float y;

  private Vec2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public static Vec2 of(float x, float y) {
    return new Vec2(x, y);
  }

  public static Vec2 zero() {
    return of(0, 0);
  }

  public static Vec2 one() {
    return of(1, 1);
  }

}
