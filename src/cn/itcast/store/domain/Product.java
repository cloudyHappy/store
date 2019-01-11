package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter@Setter
@ToString
public class Product {
    private String pid;//商品id
    private String pname;//商品名称

    private double market_price;//商场价格
    private double shop_price;//商城价格

    private String pimage;//商品图片路径
    private Date pdate;//上架时间
    private int is_hot;//是否热卖

    private String pdesc;//商品描述
    private int pflag;//商品标记
    private Category category = new Category();//商品分类
    private String cid;
}
