package BUYER.ViewHolder;

import com.google.firebase.auth.FirebaseAuth;

public class SaveProduct {
    private String firstCountryNew;
    private String secondCountyNew;
    private String descriptionNew;
    private String addressNew;
    private String priceNew;
    private String linkNew;
    private String saveCurrentTime;
    private String saveCurrentDate;
    private String productRandomKey;
    private String ownerUID;


    public SaveProduct(String firstCountry, String secondCounty, String description, String address,
                       String price, String link, String currentTime, String currentDate, String randomKey) {
        this.firstCountryNew = firstCountry;
        this.secondCountyNew = secondCounty;
        this.descriptionNew = description;
        this.addressNew = address;
        this.priceNew = price;
        this.linkNew = link;
        this.saveCurrentTime = currentTime;
        this.saveCurrentDate = currentDate;
        this.productRandomKey = randomKey;


        this.ownerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

    public String getProductRandomKey() {
        return productRandomKey;
    }

    public void setProductRandomKey(String productRandomKey) {
        this.productRandomKey = productRandomKey;
    }

    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }
}
