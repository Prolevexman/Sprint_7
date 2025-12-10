package prolevexman.data;

public enum LoginError {
    EMPTY_LOGIN(400, "Недостаточно данных для входа"),
    EMPTY_PASSWORD(400, "Недостаточно данных для входа"),
    NONEXISTING_CREDENTIALS(404, "Учетная запись не найдена");

    private final int status;
    private final String message;

    LoginError(int status, String message ) {
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
