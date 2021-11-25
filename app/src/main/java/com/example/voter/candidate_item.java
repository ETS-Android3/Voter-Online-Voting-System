package com.example.voter;

public class candidate_item {

    String id,name,party,img;
    public candidate_item() {

    }

    public candidate_item(String id, String name, String party, String img) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
