#version 120

uniform sampler2D texture;

varying vec2 texCoord;

const int samples = 64;
const vec2 center = vec2(.468, .576) * .5;

void main() {
    vec2 dir = .5 - texCoord.xy + center;
    vec3 bounces = vec3(0);
    float z = 1.0 / samples;
    for (int i = 0; i < samples; i++) {
        float scale = z * i;
        vec2 uv = texCoord + dir * scale;
        bounces += texture2D(texture, uv).xyz;
    }
    bounces /= 16;

    gl_FragColor = vec4( bounces, 1.0 );
}
