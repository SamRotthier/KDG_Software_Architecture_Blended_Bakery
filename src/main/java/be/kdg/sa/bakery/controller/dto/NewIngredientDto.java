package be.kdg.sa.bakery.controller.dto;

public class NewIngredientDto {
    private String name;

    public NewIngredientDto() {
    }

    public NewIngredientDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
