package game

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.nio.FloatBuffer

class Main {

  // https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/state/ExampleState.java

  private var vaoId = 0
  private var vboId = 0
  val verticesBuffer = createFloatBuffer()

  init {
    vaoId = glGenVertexArrays();
    glBindVertexArray(vaoId);

    // Create a new Vertex Buffer Object in memory and select it (bind)
    // A VBO is a collection of Vectors which in this case resemble the location of each vertex.
    vboId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
    // Put the VBO in the attributes list at index 0
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    // Deselect (bind to 0) the VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    // Deselect (bind to 0) the VAO
    glBindVertexArray(0);
  }

  fun onFrame(delta: Float) {
    clear()

    glBindVertexArray(vaoId);
    glEnableVertexAttribArray(0);

    // Draw the vertices
    glDrawArrays(GL_TRIANGLES, 0, 6);

    // Put everything back to default (deselect)
    glDisableVertexAttribArray(0);
    glBindVertexArray(0);
  }

  private fun clear() {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

    glDrawArrays(GL_TRIANGLES, 0, 3);
  }

  private fun createFloatBuffer() = floatArrayOf(
    // Left bottom triangle
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    // Right top triangle
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
    -0.5f, 0.5f, 0f
  ).toBuffer()

  private fun FloatArray.toBuffer(): FloatBuffer {
    val buffer = BufferUtils.createFloatBuffer(size)
    buffer.put(this)
    buffer.flip()
    return buffer
  }

}