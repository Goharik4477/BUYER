package com.example.buyer.BUYER.b.Model;

import java.io.Serializable;

public class Post  implements Serializable{
    private String postId;


    private String postedBy;
    private String weight;
    private  String postDescription,
            FirsCountry,
            SecondCountry,
            Address, Link, Price, category, username, priceForService, userId, until  ;
    private long postedAt;
    private boolean approved = false;
    private int PostMessage;



    public Post() {
    }




    public Post(String postId,
                String postedBy,
                String postDescription,
                String firsCountry,
                String secondCountry,
                String address,
                String link,
                String price,
                long postedAt,
                String category,
                String username,
                String priceForService,
                String userId,
                String Until,
                String weight)
    {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.FirsCountry = firsCountry;
        this.SecondCountry = secondCountry;
        this.Address = address;
        this.weight = weight;
        this.Link = link;
        this.until = Until;
        this.priceForService =priceForService;
        this.username = username;
        this.Price = price;
        this.userId = userId;
        this.postedAt = postedAt;
        this.category = category;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPriceForService() {
        return priceForService;
    }

    public void setPriceForService(String priceForService) {
        this.priceForService = priceForService;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
