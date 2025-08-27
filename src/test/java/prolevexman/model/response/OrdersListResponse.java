package prolevexman.model.response;

import java.util.List;

public class OrdersListResponse {
    private List<OrderResponse> orders;
    private PageInfo pageInfo;
    private List<Stations> availiableStations;

    public List<Stations> getAvailiableStations() {
        return availiableStations;
    }

    public void setAvailiableStations(List<Stations> availiableStations) {
        this.availiableStations = availiableStations;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
