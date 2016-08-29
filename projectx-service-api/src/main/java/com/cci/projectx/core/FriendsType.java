package com.cci.projectx.core;

/**
 * Created by 33303 on 2016/5/28.
 */
public enum  FriendsType {
    ALREADYFRIENDS(1),//已经是朋友
    APPLYFRIENDS(2);  //申请获等待添加的朋友
    int type;
    private FriendsType(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }
}
