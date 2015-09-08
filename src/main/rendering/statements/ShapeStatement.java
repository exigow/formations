package rendering.statements;

import rendering.Color;

public abstract class ShapeStatement {

  private Color fill = Color.of(Color.Preset.WHITE);
  private Color outline = Color.of(Color.Preset.BLACK);
  private float outlineWidth = 1f;

  public ShapeStatement color(Color color) {
    fill = color;
    return this;
  }

  public ShapeStatement outline(Color color) {
    outline = color;
    return this;
  }

  public ShapeStatement outlineWidth(float width) {
    outlineWidth = width;
    return this;
  }

  public abstract float[] toVertices();

}
