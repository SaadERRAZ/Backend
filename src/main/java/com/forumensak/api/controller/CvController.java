package com.forumensak.api.controller;


import com.forumensak.api.model.cv.*;
import com.forumensak.api.model.social.Comment;
import com.forumensak.api.model.social.Post;
import com.forumensak.api.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class CvController {
    @Autowired
    CvService cvService;

    @GetMapping("")
    public ResponseEntity<?> getCv(@RequestHeader("Authorization") String authHeader) {
        return cvService.getCv(authHeader);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadImage(file, authHeader);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> uploadImageById(@PathVariable long id) {
        return cvService.getImageById(id);
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestHeader("Authorization") String authHeader) {
        return cvService.getImage(authHeader);
    }

    @PostMapping("/about")
    public ResponseEntity<?> uploadAbout(@RequestBody About about, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadAbout(about, authHeader);
    }


    @PostMapping("/experience")
    public ResponseEntity<?> uploadExperience(@RequestBody Experience experience, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadExperience(experience, authHeader);
    }

    @GetMapping("/experience")
    public ResponseEntity<?> getExperience(@RequestHeader("Authorization") String authHeader) {
        return cvService.getExperience(authHeader);
    }

    @DeleteMapping("/experience/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable long id) {
        return cvService.deleteExperience(id);
    }

    @PostMapping("/education")
    public ResponseEntity<?> uploadEducation(@RequestBody Education education, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadEducation(education, authHeader);
    }

    @GetMapping("/education")
    public ResponseEntity<?> getEducation(@RequestHeader("Authorization") String authHeader) {
        return cvService.getEducation(authHeader);
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable long id) {
        return cvService.deleteEducation(id);
    }

    @PostMapping("/award")
    public ResponseEntity<?> uploadAward(@RequestBody Award award, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadAward(award, authHeader);
    }

    @GetMapping("/award")
    public ResponseEntity<?> getAward(@RequestHeader("Authorization") String authHeader) {
        return cvService.getAward(authHeader);
    }

    @DeleteMapping("/award/{id}")
    public ResponseEntity<?> deleteAward(@PathVariable long id) {
        return cvService.deleteAward(id);
    }

    @PostMapping("/dev")
    public ResponseEntity<?> uploadDevLanguage(@RequestBody DevLanguage devLanguage, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadDevLanguage(devLanguage, authHeader);
    }

    @GetMapping("/dev")
    public ResponseEntity<?> getDevLanguage(@RequestHeader("Authorization") String authHeader) {
        return cvService.getDevLanguage(authHeader);
    }

    @DeleteMapping("/dev/{id}")
    public ResponseEntity<?> deleteDevLanguage(@PathVariable long id) {
        return cvService.deleteDevLanguage(id);
    }

    @PostMapping("/normal")
    public ResponseEntity<?> uploadNormalLanguage(@RequestBody NormalLanguage normalLanguage, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadNormalLanguage(normalLanguage, authHeader);
    }

    @GetMapping("/normal")
    public ResponseEntity<?> getNormalLanguage(@RequestHeader("Authorization") String authHeader) {
        return cvService.getNormalLanguage(authHeader);
    }

    @DeleteMapping("/normal/{id}")
    public ResponseEntity<?> deleteNormalLanguage(@PathVariable long id) {
        return cvService.deleteNormalLanguage(id);
    }

    @PostMapping("/software")
    public ResponseEntity<?> uploadSoftware(@RequestBody Software software, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadSoftware(software, authHeader);
    }

    @GetMapping("/software")
    public ResponseEntity<?> getSoftware(@RequestHeader("Authorization") String authHeader) {
        return cvService.getSoftware(authHeader);
    }

    @DeleteMapping("/software/{id}")
    public ResponseEntity<?> deleteSoftware(@PathVariable long id) {
        return cvService.deleteSoftware(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCvById(@PathVariable long id) {
        return cvService.getCvById(id);
    }

    @GetMapping("/search/{keyword}")
    public List<About> search(@PathVariable String keyword) {
        return cvService.listAll(keyword);
    }

    @PutMapping("/updateAbout/{id}")
    public ResponseEntity<?> updateAbout(@PathVariable long id, @RequestBody About aboutDetails) {
        return cvService.updateAbout(id, aboutDetails);
    }

    @PutMapping("/updateExperience/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable long id, @RequestBody Experience experienceDetails) {
        return cvService.updateExperience(id, experienceDetails);
    }

    @GetMapping("/getexperiencebyid/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable long id) {
        return cvService.getExperienceById(id);
    }

    @PutMapping("/updateEducation/{id}")
    public ResponseEntity<?> updateEducation(@PathVariable long id, @RequestBody Education educationDetails) {
        return cvService.updateEducation(id, educationDetails);
    }

    @GetMapping("/geteducationbyid/{id}")
    public ResponseEntity<?> getEducationById(@PathVariable long id) {
        return cvService.getEducationById(id);
    }

    @PutMapping("/flag/{id}")
    public ResponseEntity<?> turnFlag(@PathVariable long id) {
        return cvService.turnFlag(id);
    }

    @PostMapping("/uploadPost")
    public ResponseEntity<?> uploadPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadPost(post, authHeader);
    }

    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts() {
        return cvService.getPosts();
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable long id, @RequestBody Post postDetails) {
        return cvService.updatePost(id, postDetails);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable long id) {
        return cvService.deletePost(id);
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> uploadComment(@PathVariable(value = "postId") long postId, @RequestBody Comment comment, @RequestHeader("Authorization") String authHeader) {
        return cvService.uploadComment(postId, comment, authHeader);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable long id, @RequestBody Comment commentDetails) {
        return cvService.updateComment(id, commentDetails);
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        return cvService.getAllStudents();
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        return cvService.deleteComment(id);
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<?> getComments(@PathVariable(value = "postId") long postId) {
        return cvService.getComments(postId);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enableAccount(@PathVariable(value = "id") long id) {
        return cvService.enableAccount(id);
    }
}

