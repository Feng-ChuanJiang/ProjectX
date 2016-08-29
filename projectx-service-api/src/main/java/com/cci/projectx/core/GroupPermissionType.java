package com.cci.projectx.core;

/**
 * Created by 33303 on 2016/8/28.
 */
public enum GroupPermissionType {
    FRIENDPERMISSION(1),//朋友可见
    ALLPERMISSION(2),   //说有人可见
    GROUPPERMISSION(3); //指定圈可见

    private int type;
    private GroupPermissionType(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }

}
