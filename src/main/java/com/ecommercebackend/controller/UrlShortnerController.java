//package com.ecommercebackend.controller;
//
//import java.lang.annotation.Target;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ecommercebackend.model.Url;
//import com.ecommercebackend.payload.request.UrlRequest;
//import com.ecommercebackend.urlmanager.UrlManager;
//import com.ecommercebackend.urlmanagerimpl.urlmanagerimpl;
//
//@RestController
//@RequestMapping("/url")
//public class UrlShortnerController {
//	
//	@Value("${redis.ttl}")
//	private long ttl;
//
//    @Autowired
//    private urlmanagerimpl urlManager;
//    
//    @Autowired
//    private RedisTemplate<String, Url> redisTemplate;
//
//    @PostMapping("/shorten")
//    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequest url) {
//        Url shortUrlEntry = urlManager.shorten(url.getUrl());
//        redisTemplate.opsForValue().set(shortUrlEntry.getKey(), shortUrlEntry, ttl, TimeUnit.SECONDS);
//        return ResponseEntity.status(201).body(shortUrlEntry);
//    }
//
//    @GetMapping("/meow/{key}")
//    public ResponseEntity getUrl(@PathVariable("key") String key) {
//        String url = urlManager.getUrl(key);
//        System.out.println(url);
//        return ResponseEntity.ok(url);
//
//    }
//}