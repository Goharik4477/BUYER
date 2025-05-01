package com.example.buyer.BUYER.b.ViewHolder;

public class Products {
    private String firstCountryNew;
    private String secondCountyNew;
    private String descriptionNew;
    private String addressNew;
    private String priceNew;
    private String linkNew;
    private String saveCurrentTime;
    private String saveCurrentDate;


    public Products() {

    }


    public Products(String firstCountryNew, String secondCountyNew, String descriptionNew,
                    String addressNew, String priceNew, String linkNew, String saveCurrentTime, String saveCurrentDate) {
        this.firstCountryNew = firstCountryNew;
        this.secondCountyNew = secondCountyNew;
        this.descriptionNew = descriptionNew;
        this.addressNew = addressNew;
        this.priceNew = priceNew;
        this.linkNew = linkNew;
        this.saveCurrentTime = saveCurrentTime;
        this.saveCurrentDate = saveCurrentDate;
    }


    public String getFirstCountryNew() {
        return firstCountryNew;
    }

    public void setFirstCountryNew(String firstCountryNew) {
        this.firstCountryNew = firstCountryNew;
    }

    public String getSecondCountyNew() {
        return secondCountyNew;
    }

    public void setSecondCountyNew(String secondCountyNew) {
        this.secondCountyNew = secondCountyNew;
    }

    public String getDescriptionNew() {
        return descriptionNew;
    }

    public void setDescriptionNew(String descriptionNew) {
        this.descriptionNew = descriptionNew;
    }

    public String getAddressNew() {
        return addressNew;
    }

    public void setAddressNew(String addressNew) {
        this.addressNew = addressNew;
    }

    public String getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(String priceNew) {
        this.priceNew = priceNew;
    }

    public String getLinkNew() {
        return linkNew;
    }

    public void setLinkNew(String linkNew) {
        this.linkNew = linkNew;
    }

    public String getSaveCurrentTime() {
        return saveCurrentTime;
    }

    public void setSaveCurrentTime(String saveCurrentTime) {
        this.saveCurrentTime = saveCurrentTime;
    }

    public String getSaveCurrentDate() {
        return saveCurrentDate;
    }

    public void setSaveCurrentDate(String saveCurrentDate) {
        this.saveCurrentDate = saveCurrentDate;
    }
}
