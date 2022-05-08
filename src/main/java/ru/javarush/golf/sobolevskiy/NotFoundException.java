package ru.javarush.golf.sobolevskiy;

public class NotFoundException extends Exception {
    private final int detail;

    public NotFoundException(int detail, String message) {
        super(message);
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "NotFoundException {"
                + "detail=" + detail
                + ", message=" + getMessage()
                + "} ";
    }
}
