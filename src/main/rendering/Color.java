package rendering;

public class Color {

  public final float red;
  public final float green;
  public final float blue;
  public final float alpha;

  private Color(float red, float green, float blue, float alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public static Color create(float red, float green, float blue, float alpha) {
    return new Color(red, green, blue, alpha);
  }

  public static Color create(float red, float green, float blue) {
    return new Color(red, green, blue, 1f);
  }

  public static Color of(Preset preset) {
    return create(preset.red, preset.green, preset.blue);
  }

  public enum Preset {

    RED(1, 0, 0),
    GREEN(0, 1, 0),
    BLUE(0, 0, 1),
    WHITE(1, 1, 1),
    BLACK(0, 0, 0);

    float red, green, blue;

    Preset(float red, float green, float blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
    }

  }

}
