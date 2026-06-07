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
                withSonarQubeEnv('SonarQube') {
                    echo '🔍 Running SonarQube Code Analysis...'
                    
                    // FIXED: Token ko environment variable se uthakar Docker container ke andar pass kiya hai
                    sh 'docker run --rm -v "$(pwd)":/usr/src sonarsource/sonar-scanner-cli -Dsonar.token=$SONAR_AUTH_TOKEN'
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
                
                // Docker container ke andar hi Java compile aur run hoga (Safe Tarika)
                sh 'docker run --rm --network=host -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp openjdk:17 javac WebpageTest.java'
                sh 'docker run --rm --network=host -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp openjdk:17 java WebpageTest'
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