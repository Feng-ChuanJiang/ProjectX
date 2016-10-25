package com.cci.projectx.core;

import cn.jpush.api.JPushClient;
import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.ClientContext;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.easemob.server.example.comm.EasemobRestAPIFactory;

/**
 * Created by haiquanli on 16/7/18.
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
    //极光推送
    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    //阿里大于
    @Value("${sms.serverUrl}")
    private String smsServerUrl;
    @Value("${sms.appKey}")
    private String smsAppKey;
    @Value("${sms.appSecret}")
    private String smsAppSecret;


    /**
     * 阿里大于
     *
     * @return
     */
    @Bean
    public TaobaoClient taobaoClient() {
        return new DefaultTaobaoClient(smsServerUrl, smsAppKey, smsAppSecret);
    }

    /**
     * 环信配置文件
     *
     * @return
     */
    @Bean
    public EasemobRestAPIFactory easemobRestAPIFactory() {
        return ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
    }
    /**
     * 环信用户管理
     *
     * @return
     */
    @Bean
    public IMUserAPI imUserAPI(){
        return  (IMUserAPI)easemobRestAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
    }


    /**
     * 极光推送
     *
     * @return
     */
    @Bean
    public JPushClient jPushClient() {
        return new JPushClient(masterSecret, appKey);
    }

}
