package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Setter
@Getter
public class Cart_list {
    private double totalMoney;//总金额
    private List<CartItem> list = new ArrayList<>();//购物项


    public void addCartIntemToCart(CartItem cartItem) {
        /*
            1.如果购物车中没有此商品,就将此商品添加进入购物车
            2.如果购物车中已存在该商品,就叠加购物车中商品的数量
         */

        //购物车中是否存在
        boolean falg = false;
        //已经存在的商品
        CartItem oldCartItem = null;
        for (CartItem item : list) {
            if (item.getProduct().getPid().equals(cartItem.getProduct().getPid())) {
                falg = true;
                oldCartItem = item;
            }
        }
        if (falg) {
            //直接添加新的商品进入购物车
            list.add(cartItem);
        }else{
            //对购物车中的商品数量进行叠加
            oldCartItem.setNum(oldCartItem.getNum()+cartItem.getNum());
        }

    }

    /**
     * 移除购物项
     *
     * @param pid
     */
    public void removeCartItem(String pid) {
        ListIterator<CartItem> iterator = list.listIterator();
        while (iterator.hasNext()) {
            CartItem c = iterator.next();
            if (c.getProduct().getPid().equals(pid)) iterator.remove();
        }
        System.out.println(list);
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        list.clear();
    }


}
