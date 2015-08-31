package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import world.Collective;
import world.Entity;
import world.Group;
import world.World;

import java.util.*;
import java.util.stream.Collectors;

public class WorldDebugRenderer {

  private final Renderer renderer = Renderer.setup();

  public void renderSelected(Set<Group> groups, float border) {
    for (Group group : groups)
      for (Entity entity : group.entities)
        renderer.renderEntity(entity, border, Colors.SELECTION.fill, Colors.SELECTION.outline);
  }

  public void renderWorld(World world, Matrix4 projection) {
    renderer.setProjection(projection);
    clearBackground();
    for (Entity entity : entitiesOf(world))
      renderer.renderEntity(entity, 0, Colors.ENTITY.fill, Colors.ENTITY.outline);
    for (Collective collective : world.collectives)
      renderer.renderHull(positionsOf(entitiesOf(collective)), Colors.COLLECTIVE.fill, Colors.COLLECTIVE.outline);
    for (Collective collective : world.collectives)
      for (Group group : collective.groups)
        renderer.renderHull(positionsOf(group.entities), Colors.GROUP.fill, Colors.GROUP.outline);
  }

  public Set<Entity> entitiesOf(World world) {
    Set<Entity> result = new HashSet<>();
    for (Collective collective : world.collectives)
      for (Group group : collective.groups)
        result.addAll(group.entities);
    return result;
  }

  private static Collection<Entity> entitiesOf(Collective collective) {
    Collection<Entity> entities = new ArrayList<>();
    for (Group group : collective.groups)
      entities.addAll(group.entities);
    return entities;
  }

  private static Collection<Vector2> positionsOf(Collection<Entity> entities) {
    return entities.stream().map(e -> e.position).collect(Collectors.toList());
  }

  private void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  public void renderSelection(Rectangle rectangle) {
    renderer.renderHull(rectangleToHull(rectangle), Colors.SELECTION.fill, Colors.SELECTION.outline);
  }

  private static List<Vector2> rectangleToHull(Rectangle rectangle) {
    Vector2 half = new Vector2(rectangle.width / 2, rectangle.height / 2);
    Vector2 center = new Vector2(rectangle.x, rectangle.y).add(half);
    return Arrays.asList(
      new Vector2(center).add(-half.x, -half.y),
      new Vector2(center).add(half.x, -half.y),
      new Vector2(center).add(half.x, half.y),
      new Vector2(center).add(-half.x, half.y));
  }

  private enum Colors {

    SELECTION(
      1, 1, 1, .1f,
      1, 1, 1, 1f
    ), COLLECTIVE(
      1, 0, 1, .125f,
      1, .5f, 1, .5f
    ), GROUP(
      1, 1, 0, .125f,
      1, 1, .5f, .5f
    ), ENTITY(
      1, 1, 0, .125f,
      1, 1, .5f, .5f
    );

    private final Color outline;
    private final Color fill;

    Colors(float r, float g, float b, float a, float r2, float g2, float b2, float a2) {
      this.fill = new Color(r, g, b, a);
      this.outline = new Color(r2, g2, b2, a2);
    }

  }

}
