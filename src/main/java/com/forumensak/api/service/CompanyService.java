package com.forumensak.api.service;

import com.forumensak.api.exception.AppException;
import com.forumensak.api.model.User;
import com.forumensak.api.model.company.AboutCompany;
import com.forumensak.api.model.cv.About;
import com.forumensak.api.model.social.Comment;
import com.forumensak.api.model.social.Post;
import com.forumensak.api.payload.ResponseMessage;
import com.forumensak.api.repository.AboutCompanyRepository;
import com.forumensak.api.repository.CommentRepository;
import com.forumensak.api.repository.PostRepository;
import com.forumensak.api.repository.UserRepository;
import com.forumensak.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    FilesStorageService storageService;
    @Autowired
    AboutCompanyRepository aboutCompanyRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;


    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }
    public ResponseEntity<?> updateAbout(long id, AboutCompany aboutDetails) {
        String message = "";
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            AboutCompany aboutCompany = user.getCompany().getAboutCompany();
            aboutCompany.setAddress(aboutDetails.getAddress());
            aboutCompany.setCity(aboutDetails.getCity());
            aboutCompany.setBio(aboutDetails.getBio());
            aboutCompany.setNumber(aboutDetails.getNumber());
            AboutCompany updatedAbout = aboutCompanyRepository.save(aboutCompany);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAbout);
        } catch (Exception e) {
            message = "Could not edit the about section due to a server error!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> uploadCompanyImage(MultipartFile file, String authHeader) {
        String message = "";
        try {
            storageService.save(file);
            message = "Uploaded the file successfully!";
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));

            user.getCompany().setCompanyImage(file.getOriginalFilename());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> getCompanyImage(String authHeader) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            Resource resource = storageService.load(user.getCompany().getCompanyImage());
            return ResponseEntity.status(HttpStatus.OK).body(encoder(resource.getURI().getPath()));
        } catch (Exception e) {
            message = "Could not upload the file: !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> getCompanyImageById(long id) {
        String message = "";
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            Resource resource = storageService.load(user.getCompany().getCompanyImage());
            return ResponseEntity.status(HttpStatus.OK).body(encoder(resource.getURI().getPath()));
        } catch (Exception e) {
            message = "Could not upload the file: !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> uploadAboutCompany(AboutCompany aboutCompany, String authHeader) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            user.getCompany().getAboutCompany().setName(user.getCompanyName());
            user.getCompany().getAboutCompany().setNumber(aboutCompany.getNumber());
            user.getCompany().getAboutCompany().setSocials(aboutCompany.getSocials());
            user.getCompany().getAboutCompany().setCity(aboutCompany.getCity());
            user.getCompany().getAboutCompany().setBio(aboutCompany.getBio());
            user.getCompany().getAboutCompany().setAddress(aboutCompany.getAddress());
            userRepository.save(user);
            message = "Uploaded company info!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public  ResponseEntity<?> getAllCompanies()
    {
        try {
            List<User> users=userRepository.findAll();
            users=users.stream().filter(user -> user.getRoles().iterator().next().getId()==3).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        catch (Exception e) {
            String message = "Error!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    public ResponseEntity<?> getCompany(String authHeader) {
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist")));
            User user = userOptional.get();
            return ResponseEntity.ok(user.getCompany());
        } catch (Exception e) {
            String message = "Could not upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> getCompanyById(long id) {
        try {
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist")));
            User user = userOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            String message = "Error!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public List<AboutCompany> listAll(String keyword) {
        if (keyword != null) {
            return aboutCompanyRepository.searchCompany(keyword);
        }
        return aboutCompanyRepository.findAll();
    }

    public ResponseEntity<?> turnFlag(long id) {
        String message = "";
        try {
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist")));
            User user = userOptional.get();
            user.getCompany().setFlag(true);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user.getCompany().isFlag());
        } catch (Exception e) {
            message = "Couldn't!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> uploadPost(Post post, String authHeader) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist")));
            User user = userOptional.get();
            Post newPost = new Post();
            newPost.setOwner(user);
            newPost.setOwnerFirstName(user.getName());
            newPost.setCompanyName(user.getCompany().getAboutCompany().getName());
            newPost.setOwnerImage(user.getCompany().getCompanyImage());
            newPost.setMessage(post.getMessage());
            newPost.setOwnersId(id);
            newPost.setOwnerUsername(user.getUsername());
            newPost.setRole(post.getRole());
            postRepository.save(newPost);
            userRepository.save(user);
            //returns the location of the created post
            return ResponseEntity.status(HttpStatus.OK).body(user.getCompany().getAboutCompany().getNumber());
        } catch (Exception e) {
            message = "Could not upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> deletePost(long id) {
        postRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfuly");
    }

    public ResponseEntity<?> updatePost(long id, Post postDetails) {
        String message = "";
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            post.setMessage(postDetails.getMessage());
            Post updatedPost = postRepository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
        } catch (Exception e) {
            message = "Could not edit post!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> uploadComment(long postId, Comment comment, String authHeader) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist")));
            User user = userOptional.get();
            Comment newComment = new Comment();
            Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(postId).orElseThrow(() -> new AppException("User id doesn't exist")));
            Post post = postOptional.get();
            newComment.setOwner(user);
            newComment.setPost(post);
            newComment.setOwnerFirstName(user.getName());
            newComment.setOwnerLastName(user.getCompanyName());
            newComment.setOwnerImage(user.getCompany().getCompanyImage());
            newComment.setMessage(comment.getMessage());
            newComment.setPostsId(postId);
            newComment.setOwnersId(id);
            newComment.setOwnerUsername(user.getUsername());
            newComment.setRole(comment.getRole());
            commentRepository.save(newComment);
            userRepository.save(user);
            //returns the location of the created post
            return ResponseEntity.status(HttpStatus.OK).body(newComment);
        } catch (Exception e) {
            message = "Could not upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<?> deleteComment(long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfuly");
    }

    public ResponseEntity<?> updateComment(long id, Comment commentDetails) {
        String message = "";
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
            comment.setMessage(commentDetails.getMessage());
            Comment updatedComment = commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
        } catch (Exception e) {
            message = "Could not edit post!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    private String getJwtFromHeader(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
