package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cart_map {
    private double totalMoney;//总金额
    private Map<String,CartItem> map = new HashMap<>();

    /**
     * 向购物车中添加购物项
     * @param cartItem
     */
    public void addCartItemToCar(CartItem cartItem){
        String pid = cartItem.getProduct().getPid();
        if(map.containsKey(pid)){
            CartItem c = map.get(pid);
            c.setNum(c.getNum()+cartItem.getNum());
        }else{
            map.put(pid,cartItem );
        }
    }
    /**
     * 移除购物车中的项
     * @param pid
     */
    public void removeCartItemByPid(String pid){
        map.remove(pid);
    }


    /**
     * 清空购物车
     */
    public void clearCart(){
        map.clear();
    }

    public double getTotalMoney(){
        //总计清零
        totalMoney = 0;
        Collection<CartItem> values = map.values();
        for (CartItem value : values) {
            totalMoney+=value.getSubTotal();
        }
        return totalMoney;
    }
}
