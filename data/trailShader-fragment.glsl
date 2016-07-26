uniform sampler2D texture;

varying vec2 texCoord;
varying float life;

void main() {
    vec4 color = texture2D(texture, texCoord) * vec4(1, 1, 1, life);
    gl_FragColor = color;
}
