
package com.cci.projectx.core.controller;

import cn.jpush.api.push.PushResult;
import com.cci.projectx.core.JPushPush;
import com.cci.projectx.core.model.UserModel;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 33303 on 2016/9/8.
 */
@RestController
@RequestMapping("/")
public class JiguangTest{
    @Autowired
    JPushPush jPushPush;

    @GetMapping(value = "/jiguang")
    public PushResult getPhotoById() {
        Map<String,String > map=new HashedMap();
        map=  jPushPush.convertBean(new UserModel());


       return jPushPush.buildPushObject_all_alias_alert(new Long(3),"涛涛请求加你为好友",map);
    }

}
