package BUYER.ViewHolder;

public class Products {
    private String FirstCountryNew, SecondCountyNew, DescriptionNew, addressNew, priceNew, linkNew, saveCurrentTime, saveCurrentDate;

    public Products() {
        // Пустой конструктор
    }

    // Конструктор с параметрами
    public Products(String firstCountryNew, String secondCountyNew, String descriptionNew,
                    String addressNew, String priceNew, String linkNew, String saveCurrentTime, String saveCurrentDate) {
        FirstCountryNew = firstCountryNew;
        SecondCountyNew = secondCountyNew;
        DescriptionNew = descriptionNew;
        this.addressNew = addressNew;
        this.priceNew = priceNew;
        this.linkNew = linkNew;
        this.saveCurrentTime = saveCurrentTime;
        this.saveCurrentDate = saveCurrentDate;
    }


    public String getFirstCountryNew() {
        return FirstCountryNew;
    }

    public void setFirstCountryNew(String firstCountryNew) {
        FirstCountryNew = firstCountryNew;
    }

    public String getSecondCountyNew() {
        return SecondCountyNew;
    }

    public void setSecondCountyNew(String secondCountyNew) {
        SecondCountyNew = secondCountyNew;
    }

    public String getDescriptionNew() {
        return DescriptionNew;
    }

    public void setDescriptionNew(String descriptionNew) {
        DescriptionNew = descriptionNew;
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
