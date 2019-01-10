package cn.itcast.store.domain;

        import lombok.Getter;
        import lombok.Setter;

@Getter@Setter
public class OrderItem {
    private String itemid;
    private int quantity;
    private double total;
    private Product product;
    private Order order;

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid='" + itemid + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", product=" + product +
                '}';
    }
}
