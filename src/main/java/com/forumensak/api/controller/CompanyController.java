package com.forumensak.api.controller;


import com.forumensak.api.model.company.AboutCompany;
import com.forumensak.api.model.social.Comment;
import com.forumensak.api.model.social.Post;
import com.forumensak.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/comp")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("")
    public ResponseEntity<?> getCompany(@RequestHeader("Authorization") String authHeader) {
        return companyService.getCompany(authHeader);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadCompanyImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        return companyService.uploadCompanyImage(file, authHeader);

    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> uploadCompanyImageById(@PathVariable long id) {
        return companyService.getCompanyImageById(id);
    }

    @GetMapping("/image")
    public ResponseEntity<?> getCompanyImage(@RequestHeader("Authorization") String authHeader) {
        return companyService.getCompanyImage(authHeader);
    }

    @PostMapping("/about")
    public ResponseEntity<?> uploadAboutCompany(@RequestBody AboutCompany aboutCompany, @RequestHeader("Authorization") String authHeader) {
        return companyService.uploadAboutCompany(aboutCompany, authHeader);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/search/{keyword}")
    public List<AboutCompany> search(@PathVariable String keyword) {
        return companyService.listAll(keyword);
    }

    @PutMapping("/flag/{id}")
    public ResponseEntity<?> turnFlag(@PathVariable long id) {
        return companyService.turnFlag(id);
    }

    @PostMapping("/uploadPost")
    public ResponseEntity<?> uploadPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader) {
        return companyService.uploadPost(post, authHeader);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable long id, @RequestBody Post postDetails) {
        return companyService.updatePost(id, postDetails);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable long id) {
        return companyService.deletePost(id);
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> uploadComment(@PathVariable(value = "postId") long postId, @RequestBody Comment comment, @RequestHeader("Authorization") String authHeader) {
        return companyService.uploadComment(postId, comment, authHeader);
    }

    //
    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable long id, @RequestBody Comment commentDetails) {
        return companyService.updateComment(id, commentDetails);
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        return companyService.deleteComment(id);
    }

    @PutMapping("/about/{id}")
    public ResponseEntity<?> updateAbout(@PathVariable long id, @RequestBody AboutCompany aboutCompany) {
        return companyService.updateAbout(id,aboutCompany);
    }

}


