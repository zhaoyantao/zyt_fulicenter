package cn.ucai.fulicenter.bean;


import java.io.Serializable;

/**
 * Created by sks on 2016/4/15.
 */
public class ColorBean implements Serializable {


    /**
     * {
     "catId": 262, //小类别id
     "colorId": 1, //颜色id
     "colorName": "灰色",//颜色名称
     "colorCode": "#959595",//颜色代码
     "colorImg": "http://121.197.1.20/images/201309/1380064809234134935.jpg"//颜色图片地址
     }
     */

    private int catId;
    private int colorId;
    private String colorName;
    private String colorCode;
    private String colorImg;

    @Override
    public String toString() {
        return "ColorBean{" +
                "catId=" + catId +
                ", colorId=" + colorId +
                ", colorName='" + colorName +
                ", colorCode='" + colorCode +
                ", colorImg='" + colorImg +
                '}';
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
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
}
