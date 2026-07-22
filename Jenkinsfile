pipeline {
    agent any



    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Copy Firebase Key') {
            steps {
                sh '''
                    cp /run/secrets/firebase/leximatch-77825-firebase-adminsdk-fbsvc-c0f625d686.json \
                       src/main/resources/
                '''
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
                    def VERSION = sh(
                        script: "grep '^version' build.gradle | cut -d\"'\" -f2",
                        returnStdout: true
                    ).trim()
                    sh '''
                         docker build \
                          -t leximatch-spring:${VERSION} \
                          -t leximatch-spring:latest .
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop leximatch-spring || true
                    docker rm leximatch-spring || true

                    docker run -d \
                      --name leximatch-spring \
                      --network leximatch-net \
                      --restart unless-stopped \
                      -p 8080:8080 \
                      --env-file /run/secrets/spring.env \
                      leximatch-spring:${VERSION}

                    docker image prune -f
                '''
            }
        }
    }

    post {
        success {
            echo "Spring Deploy Success! Version : ${VERSION}"
        }

        failure {
            echo 'Spring Deploy Failed!'
            sh 'docker logs leximatch-spring || true'
        }
    }
}