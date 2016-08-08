#version 120

uniform sampler2D texture;
uniform vec3 ambientColor;

varying vec2 texCoord;

void main() {
    vec4 color = texture2D(texture, texCoord);
    vec3 multipledColor = color.rgb * ambientColor;
    gl_FragColor = vec4(multipledColor, color.a);
}
