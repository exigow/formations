uniform sampler2D texture;
uniform vec2 scale;

varying vec2 texCoord;

void main() {
    vec4 color = vec4(0);
    vec2 stage1 = vec2(1.411764705882353) * scale;
    vec2 stage2 = vec2(3.2941176470588234) * scale;
    vec2 stage3 = vec2(5.176470588235294) * scale;
    color += texture2D(texture, texCoord) * 0.1964825501511404;
    color += texture2D(texture, texCoord + stage1) * 0.2969069646728344;
    color += texture2D(texture, texCoord - stage1) * 0.2969069646728344;
    color += texture2D(texture, texCoord + stage2) * 0.09447039785044732;
    color += texture2D(texture, texCoord - stage2) * 0.09447039785044732;
    color += texture2D(texture, texCoord + stage3) * 0.010381362401148057;
    color += texture2D(texture, texCoord - stage3) * 0.010381362401148057;
    gl_FragColor = color;
}
