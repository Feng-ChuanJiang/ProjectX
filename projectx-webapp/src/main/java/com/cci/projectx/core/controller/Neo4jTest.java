package com.cci.projectx.core.controller;

import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.neorepository.SchoolNeoRepository;
import com.cci.projectx.core.neorepository.UserNeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by 33303 on 2016/9/8.
 */
@RestController
@RequestMapping("/")
public class Neo4jTest {
    @Autowired
    Neo4jOperations neo4jTemlate;
    @Autowired
    UserNeoRepository userRepository;
    @Autowired
    SchoolNeoRepository schoolNeoRepository;
    @GetMapping(value = "/neott")
    public Collection<UserNeo> getUser(){
       // boolean userNeo=userRepository.findByUserId(new Long(1));

       // schoolNeoRepository.getConnecTwoUserFromName(new Long(5),"成都大学");


        return  schoolNeoRepository.getConnecTwoUserFromName(new Long(5),"成都大学");
    }


}
