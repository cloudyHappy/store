package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
    private String  oid;
    private Date ordertime;
    private double  total;
    private int  state;
    private String  address;
    private String  name;
    private String  telephone;

    private User user ;
    private List<OrderItem> list = new ArrayList<>();
}
