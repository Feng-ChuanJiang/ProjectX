package com.cci.projectx.core.controller;

import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.body.IMUserBody;
import com.easemob.server.example.comm.body.ModifyNicknameBody;
import com.easemob.server.example.comm.body.ResetPasswordBody;
import com.easemob.server.example.comm.wrapper.BodyWrapper;
import com.easemob.server.example.comm.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 33303 on 2016/9/8.
 */
@RestController
@RequestMapping("/")
public class Neo4jTest {
    @Autowired
    IMUserAPI imUserAPI;

    @GetMapping(value = "/huanxin")
    public ResponseWrapper getPhotoById(){

        //添加
        BodyWrapper userBody = new IMUserBody("18328301233", "123456", "xin心上人");
        imUserAPI.createNewIMUserSingle(userBody);
        //修改密码
        BodyWrapper passwordBody = new ResetPasswordBody("654321");
        imUserAPI.modifyIMUserPasswordWithAdminToken("18328301233",passwordBody);
        //修改昵称
        BodyWrapper nicknameBody = new ModifyNicknameBody("心上人1");
        imUserAPI.modifyIMUserNickNameWithAdminToken("18328301233",nicknameBody);

        //删除用户
        //imUserAPI.deleteIMUserByUserName("18328301233");



        ResponseWrapper responseWrapper=(ResponseWrapper)imUserAPI.getIMUsersByUserName("18328301233");
        String uuid = ((ObjectNode) responseWrapper.getResponseBody()).get("entities").get(0).get("uuid").asText();
        return  responseWrapper;

    }


}
