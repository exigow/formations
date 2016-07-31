uniform sampler2D texture;
uniform float scale;
uniform float bias;

varying vec2 texCoord;

void main() {
    vec4 color = texture2D(texture, texCoord);
    gl_FragColor = max(vec4(0.0), color + vec4(vec3(bias), 0)) * vec4(vec3(scale), 1);
}
