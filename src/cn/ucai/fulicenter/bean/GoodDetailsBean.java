package cn.ucai.fulicenter.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by sks on 2016/4/15.
 */
public class GoodDetailsBean implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodDetailsBean that = (GoodDetailsBean) o;

        return goodsId == that.goodsId;

    }

    @Override
    public int hashCode() {
        return goodsId;
    }

    /*{

            "id":280,// 主键ID
            "goodsId":7677,// 商品id
            "catId":291,// 分类id
            "goodsName":"双层分格饭盒 绿色",//商品的中文名称
            "goodsEnglishName":"Monbento",//商品的英文名称
            "goodsBrief":"PP食品级材质，轻巧、易清洗、蠕变性小，不易变形，可置于微波炉加热，可方巾洗碗机清洗。双层色彩可以随意组合，轻巧方便。",//商品简介
            "shopPrice":"￥253",//商品原始价格
            "currencyPrice":"￥293",//商品的人民币价格
            "promotePrice":"￥0",//商品折扣价格
            "rankPrice":"￥293",//商品的人民币折扣价格
            "goodsThumb":"201509/thumb_img/7677_thumb_G_1442391216339.png",//商品缩略图地址
            "goodsImg":"201509/thumb_img/7677_thumb_G_1442391216339.png",//商品图片地址
            "addTime":1442419200000,// 上架时间
            "shareUrl":"http://m.fulishe.com/item/7677",//分享地址
            "properties":[//商品属性
                {
                    "id":9529,// 属性id
                    "goodsId":0,// 商品id
                    "colorId":7, //商品属性的颜色id
                    "colorName":"白色",//商品属性的颜色名称
                    "colorCode":"#ffffff",//商品属性颜色码
                    "colorImg":"",//商品属性颜色图片地址
                    "colorUrl":"https://detail.tmall.com/item.htm?spm=a1z10.5-b.w4011-3609973698.66.6PtkVY&id=520971761592&rn=5ddf7aff64dbe1a24da0eaf7409e3389&abbucket=15&skuId=3104519239252",//商品属性颜色地址
                    "albums":[//相册属性
                        {
                                "pid":7677,// 商品属性id
                                "imgId":28296,// 相册图片id
                                "imgUrl":"201509/goods_img/7677_P_1442391216432.png",//相册图片地址
                                "thumbUrl":"no_picture.gif"//相册缩略图地址
                            }
                    ]
                }
            ],
            "promote":false//是否允许折扣
    }**/

    private int id;
    private int goodsId;
    private int catId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsBrief;
    private String shopPrice;
    private String currencyPrice;
    private String promotePrice;
    private String rankPrice;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;
    private String shareUrl;
    private PropertyBean[] propertyBean;
    @JsonProperty("isPromote")
    private boolean isPromote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PropertyBean[] getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(PropertyBean[] propertyBean) {
        this.propertyBean = propertyBean;
    }


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getRankPrice() {
        return rankPrice;
    }

    public void setRankPrice(String rankPrice) {
        this.rankPrice = rankPrice;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isPromote() {
        return isPromote;
    }

    public void setIsPromote(boolean isPromote) {
        this.isPromote = isPromote;
    }

    @Override
    public String toString() {
        return "ColorBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", catId=" + catId +
                ", goodsName='" + goodsName +
                ", goodsEnglishName='" + goodsEnglishName +
                ", goodsBrief='" + goodsBrief +
                ", shopPrice='" + shopPrice +
                ", currencyPrice='" + currencyPrice +
                ", promotePrice='" + promotePrice +
                ", rankPrice='" + rankPrice +
                ", goodsThumb='" + goodsThumb +
                ", goodsImg='" + goodsImg +
                ", addTime=" + addTime +
                ", shareUrl='" + shareUrl +
                ", propertyBean=" + Arrays.toString(propertyBean) +
                ", isPromote=" + isPromote +
                '}';
    }

}
