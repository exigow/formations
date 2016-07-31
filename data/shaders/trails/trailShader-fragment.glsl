uniform sampler2D texture;

varying vec2 texCoord;
varying float life;

float luminance(vec3 rgb) {
    vec3 w = vec3(0.2125, 0.7154, 0.0721);
    return dot(rgb, w);
}

void main() {
    vec3 color = texture2D(texture, texCoord);
    float alpha = life * luminance(color);
    gl_FragColor = vec4(color, alpha);
}
