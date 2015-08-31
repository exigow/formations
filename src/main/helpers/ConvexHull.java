package helpers;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Collection;

public class ConvexHull {

  private static float cross(Vector2 o, Vector2 a, Vector2 b) {
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
  }

  public static Vector2[] convexHull(Collection<Vector2> X) {
    Vector2[] P = toArray(X);
    if (P.length > 1) {
      int n = P.length, k = 0;
      Vector2[] H = new Vector2[2 * n];

      Arrays.sort(P, (o1, o2) -> {
        if (o1.x == o2.x) {
          return (int) (o1.y - o2.y);
        } else {
          return (int) (o1.x - o2.x);
        }
      });

      // Build lower hull
      for (Vector2 aP : P) {
        while (k >= 2 && cross(H[k - 2], H[k - 1], aP) <= 0)
          k--;
        H[k++] = aP;
      }

      // Build upper hull
      for (int i = n - 2, t = k + 1; i >= 0; i--) {
        while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
          k--;
        H[k++] = P[i];
      }
      if (k > 1) {
        H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
      }
      return H;
    } else if (P.length <= 1) {
      return P;
    } else{
      return new Vector2[0];
    }
  }

  private static Vector2[] toArray(Collection<Vector2> vectors) {
    return vectors.toArray(new Vector2[vectors.size()]);
  }

}
