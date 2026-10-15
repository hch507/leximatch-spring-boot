package org.leximatch.game.common.config;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


//Todo : 추후 json 외부 파일 방식으로 리펙토링 필요
//운영
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {

        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        GoogleCredentials credentials =
                GoogleCredentials.fromStream(
                        new ClassPathResource(
                                "leximatch-77825-firebase-adminsdk-fbsvc-c0f625d686.json"
                        ).getInputStream()
                );

        FirebaseOptions options =
                FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .build();

        FirebaseApp.initializeApp(options);
    }
}
// 개발
//@Configuration
//public class FirebaseConfig {
//
//    @PostConstruct
//    public void init() throws IOException {
//
//        if (!FirebaseApp.getApps().isEmpty()) {
//            return;
//        }
//
//        String credentialsPath =
//                System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
//
//        if (credentialsPath == null || credentialsPath.isBlank()) {
//            throw new IllegalStateException(
//                    "GOOGLE_APPLICATION_CREDENTIALS 환경변수가 설정되지 않았습니다."
//            );
//        }
//
//        GoogleCredentials credentials;
//
//        try (InputStream inputStream =
//                     new FileInputStream(credentialsPath)) {
//            credentials = GoogleCredentials.fromStream(inputStream);
//        }
//
//        FirebaseOptions options =
//                FirebaseOptions.builder()
//                        .setCredentials(credentials)
//                        .build();
//
//        FirebaseApp.initializeApp(options);
//    }
//}