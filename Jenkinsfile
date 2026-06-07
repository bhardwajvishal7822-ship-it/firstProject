pipeline {
    agent any

    stages {
        stage('Stop Old Container') {
            steps {
                echo 'Stopping and removing old container if exists...'
                sh 'docker stop my-running-site || true'
                sh 'docker rm my-running-site || true'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building new Docker image from GitHub code...'
                sh 'docker build -t my-html-app .'
            }
        }

        // 🌟 NAYA STAGE: SonarQube Code Quality Check
        stage('SonarQube Analysis') {
            steps {
                // Yeh block automatic aapke Jenkins credentials se token nikalega
                withSonarQubeEnv('SonarQube') {
                    echo '🔍 Running SonarQube Code Analysis...'
                    
                    // FIXED: 
                    // 1. -e SONAR_TOKEN=$SONAR_AUTH_TOKEN se token container ke andar pass hoga (401 Fix)
                    // 2. -v "$(pwd)"/.scannerwork:/usr/src/.scannerwork se raseed Jenkins ko mil jayegi (Warning Fix)
                    sh '''
                        docker run --rm \
                        -v "$(pwd)":/usr/src \
                        -v "$(pwd)"/.scannerwork:/usr/src/.scannerwork \
                        -e SONAR_TOKEN=$SONAR_AUTH_TOKEN \
                        sonarsource/sonar-scanner-cli
                    '''
                }
            }
        }

        stage('Deploy New Container') {
            steps {
                echo 'Deploying new container on port 8083...'
                sh 'docker run -d -p 8083:80 --name my-running-site my-html-app'
                echo 'Waiting for 3 seconds to let the server start...'
                sh 'sleep 3' 
            }
        }

        stage('Automated Java Test') {
            steps {
                echo '🧪 Compiling and Running Java Integration Tests inside Docker...'
                
                // FIXED: openjdk:17 ko badalkar eclipse-temurin:17 kar diya hai
                sh 'docker run --rm --network=host -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp eclipse-temurin:17 javac WebpageTest.java'
                sh 'docker run --rm --network=host -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp eclipse-temurin:17 java WebpageTest'
            }
        }
    }

    post {
        failure {
            echo '🚨 TEST YA ANALYSIS FAIL HO GAYA! Bad code detected. Stopping the container immediately...'
            sh 'docker stop my-running-site || true'
        }
        success {
            echo '🚀 SAARE TESTS PASS & CODE SCANNED! Website is live and verified.'
        }
    }
}