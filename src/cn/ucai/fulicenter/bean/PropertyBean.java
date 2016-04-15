package cn.ucai.fulicenter.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by sks on 2016/4/15.
 */
public class PropertyBean implements Serializable {

    /**
     * id : 9529
     * goodsId : 0
     * colorId : 7
     * colorName : 白色
     * colorCode : #ffffff
     * colorImg :
     * colorUrl : https://detail.tmall.com/item.htm?spm=a1z10.5-b.w4011-3609973698.66.6PtkVY&id=520971761592&rn=5ddf7aff64dbe1a24da0eaf7409e3389&abbucket=15&skuId=3104519239252
     */

    private int id;
    private int goodsId;
    private int colorId;
    private String colorName;
    private String colorCode;
    private String colorImg;
    private String colorUrl;
    private AlbumBean[] albumBean;

    public AlbumBean[] getAlbumBean() {
        return albumBean;
    }

    public void setAlbumBean(AlbumBean[] albumBean) {
        this.albumBean = albumBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getColorUrl() {
        return colorUrl;
    }

    public void setColorUrl(String colorUrl) {
        this.colorUrl = colorUrl;
    }

    @Override
    public String toString() {
        return "PropertyBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", colorId=" + colorId +
                ", colorName='" + colorName +
                ", colorCode='" + colorCode +
                ", colorImg='" + colorImg +
                ", colorUrl='" + colorUrl +
                ", albumBean=" + Arrays.toString(albumBean) +
                '}';
    }
}
