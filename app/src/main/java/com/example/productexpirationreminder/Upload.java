package com.example.productexpirationreminder;

public class Upload {
    String itemid;
    private String mName;
    private String mImageUrl;
    public Upload(){

    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public Upload(String itemid,String mName, String mImageUrl) {
        if(mName.trim().equals("")){
            mName="No name";

        }
        this.itemid = itemid;
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }
}
