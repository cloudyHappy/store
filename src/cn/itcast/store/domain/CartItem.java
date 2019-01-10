package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CartItem {
    private Product product;//当前商品
    private int num;//当前商品个数
    private double subTotal;//小计
    public double getSubTotal(){
        return num*product.getShop_price();
    }
}
