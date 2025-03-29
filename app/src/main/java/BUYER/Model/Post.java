package BUYER.Model;

public class Post {
    private String postId;
    private String postedBy;
    private  String postDescription, FirsCountry, SecondCountry, Address, Link, Price, category ;
    private long postedAt;
    private int PostMessage;



    public Post() {
    }



    public Post(String postId, String postedBy, String postDescription, String firsCountry, String secondCountry, String address, String link, String price, long postedAt, String category) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        FirsCountry = firsCountry;
        SecondCountry = secondCountry;
        Address = address;
        Link = link;
        Price = price;
        this.postedAt = postedAt;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getFirsCountry() {
        return FirsCountry;
    }

    public void setFirsCountry(String firsCountry) {
        FirsCountry = firsCountry;
    }

    public String getSecondCountry() {
        return SecondCountry;
    }

    public void setSecondCountry(String secondCountry) {
        SecondCountry = secondCountry;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public int getPostMessage() {
        return PostMessage;
    }

    public void setPostMessage(int postMessage) {
        PostMessage = postMessage;
    }
}
