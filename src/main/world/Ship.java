package world;

import static helpers.MathUtilities.*;

public class Ship {

  public Place place = new Place();
  public float size;
  public float speed;
  public float maximumAvailableSpeed = 1f;

  public void moveTo(Place destination) {
    float acceleration = .0125f;
    float directionToDesignation = pointDirection(destination.position, place.position);
    float distanceToDesignation = pointDistance(destination.position, place.position);
    float flySpeedTarget = Math.min(distanceToDesignation * .025f, maximumAvailableSpeed);
    speed += (flySpeedTarget - speed) * acceleration;
    place.direction += angdiff(directionToDesignation, place.direction) * .0125f;
    float fix = (float)Math.pow(1 - Math.min(pointDistance(place.position, destination.position), 128f) / 128f, 16f);
    place.direction -= angdiff(place.direction, destination.direction) * fix;
    float vx = lerp(ldx(speed, place.direction), (destination.position.x - place.position.x) * .125f, fix);
    float vy = lerp(ldy(speed, place.direction), (destination.position.y - place.position.y) * .125f, fix);
    place.position.add(vx, vy);
  }

}
