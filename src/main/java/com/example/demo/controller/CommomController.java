package com.example.demo.controller;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by 何峥言 on 2017/6/20.
 */
@Controller
@RequestMapping("/container")
public class CommomController {
    @GetMapping()
    public String toIndex(){
        return "index";
    }
    @RequestMapping(value = "/uptoken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> makeToken() {
        //todo
        // 需要修改AK、SK、bucketName
        Config.ACCESS_KEY = "RT2r0qxRFiBCR25TlIkLLcWfrKEZuQAD9V0XHsO7";
        Config.SECRET_KEY = "RYaqcpeWVyT2B-2TQnlXXFmKz34M7NUwzuMsjDVO";
        Mac mac =new Mac( Config.ACCESS_KEY,Config.SECRET_KEY);
        String bucketName = "ortynkhb7.bkt.clouddn.com";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        try {
            String uptoken = putPolicy.token(mac);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("uptoken",uptoken);
            return new ResponseEntity(jsonObject, HttpStatus.OK);
        } catch (AuthException e) {
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }
}
