package com.xiaorui.controller;

import com.xiaorui.domain.AB;
import com.xiaorui.myspringmvc.ForwarContent;
import com.xiaorui.myspringmvc.Param;
import com.xiaorui.myspringmvc.PlaceCod;
import com.xiaorui.myspringmvc.PlaceCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 12:47
 **/

public class Login {

//    @Param("a") String a,@Param("b") int b
    @PlaceCode({PlaceCod.request,PlaceCod.application,PlaceCod.session})
    public ForwarContent login(ArrayList<String> map){
//        System.out.println(map);
//        System.out.println(a);
//        System.out.println(b);
        for (int i = 0; i < map.size(); i++) {
            String s = map.get(i);
            System.out.println(s);
        }
        HashMap hashMap = new HashMap<>();
        hashMap.put("c","y");
        ForwarContent forwarContent = new ForwarContent("index1.jsp"
                ,hashMap);
        return forwarContent;
    }


}
