package prolevexman.model.request;

public class CourierDelete {
    private String id;

    public CourierDelete(int id) {
        this.id = String.valueOf(id);
    }

    public CourierDelete() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
