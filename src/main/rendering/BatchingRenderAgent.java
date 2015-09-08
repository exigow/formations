package rendering;

import rendering.statements.ShapeStatement;

import java.util.Arrays;

public class BatchingRenderAgent {

  private final float[] vertices = new float[32];
  private int pivot = 0;

  public BatchingRenderAgent reset() {
    return this;
  }

  public BatchingRenderAgent render(ShapeStatement statement) {
    float[] add = statement.toVertices();
    for (float anAdd : add) vertices[pivot++] = anAdd;
    return this;
  }

  public BatchingRenderAgent render(ShapeStatement... statements) {
    for (ShapeStatement statement : statements)
      render(statement);
    return this;
  }

  public void flush() {
    System.out.println(Arrays.toString(vertices));
  }

}
