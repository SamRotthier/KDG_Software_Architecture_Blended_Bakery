package be.kdg.sa.bakery.controller.dto;

public class NewProductDto {
    private String name;
    private String description;

    public NewProductDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public NewProductDto() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
