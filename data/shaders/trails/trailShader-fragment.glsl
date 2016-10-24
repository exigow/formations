#version 120

uniform sampler2D texture;

varying vec2 texCoord;
varying float alpha;

void main() {
    vec3 color = texture2D(texture, texCoord).rgb;
    gl_FragColor = vec4(color, alpha);
}
