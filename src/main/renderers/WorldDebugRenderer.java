package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import world.Collective;
import world.Ship;
import world.Squad;
import world.World;

import java.util.*;
import java.util.stream.Collectors;

public class WorldDebugRenderer {

  private final Renderer renderer = Renderer.setup();

  public void renderSelected(Set<Squad> squads, float border) {
    for (Squad squad : squads)
      for (Ship ship : squad.ships)
        renderer.renderShip(ship, border, Colors.SELECTION.fill, Colors.SELECTION.outline);
  }

  public void renderWorld(World world, Matrix4 projection) {
    renderer.setProjection(projection);
    clearBackground();
    for (Ship ship : world.allShips())
      renderer.renderShip(ship, 0, Colors.SHIP.fill, Colors.SHIP.outline);
    for (Collective collective : world.collectives)
      renderer.renderHull(positionsOf(shipsOf(collective)), Colors.COLLECTIVE.fill, Colors.COLLECTIVE.outline);
    for (Collective collective : world.collectives)
      for (Squad squad : collective.squads)
        renderer.renderHull(positionsOf(squad.ships), Colors.GROUP.fill, Colors.GROUP.outline);
  }

  private static Collection<Ship> shipsOf(Collective collective) {
    Collection<Ship> ships = new ArrayList<>();
    for (Squad squad : collective.squads)
      ships.addAll(squad.ships);
    return ships;
  }

  private static Collection<Vector2> positionsOf(Collection<Ship> ships) {
    return ships.stream().map(e -> e.place.position).collect(Collectors.toList());
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
    ), SHIP(
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
