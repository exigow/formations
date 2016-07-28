uniform sampler2D texture;

varying vec2 texCoord;
varying float life;

float luminance(vec3 rgb) {
    const vec3 W = vec3(0.2125, 0.7154, 0.0721);
    return dot(rgb, W);
}

void main() {
    float alpha = life * luminance(life);
    vec3 color = texture2D(texture, texCoord);
    gl_FragColor = vec4(color, alpha);
}
