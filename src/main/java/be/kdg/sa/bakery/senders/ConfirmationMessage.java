package be.kdg.sa.bakery.senders;

public class ConfirmationMessage {
    private String message;


    public ConfirmationMessage(String message) {
        this.message = message;
    }
    public String getName() {
        return message;
    }
}
