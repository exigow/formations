attribute vec4 a_position;
uniform mat4 u_projModelView;

void main() {
   gl_Position = u_projModelView * a_position;
   gl_PointSize = 2.0;
}
