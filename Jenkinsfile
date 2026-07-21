pipeline {
    agent any

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
                sh '''
                    docker build -t leximatch-spring:${BUILD_NUMBER} .
                '''
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
                      leximatch-spring:${BUILD_NUMBER}

                    docker image prune -f
                '''
            }
        }
    }

    post {
        success {
            echo 'Spring Deploy Success!'
        }

        failure {
            echo 'Spring Deploy Failed!'
            sh 'docker logs leximatch-spring || true'
        }
    }
}