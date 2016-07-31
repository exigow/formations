uniform sampler2D texture;

varying vec2 texCoord;
varying float life;

void main() {
    float alpha = pow(life, 4);
    vec3 color = texture2D(texture, texCoord);
    gl_FragColor = vec4(color, alpha);
}
