package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import helpers.ConvexHull;
import world.Collective;
import world.Entity;
import world.Group;
import world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.badlogic.gdx.math.MathUtils.*;

public class DebugRenderer {

  private final static float ALPHA = 1f;
  private final static Color ENTITY_COLOR = new Color(1, 0, 0, ALPHA);
  private final static Color GROUP_HULL_COLOR = new Color(0, 1, 0, ALPHA);
  private final static Color COLLECTIVE_HULL_COLOR = new Color(0, 0, 1, ALPHA);

  private final ShapeRenderer shape = new ShapeRenderer();

  private void renderEntities(Set<Entity> entities) {
    shape.setColor(ENTITY_COLOR);
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities) {
      shape.circle(entity.position.x, entity.position.y, entity.size);
      float toX = entity.position.x + cos(entity.angle * degreesToRadians) * entity.size;
      float toY = entity.position.y + sin(entity.angle * degreesToRadians) * entity.size;
      shape.line(entity.position.x, entity.position.y, toX, toY);
    }
    shape.end();
  }

  public void renderWorld(World world, Matrix4 projection) {
    shape.setProjectionMatrix(projection);
    clearBackground();
    renderCollectivesHulls(world);
    renderGroupsHulls(world);
    renderEntities(world.allEntities());
  }

  private void renderCollectivesHulls(World world) {
    for (Collective collective : world.collectives)
      renderHull(positionsOf(entitiesOf(collective)), COLLECTIVE_HULL_COLOR);
  }

  private void renderGroupsHulls(World world) {
    for (Collective collective : world.collectives)
      for (Group group : collective.groups)
        renderHull(positionsOf(group.entities), GROUP_HULL_COLOR);
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

  private void renderHull(Collection<Vector2> vectors, Color color) {
    Vector2[] hull = ConvexHull.convexHull(vectors);
    if (hull.length <= 1)
      return;
    shape.setColor(color);
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (int i = 1; i < hull.length; i++) {
      Vector2 from = hull[i - 1];
      Vector2 to = hull[i];
      shape.line(from.x, from.y, to.x, to.y);
    }
    Vector2 first  = hull[0];
    Vector2 last = hull[hull.length - 1];
    shape.line(first.x, first.y, last.x, last.y);
    shape.end();
  }

  private void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
