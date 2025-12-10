package prolevexman.data;

public enum CourierError {
    EMPTY_LOGIN(400, "Недостаточно данных для создания учетной записи"),
    EMPTY_PASSWORD(400, "Недостаточно данных для создания учетной записи"),
    EMPTY_FIRST_NAME(400, "Недостаточно данных для создания учетной записи"),
    DUPLICATE_LOGIN(409, "Этот логин уже используется. Попробуйте другой.");

    private final int status;
    private final String message;

    CourierError(int status, String message ) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
