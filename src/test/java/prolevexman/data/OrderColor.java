package prolevexman.data;

public enum OrderColor {
    BLACK("BLACK"),
    GREY("GREY");

    private final String color;

    OrderColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
