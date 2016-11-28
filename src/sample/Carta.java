package sample;

/**
 * Created by 20464654j on 04/11/16.
 */
public class Carta {

    private String name;
    private String rarity;
    private String type;
    private String color;
    private String imageUrl;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    @Override
    public String toString() {
        return name;
    }
}
