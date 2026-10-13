// pipeline {
//     agent any
//
//
//
//     stages {
//
//         stage('Checkout') {
//             steps {
//                 checkout scm
//             }
//         }
//
//         stage('Copy Firebase Key') {
//             steps {
//                 sh '''
//                     cp /run/secrets/firebase/leximatch-77825-firebase-adminsdk-fbsvc-c0f625d686.json \
//                        src/main/resources/
//                 '''
//             }
//         }
//         stage('Build Spring') {
//             steps {
//                 sh '''
//                     chmod +x gradlew
//                     ./gradlew clean bootJar
//                 '''
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     env.VERSION = sh(
//                         script: "awk -F\"'\" '/^version/ {print \$2}' build.gradle",
//                         returnStdout: true
//                     ).trim()
//
//                     echo "VERSION=${env.VERSION}"
//
//                     sh """
//                         docker build \
//                           -t leximatch-spring:${env.VERSION} \
//                           -t leximatch-spring:latest .
//                     """
//                 }
//             }
//         }
// //         운영
// //         stage('Deploy') {
// //             steps {
// //                 sh """
// //
// //                     docker stop leximatch-spring-1 || true
// //                                         docker rm leximatch-spring-1 || true
// //
// //                     docker run -d \
// //                       --name leximatch-spring-1 \
// //                       --network leximatch-net \
// //                       --restart unless-stopped \
// //                       --env-file /run/secrets/spring.env \
// //                       leximatch-spring:${env.VERSION}
// //
// //                     docker network connect ai-net leximatch-spring-1
// //                     docker image prune -f
// //                 """
// //             }
// //         }
// //
// //      }
// //           운영
//           stage('Deploy') {
//                     steps {
//                         sh """
//                             if docker container inspect leximatch-spring-backup >/dev/null 2>&1; then
//                                 echo "ERROR: 기존 백업 컨테이너가 존재합니다."
//                                 exit 1
//                             fi
//
//                             if docker container inspect leximatch-spring-1 >/dev/null 2>&1; then
//                                             docker stop leximatch-spring-1
//                                             docker rename leximatch-spring-1 leximatch-spring-backup
//                             fi
//
//
//
//                             docker run -d \
//                               --name leximatch-spring-1 \
//                               --network leximatch-net \
//                               --restart unless-stopped \
//                               --env-file /run/secrets/spring.env \
//                               leximatch-spring:${env.VERSION}
//
//                             docker network connect ai-net leximatch-spring-1
//                             docker image prune -f
//                         """
//                     }
//                 }
//
//           }
//
// //         // 개발
// //         stage('Deploy') {
// //             steps {
// //                 sh """
// //                     docker stop leximatch-spring-1 || true
// //                     docker rm leximatch-spring-1 || true
// //
// //
// //                     # Spring 1
// //                     docker run -d \
// //                       --name leximatch-spring-1 \
// //                       --network leximatch-net \
// //                       --restart unless-stopped \
// //                       --env-file /run/secrets/spring.env \
// //                       leximatch-spring:${env.VERSION}
// //
// //                     docker network connect ai-net leximatch-spring-1
// //
// //                     docker image prune -f
// //                 """
// //             }
// //         }
// //     }
//     post {
//         success {
//             echo "Spring Deploy Success! Version : ${env.VERSION}"
//         }
//
//         failure {
//             echo 'Spring Deploy Failed!'
//             sh 'docker logs leximatch-spring || true'
//         }
//     }
//
// }
// ---------------------------------------------------------개발 환경 -----------------------------------

pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = 'hch507/leximatch-spring'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Spring') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew clean bootJar
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    env.VERSION = sh(
                        script: """awk -F '"' '/^[[:space:]]*version[[:space:]]*=/ {print \$2; exit}' build.gradle""",
                        returnStdout: true
                    ).trim()

                    if (!env.VERSION) {
                        error('build.gradle에서 버전을 찾을 수 없습니다.')
                    }

                    echo "VERSION=${env.VERSION}"
                }

                sh '''
                    docker build \
                      -t "$DOCKERHUB_REPO:$VERSION" \
                      -t "$DOCKERHUB_REPO:latest" .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKERHUB_USERNAME',
                        passwordVariable: 'DOCKERHUB_TOKEN'
                    )
                ]) {
                    sh '''
                        set -eu
                        set +x

                        echo "$DOCKERHUB_TOKEN" | docker login \
                          -u "$DOCKERHUB_USERNAME" \
                          --password-stdin

                        docker push "$DOCKERHUB_REPO:$VERSION"
                        docker push "$DOCKERHUB_REPO:latest"

                        docker logout
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "Docker Hub 업로드 성공! Version: ${env.VERSION}"
        }

        failure {
            echo '빌드 또는 Docker Hub 업로드 실패!'
        }
    }
}