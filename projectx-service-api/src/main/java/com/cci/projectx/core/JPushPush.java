package com.cci.projectx.core;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 33303 on 2016/9/29.
 */
@Service
public class JPushPush {

    @Autowired
    JPushClient jPushClient;

    public static final String FRIEND_APPLY="申请加你为好友";
    public static final String FRIEND_CONSENT="通过了你的好友验证";
    public static final String RUN_APPLY="申请加你为跑友";
    public static final String RUN_CONSENT="通过了你的跑友验证";
    public static final String SEMINAR_DISCUSS="邀请你加入研讨会";
    public static final String INTERACT_INVITE="提醒你查看他的新动态";
    public static final String INTERACT_PRAISE="赞了你的动态";
    //public static final String INTERACT_FORWARD="转发了你的动态";
    public static final String COMMENT_FRIEND="的评论提及到了你";




    /**
     * IOS推送通知
     * @param userId
     * @param   map
     * @return
     */
    public  PushResult buildPushObject_all_alias_alert(Long userId,String message,Map<String,String>  map )  {
        //定义通知
        PushPayload payload =PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag(userId.toString()))
                .setNotification(Notification.ios(message,  map))
                .build();
        try {
            //发送
            return jPushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实体转Map
     * @param bean
     * @return
     */
    public  Map convertBean(Object bean)  {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if(result instanceof Collection){continue;}
                    if (result != null) {
                        returnMap.put(propertyName, String.valueOf(result));
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        }catch (Exception e){
            return null;
        }
        return returnMap;
    }

}
