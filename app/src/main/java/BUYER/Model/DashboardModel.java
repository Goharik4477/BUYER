package BUYER.Model;

public class DashboardModel {
    int profile;
    String name,  FirstCountry, SecondCountry, Price, About, Link, address;

    public DashboardModel(int profile, String name, String firstCountry, String secondCountry, String price, String about, String link, String address) {
        this.profile = profile;
        this.name = name;
        this.FirstCountry = firstCountry;
        this.SecondCountry = secondCountry;
        this.Price = price;
        this.About = about;
        this.Link = link;
        this.address = address;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstCountry() {
        return FirstCountry;
    }

    public void setFirstCountry(String firstCountry) {
        FirstCountry = firstCountry;
    }

    public String getSecondCountry() {
        return SecondCountry;
    }

    public void setSecondCountry(String secondCountry) {
        SecondCountry = secondCountry;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
