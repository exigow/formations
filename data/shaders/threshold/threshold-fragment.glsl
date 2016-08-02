uniform sampler2D texture;
uniform float scale;
uniform float bias;

varying vec2 texCoord;

vec3 lookupThresholded(vec2 uv) {
    vec3 color = texture2D(texture, uv).rgb;
    return max(vec3(0), color + vec3(bias)) * vec3(scale);
}

void main() {
    vec2 size = vec2(.0025, .0025);
    vec2 x =  vec2(size.x, 0);
    vec2 y =  vec2(0, size.y);
    vec3 left = lookupThresholded(texCoord - x);
    vec3 right = lookupThresholded(texCoord + x);
    vec3 up = lookupThresholded(texCoord - y);
    vec3 down = lookupThresholded(texCoord + y);
    // this 4x sampling is to avoid "bloom flickering" on downscale
    gl_FragColor = vec4((left + right + up + down) / 4, 1);
}
