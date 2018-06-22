
package br.com.barrsoft.cryptocurrency.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CryptoCurrency {

    @SerializedName("cap24hrChange")
    @Expose
    private Double cap24hrChange;
    @SerializedName("long")
    @Expose
    private String longName;
    @SerializedName("mktcap")
    @Expose
    private Double mktcap;
    @SerializedName("perc")
    @Expose
    private Double perc;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("shapeshift")
    @Expose
    private Boolean shapeshift;
    @SerializedName("short")
    @Expose
    private String shortName;
    @SerializedName("supply")
    @Expose
    private Long supply;
    @SerializedName("usdVolume")
    @Expose
    private Float usdVolume;
    @SerializedName("volume")
    @Expose
    private Float volume;
    @SerializedName("vwapData")
    @Expose
    private Double vwapData;
    @SerializedName("vwapDataBTC")
    @Expose
    private Double vwapDataBTC;

    public Double getCap24hrChange() {
        return cap24hrChange;
    }

    public void setCap24hrChange(Double cap24hrChange) {
        this.cap24hrChange = cap24hrChange;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Double getMktcap() {
        return mktcap;
    }

    public void setMktcap(Double mktcap) {
        this.mktcap = mktcap;
    }

    public Double getPerc() {
        return perc;
    }

    public void setPerc(Double perc) {
        this.perc = perc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getShapeshift() {
        return shapeshift;
    }

    public void setShapeshift(Boolean shapeshift) {
        this.shapeshift = shapeshift;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getSupply() {
        return supply;
    }

    public void setSupply(Long supply) {
        this.supply = supply;
    }

    public Float getUsdVolume() {
        return usdVolume;
    }

    public void setUsdVolume(Float usdVolume) {
        this.usdVolume = usdVolume;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Double getVwapData() {
        return vwapData;
    }

    public void setVwapData(Double vwapData) {
        this.vwapData = vwapData;
    }

    public Double getVwapDataBTC() {
        return vwapDataBTC;
    }

    public void setVwapDataBTC(Double vwapDataBTC) {
        this.vwapDataBTC = vwapDataBTC;
    }
}
