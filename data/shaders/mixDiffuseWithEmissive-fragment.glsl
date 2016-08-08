#version 120

uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;
uniform sampler2D textureEmissiveBlurred;
uniform float noiseOffset;

varying vec2 texCoord;

const vec3 spectrumSample[3] = vec3[3](
    vec3(1, 0, 0),
    vec3(0, 1, 0),
    vec3(0, 0, 1)
);

vec2 uvDistortion(float k, float kcube) {
    float r2 = (texCoord.x - .5) * (texCoord.x - .5) + (texCoord.y - .5) * (texCoord.y - .5);
    float f = 0;
    f = 1 + r2 * (k + kcube * sqrt(r2));
    float x = f * (texCoord.x - .5) + .5;
    float y = f * (texCoord.y - .5) + .5;
    return vec2(x, y);
}


float rand(vec2 co){
    return fract(sin(dot(co.xy, vec2(12.9898 + noiseOffset, 78.233 + noiseOffset))) * 43758.5453);
}

float calculateVignette(vec2 coord) {
	vec2 uv = coord.xy;
    uv *=  1.0 - uv.yx;
    float vig = uv.x*uv.y * 64.0 * (.5 + .5 * rand(coord));
    return clamp(pow(vig, 0.25), 0, 1) * .5 + .5;
}

vec3 sampleAbberation(sampler2D tex) {
vec3 color = vec3(0);
    for (int i = 0; i < 3; i++) {
        float n = i / 3.0;
        vec2 coord = uvDistortion(-.0325 * n, n * .0275);
        color += texture2D(tex, coord).rgb * spectrumSample[i];
    }
    return color;
}

void main() {
    vec3 diffuse = sampleAbberation(textureDiffuse);
    vec3 emissive = sampleAbberation(textureEmissive) * .5;
    vec3 emissiveBlurred = sampleAbberation(textureEmissiveBlurred);
    float vignetteFactor = calculateVignette(texCoord);
    vec3 vignette = vec3(vignetteFactor);
    vec3 color = (diffuse + emissive + emissiveBlurred) * vignette;
    gl_FragColor = vec4(color, 1);
}
