package org.leximatch.game.application.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public String send(String token, String Title, String body)
            throws FirebaseMessagingException {

        Message message =
                Message.builder()
                        .setToken(token)
                        .setNotification(
                                Notification.builder()
                                        .setTitle(Title)
                                        .setBody(body)
                                        .build()
                        )
                        .setAndroidConfig(
                                AndroidConfig.builder()
                                        .setNotification(
                                                AndroidNotification.builder()
                                                        .setChannelId("leximatch_default")
                                                        .setPriority(AndroidNotification.Priority.HIGH)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();



        return FirebaseMessaging
                .getInstance()
                .send(message);
    }
}