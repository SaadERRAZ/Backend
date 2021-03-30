package com.forumensak.api.controller;

import com.forumensak.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String autHeader) {
        return profileService.getProfile(autHeader);
    }

    @GetMapping("/{var}")
    public ResponseEntity<?> searchUsers(@PathVariable String var) {
        return profileService.searchUsers(var);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        return profileService.getAll();
    }

    @PostMapping("/add/{id}")
    public  ResponseEntity<?> connectTo(@RequestHeader("Authorization") String authHeader,@PathVariable Long id)
    {
        return profileService.connectTo(authHeader,id);
    }
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> accept(@RequestHeader("Authorization") String authHeader,@PathVariable Long id)
    {
        return profileService.accept(authHeader,id);
    }
    @PostMapping("/notifications")
    public ResponseEntity<?> handleNotifications(@RequestHeader("Authorization")String authHeader)
    {
        return  profileService.handleNotifications(authHeader);
    }
    @DeleteMapping("/disconnect/{id}")
    public ResponseEntity<?> disconnect(@RequestHeader("Authorization") String authHeader,@PathVariable Long id)
    {
        return profileService.disconnect(authHeader,id);
    }
    @DeleteMapping("/notification/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id){
        return profileService.deleteNotification(id);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authHeader)
    {
        return profileService.getAll(authHeader);
    }
}
