package mine.block.woof;

public enum SkinType {
    TAIGA("woof:textures/variants/taiga.png", "woof:textures/variants/taiga_angry.png", "woof:textures/variants/taiga_tame.png"),
    DEFAULT("minecraft:textures/entity/wolf/wolf.png", "minecraft:textures/entity/wolf/wolf_angry.png", "minecraft:textures/entity/wolf/wolf_tame.png"),
    SNOWY("woof:textures/variants/snowy.png", "woof:textures/variants/snowy_angry.png", "woof:textures/variants/snowy_tame.png"),
    MOUNTAIN("woof:textures/variants/mountain.png", "woof:textures/variants/mountain_angry.png", "woof:textures/variants/mountain_tame.png");

    private final String baseTexture;
    private final String angryTexture;
    private final String tameTexture;

    SkinType(String baseTexture, String angryTexture, String tameTexture) {
        this.baseTexture = baseTexture;
        this.angryTexture = angryTexture;
        this.tameTexture = tameTexture;
    }

    public String getAngryTexture() {
        return angryTexture;
    }

    public String getTameTexture() {
        return tameTexture;
    }

    public String getBaseTexture() {
        return baseTexture;
    }
}
