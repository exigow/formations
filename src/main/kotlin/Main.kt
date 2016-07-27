import assets.AssetsManager
import assets.ShaderProgramLoader
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.*
import rendering.ShipDebugRenderer.render
import rendering.materials.MaterialRenderer
import rendering.trails.TrailsBuffer
import rendering.trails.TrailsDebugRenderer
import rendering.trails.TrailsRenderer
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  //private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val buffer = TrailsBuffer()
  private val trailsMap = world.allShips().filter { !it.config.displayedName.equals("Carrier") }.map{ it to buffer.registerTrail(it.position + Vec2.rotated(it.angle) * it.config.trailDistance) }.toMap()

  /*private val trailsRenderer = TrailsRenderer()
  private val materialRenderer = MaterialRenderer()
  private val gbuffer = GBuffer.setUp(Gdx.graphics.width, Gdx.graphics.height)*/
  private val fullscreenQuadRenderer = FullscreenQuadTextureRenderer()

  private val mrt = MRTFrameBuffer(Gdx.graphics.width, Gdx.graphics.height)

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
    actions.addAction(CameraShipLockAction(camera, context))
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    world.update(delta)
    buffer.update(delta)
    render(delta);
    trailsMap.forEach { e ->
      e.value.emit(e.key.position + (Vec2.rotated(e.key.angle) * e.key.config.trailDistance), 32f, Math.min(e.key.velocityAcceleration * 8 + .025f, 1f))
    }
  }

  fun render(delta: Float) {
    Draw.update(camera)

    mrt.begin()

    mrt.end()

    /*gbuffer.clearDiffuse(Color.VERY_DARK_GRAY, 1f)
    gbuffer.paintOnDiffuse {
      Draw.grid(size = Vec2.scaled(1024f), density = 16, color = Color.DARK_GRAY)
    }
    world.allShips().forEach {
      gbuffer.paintOnDiffuse {
        if (trailsMap.containsKey(it))
          trailsRenderer.render(trailsMap[it]!!, AssetsManager.peekMaterial("trail").diffuse!!, camera.projectionMatrix())
      }
      gbuffer.paintOnDiffuse {
        materialRenderer.draw(AssetsManager.peekMaterial(it.config.hullName), it.position, it.angle, camera.projectionMatrix())
      }
    }
    gbuffer.paintOnDiffuse {
      world.allShips().forEach {
        it.render(camera.normalizedRenderingScale())
      }
      uiRenderer.render(delta)
      TrailsDebugRenderer.render(buffer)
    }*/
    fullscreenQuadRenderer.render(mrt.getBufferTexture(0))

  }

  private val shader = ShaderProgram(
    """
in vec3 a_position;
in vec3 a_normal;

#ifdef texturedFlag
in vec2 a_texCoord0;
in vec3 a_tangent;
in vec3 a_binormal;
#else
in vec4 a_color;
#endif

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;
uniform mat3 u_normalMatrix;

out vec3 v_normal;
out vec3 v_position;

#ifdef texturedFlag
out vec3 v_tangent;
out vec3 v_binormal;
out vec2 v_texCoords;
#else
out vec4 v_color;
#endif

void main() {

    v_normal = normalize(u_normalMatrix * a_normal);

    #ifdef texturedFlag
        v_tangent = normalize(u_normalMatrix * a_tangent);
        v_binormal = normalize(u_normalMatrix * a_binormal);
        v_texCoords = a_texCoord0;
    #else
        v_color = a_color;
    #endif

    vec4 position = u_worldTrans * vec4(a_position, 1.0);
    v_position = position.xyz;

    vec4 pos = u_projViewTrans * position;
    gl_Position = pos;
}
    """,
    """
    #ifdef GL_ES
precision mediump float;
#endif

#ifdef texturedFlag
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_normalTexture;
#endif

in vec3 v_normal;
in vec3 v_position;

#ifdef texturedFlag
in vec3 v_tangent;
in vec3 v_binormal;
in vec2 v_texCoords;
#else
in vec4 v_color;
#endif

layout(location = 0) out vec4 diffuseOut;
layout(location = 1) out vec3 normalOut;
layout(location = 2) out vec3 positionOut;

void main() {
    #ifdef texturedFlag
        vec4 diffuse = texture(u_diffuseTexture, v_texCoords);
        vec4 specular = texture(u_specularTexture, v_texCoords);
        vec3 normal = normalize(2.0 * texture(u_normalTexture, v_texCoords).xyz - 1.0);
    #else
        vec4 diffuse = v_color;
        vec4 specular = vec4(1.0);
    #endif

    diffuseOut.rgb = diffuse.rgb;
    diffuseOut.a = specular.r;

    #ifdef texturedFlag
        vec3 finnormal = normalize((v_tangent * normal.x) + (v_binormal * normal.y) + (v_normal * normal.z));
        normalOut = finnormal;
    #else
        normalOut = v_normal;
    #endif

    positionOut = v_position;
}
    """)

}