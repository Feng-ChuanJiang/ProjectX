package com.cci.projectx.core;

/**
 * Created by 33303 on 2016/8/28.
 */
public enum InteractPermissionType {
    FRIENDPERMISSION(1),//朋友可见
    ASSIGNPERMISSION(2) ;  //指定人可看

    private int type;
    private InteractPermissionType(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }

}
