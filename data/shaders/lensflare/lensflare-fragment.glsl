uniform sampler2D texture;
uniform sampler2D gradient;

varying vec2 texCoord;

const int samplesCount = 8;

float distances[samplesCount] = {
    0.5f,
    0.7f,
    1.03f,
    1.35,
    1.55f,
    1.62f,
    2.2f,
    3.9f
};

vec3 distorted(vec2 uv, vec2 dir) {
    vec3 result = vec3(0);
    vec2 scale = dir * .0125;
    result.r += texture2D(texture, uv + scale).r;
    result.g += texture2D(texture, uv).g;
    result.b += texture2D(texture, uv - scale).b;
    return result;
}

vec3 circularFlareColor(vec2 dir) {
    vec2 haloVec = normalize(dir) * .4;
    float weight = length(vec2(0.5) - fract(texCoord + haloVec)) / length(vec2(0.5));
    weight = pow(1.0 - weight, 5.0);
    return distorted(texCoord + haloVec, dir) * weight;
}

vec3 bounce(int i, vec2 dir) {
    vec2 uv = texCoord + dir * distances[i];
    float weight = length(vec2(0.5) - uv) / length(vec2(0.5));
    weight = pow(1 - weight, 8);
    return distorted(uv, dir) * .25 * weight;
}

void main() {
    vec2 dir = .5 - texCoord.xy;
    vec3 bounces = vec3(0);
    for (int i = 0; i < samplesCount; i++)
        bounces += bounce(i, dir);
    vec2 circularGradient = length(vec2(0.5) - texCoord) / length(vec2(0.5));
    vec3 circularGradientColor = texture2D(gradient, circularGradient).rgb;
    vec3 colored = bounces * circularGradientColor;
    vec3 circular = circularFlareColor(dir);
    gl_FragColor = vec4(colored + circular, 1);
}
