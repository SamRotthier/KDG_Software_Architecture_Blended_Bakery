package be.kdg.sa.bakery.controller.dto;

import java.io.Serializable;

public class RecipeStepDto implements Serializable {
    private long id;
    private int step;
    private String description;

    public RecipeStepDto() {
    }

    public RecipeStepDto(long id, int step, String description) {
        this.id = id;
        this.step = step;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
