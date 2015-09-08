package rendering.statements;

import rendering.Vec2;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CircleStatement extends ShapeStatement {

  public final Vec2 position;
  public final float radius;

  public CircleStatement(Vec2 position, float radius) {
    this.position = position;
    this.radius = radius;
  }

  public float[] toVertices() {
    int count = 3;
    double delta = (float) Math.PI * 2 / count;
    float[] vertices = new float[count * 2];
    for (int i = 0; i < count; i += 2) {
      vertices[i] = (float) sin(delta) * radius;
      vertices[i + 1] = (float) cos(delta) * radius;
    }
    return vertices;
  }

}
