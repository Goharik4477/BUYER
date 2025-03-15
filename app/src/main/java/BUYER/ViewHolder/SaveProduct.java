package BUYER.ViewHolder;

import com.google.firebase.auth.FirebaseAuth;

public class SaveProduct {
    public String FirstCountryNew, SecondCountyNew, DescriptionNew, addressNew, priceNew, linkNew, saveCurrentTime, saveCurrentDate, productRandomKey;
    public String ownerUID;





    public SaveProduct(String FirstCountry, String SecondCounty, String Description, String address,
                       String price, String link, String CurrentTime, String CurrentDate, String RandomKey) {
        this.FirstCountryNew = FirstCountry;
        this.SecondCountyNew = SecondCounty;
        this.DescriptionNew = Description;
        this.addressNew = address;
        this.priceNew = price;
        this.linkNew = link;
        this.saveCurrentTime = CurrentTime;
        this.saveCurrentDate = CurrentDate;
        this.productRandomKey = RandomKey;

        this.ownerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }
}
