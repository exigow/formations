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

vec3 colorMultiplers[samplesCount] = {
    vec3(.75, .83, .56),
    vec3(.76, .56, .87),
    vec3(.227, .272, .286),
    vec3(.89, .87, .98),
    vec3(.56, .32, .98),
    vec3(.87, .56, .15),
    vec3(.38, .97, .45),
    vec3(.78, .43, .73)
};

vec3 distorted(vec2 uv, vec2 dir) {
    vec3 result = vec3(0.0);
    vec2 scale = dir * .0175;
    result.r += texture2D(texture, uv + scale).r;
    result.g += texture2D(texture, uv).g;
    result.b += texture2D(texture, uv - scale).b;
    return result;
}

vec3 calculateHalo(vec2 dir) {
    vec2 haloVec = normalize(dir) * .467;
    float weight = length(vec2(0.5) - fract(texCoord + haloVec)) / length(vec2(0.5));
    weight = pow(1.0 - weight, 8.0);
    return distorted(texCoord + haloVec, dir) * weight * vec3(.76, .72, .97);
}

float calculateWeight(vec2 uv) {
    float weight = clamp(length(vec2(0.5) - uv) / length(vec2(0.5)), 0, 1);
    return pow(1.0 - weight, 12.0);
}

vec2 addSize(vec2 test, float amount) {
    return test * (1 + amount * 2) - vec2(amount);
}

vec3 bounce(int i, vec2 dir) {
    vec2 uv = texCoord + dir * distances[i];
    return distorted(uv, dir) * calculateWeight(uv) * colorMultiplers[i];
}

vec3 colorizeWithRadialGradient(vec3 color) {
    vec2 gradientSample = length(vec2(0.5) - texCoord) / length(vec2(0.5));
    vec3 gradientColor = texture2D(gradient, gradientSample).rgb;
    return color * gradientColor;
}

void main() {
    vec2 dir = .5 - texCoord.xy;
    vec3 source = texture2D(texture, texCoord).rgb * vec3(.125);
    vec3 bounces = vec3(0);
    for (int i = 0; i < samplesCount; i++)
        bounces += bounce(i, dir);
    vec3 colored = colorizeWithRadialGradient(bounces);
    vec3 halo = calculateHalo(dir);
    gl_FragColor = vec4(colored + source + halo, 1);
}
