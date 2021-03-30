package com.forumensak.api.service;

import com.forumensak.api.exception.AppException;
import com.forumensak.api.model.User;
import com.forumensak.api.model.social.*;
import com.forumensak.api.payload.ConversationPayload;
import com.forumensak.api.payload.ResponseMessage;
import com.forumensak.api.repository.FriendShipRepository;
import com.forumensak.api.repository.NotificationRepository;
import com.forumensak.api.repository.UserRepository;
import com.forumensak.api.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FriendShipRepository friendShipRepository;

    public ResponseEntity<?> getProfile(String authHeader) {
        String jwt = getJwtFromHeader(authHeader);
        long id = jwtTokenProvider.getUserIdFromJWT(jwt);
        User user = userRepository.findById(id).orElseThrow(() -> new AppException("User id doesn't exist"));
        return ResponseEntity.ok(user);
    }

    private String getJwtFromHeader(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7, authHeader.length());
        }
        return null;
    }

    public ResponseEntity<?> searchUsers(String var) {
        List<User> users = userRepository.findByNameIgnoreCaseContainingOrUsernameIgnoreCaseContainingOrCompanyNameIgnoreCaseContaining(var, var, var);
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<?> getAll() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<?> connectTo(String authHeader, Long id) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id1 = jwtTokenProvider.getUserIdFromJWT(jwt);
            User userMe = userRepository.findById(id1).orElseThrow(() -> new AppException("User Me id doesn't exist"));
            User userOther = userRepository.findById(id).orElseThrow(() -> new AppException("User Other id doesn't exist"));
            Friendship friendship = new Friendship();
            friendship.setSender(userMe);
            friendship.setReceiver(userOther);
            friendship.setStatus(false);
            friendship.setId(new FriendshipKey(userMe.getId(), userOther.getId()));
            Notification notification = new Notification();
            notification.setMessage("Followed you");
            notification.setStatus(false);
            notification.setOwner(userOther);
            if (userMe.getRoles().iterator().next().getId() == 1) {
                notification.setOwnerName(userMe.getCv().getAbout().getFirstName() + " " + userMe.getCv().getAbout().getLastName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCv().getImage());
            } else if (userMe.getRoles().iterator().next().getId() == 3) {
                notification.setOwnerName(userMe.getCompany().getAboutCompany().getName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCompany().getCompanyImage());
            }
            notificationRepository.save(notification);
            friendShipRepository.save(friendship);
            userRepository.save(userMe);
            userRepository.save(userOther);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            message = "Could not connect to user with id " + id;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }

    }

    public ResponseEntity<?> accept(String authHeader, Long id) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id1 = jwtTokenProvider.getUserIdFromJWT(jwt);
            System.out.println(id);
            System.out.println(id1);
            User userMe = userRepository.findById(id1).orElseThrow(() -> new AppException("User Me id doesn't exist"));
            User userOther = userRepository.findById(id).orElseThrow(() -> new AppException("User Other id doesn't exist"));
            Friendship friendship1 = userOther.getFriendshipSended().stream().filter(friendship -> friendship.getId().getSenderId().equals(id)).collect(Collectors.toList()).get(0);
            friendship1.setStatus(true);
            friendship1.setConversation(new Conversation());
            System.out.println("ok");
            Notification notification1 = userMe.getNotifications().stream().filter(notification -> notification.getOwner().getId().equals(id1) && notification.getOwnersId()==id && !notification.isStatus()).collect(Collectors.toList()).get(0);
            System.out.println(notification1.isStatus());
            notification1.setStatus(true);
            Notification notification = new Notification();
            notification.setMessage("Accepted your connection");
            notification.setStatus(false);
            notification.setOwner(userOther);
            if (userMe.getRoles().iterator().next().getId() == 1) {
                notification.setOwnerName(userMe.getCv().getAbout().getFirstName() + " " + userMe.getCv().getAbout().getLastName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCv().getImage());
            } else if (userMe.getRoles().iterator().next().getId() == 3) {
                notification.setOwnerName(userMe.getCompany().getAboutCompany().getName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCompany().getCompanyImage());
            }
            notificationRepository.save(notification);
            userRepository.save(userMe);
            userRepository.save(userOther);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            message = "Could not accept user with id " + id;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }

    public ResponseEntity<?> handleNotifications(String authHeader) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            User user = userRepository.findById(id).orElseThrow(() -> new AppException("User Me id doesn't exist"));
            user.getNotifications().stream().forEach(notification -> {
                if (notification.getMessage().equals("Accepted your connection") || notification.getMessage().equals("Disconnected with you")) {
                    notification.setStatus(true);
                }
            });
            userRepository.save(user);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            message = "Error";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    public ResponseEntity<?> disconnect(String authHeader,long id) {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id1 = jwtTokenProvider.getUserIdFromJWT(jwt);
            User userMe = userRepository.findById(id1).orElseThrow(() -> new AppException("User Me id doesn't exist"));
            User userOther = userRepository.findById(id).orElseThrow(() -> new AppException("User Other id doesn't exist"));
            Friendship friendship = friendShipRepository.findByIdSenderIdAndIdReceiverId(id, id1);
            if (friendship == null) {
                friendship = friendShipRepository.findByIdSenderIdAndIdReceiverId(id1, id);
                if (friendship == null) return ResponseEntity.ok("Friendship not found");
            }
            friendShipRepository.delete(friendship);
            Notification notification = new Notification();
            notification.setMessage("Disconnected with you");
            notification.setStatus(false);
            notification.setOwner(userOther);
            if (userMe.getRoles().iterator().next().getId() == 1) {
                notification.setOwnerName(userMe.getCv().getAbout().getFirstName() + " " + userMe.getCv().getAbout().getLastName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCv().getImage());
            } else if (userMe.getRoles().iterator().next().getId() == 3) {
                notification.setOwnerName(userMe.getCompany().getAboutCompany().getName());
                notification.setOwnersId(userMe.getId());
                notification.setOwnerUsername(userMe.getUsername());
                notification.setOwnerImage(userMe.getCompany().getCompanyImage());
            }
            notificationRepository.save(notification);
            userRepository.save(userMe);
            userRepository.save(userOther);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            message = "Error";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    public ResponseEntity<?> deleteNotification(long id)
        {
            Notification notification=notificationRepository.findById(id).orElseThrow(() -> new AppException("Notification doesn't exist"));
            notificationRepository.delete(notification);
            return ResponseEntity.ok("Success");
        }
    public ResponseEntity<?> getAll(String authHeader)
    {
        String message = "";
        try {
            String jwt = getJwtFromHeader(authHeader);
            long id = jwtTokenProvider.getUserIdFromJWT(jwt);
            List<Friendship> friendshipList = friendShipRepository.findByIdSenderIdOrIdReceiverId(id,id);
            List<ConversationPayload> conversationPayloadList=new ArrayList<>();
            friendshipList.stream().forEach(friendship -> {
                ConversationPayload conversationPayload=new ConversationPayload();
                Long id1;
                if(friendship.getSender().getId()==id)
                {
                    id1=friendship.getReceiver().getId();
                }
                else
                {
                    id1=friendship.getSender().getId();
                }
                User user=userRepository.findById(id1).orElseThrow(()->new AppException("User not found"));
                conversationPayload.setId(user.getId());
                if (user.getRoles().iterator().next().getId() == 1) {
                    conversationPayload.setImg(user.getCv().getImage());
                    conversationPayload.setUsername(user.getCv().getAbout().getFirstName()+" "+user.getCv().getAbout().getLastName());
                } else if (user.getRoles().iterator().next().getId() == 3) {
                    conversationPayload.setImg(user.getCompany().getCompanyImage());
                    conversationPayload.setUsername(user.getCompany().getAboutCompany().getName());
                }
                if(friendship.getConversation().getMessageList().isEmpty())
                {
                    Message message1=new Message();
                    message1.setMessage("Be the first to give light to this conversation");
                    conversationPayload.setLastMessage(message1);
                    conversationPayload.setDate(friendship.getConversation().getUpdatedAt());
                    conversationPayloadList.add(conversationPayload);
                }
                else
                {
                    conversationPayload.setLastMessage(friendship.getConversation().getMessageList().get(0));
                    conversationPayload.setDate(friendship.getConversation().getUpdatedAt());
                    conversationPayloadList.add(conversationPayload);
                }

            });
            return ResponseEntity.ok(conversationPayloadList);
        }
        catch (Exception e)
        {
            message = "Error";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }

    }

