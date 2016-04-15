package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by sks on 2016/4/15.
 */
public class CartBean implements Serializable {


    /**
     * {
     "id": 7672,//主键
     "userName": 7672,// 用户账号
     "goodsId": 7672,// 商品id
     " count": 2,// 某件商品的件数
     " checked": true,//商品英文名称
     "goods": GoodDetailsBean //购物车中goodsId代表的商品详情信息
     }
     */

    private int id;
    private int userName;
    private int goodsId;
    private int count;
    private boolean checked;
    private GoodDetailsBean goods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserName() {
        return userName;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public GoodDetailsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodDetailsBean goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName=" + userName +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", checked=" + checked +
                ", goods=" + goods +
                '}';
    }

    public CartBean(int id, int userName, int goodsId, int count, boolean checked) {
        this.id = id;
        this.userName = userName;
        this.goodsId = goodsId;
        this.count = count;
        this.checked = checked;
    }

    public CartBean() {

    }
}
