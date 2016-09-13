package com.cci.projectx.core;

/**
 * Created by 33303 on 2016/9/2.
 */
public enum  FriendRelation {
    ALREADYFRIENDS(1),//已经是朋友
    NOFRIENDS(2),  //不是朋友
    NOREGISTER(3);//未注册
    int type;
    private FriendRelation(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }
}
