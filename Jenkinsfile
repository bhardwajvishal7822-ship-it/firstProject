// pipeline {
//     agent any

//     stages {
//         stage('Stop Old Container') {
//             steps {
//                 echo 'Stopping and removing old container if exists...'
//                 sh 'docker stop my-running-site || true'
//                 sh 'docker rm my-running-site || true'
//             }
//         }

//         stage('Build Docker Image') {
//             steps {
//                 echo 'Building new Docker image from GitHub code...'
//                 // Ab 'dir' ki zaroorat nahi hai, Jenkins khud GitHub repository ke andar hi khulega
//                 sh 'docker build -t my-html-app .'
//             }
//         }

//         stage('Deploy New Container') {
//             steps {
//                 echo 'Deploying new container on port 8083...'
//                 sh 'docker run -d -p 8083:80 --name my-running-site my-html-app'
//             }
//         }
//     }
// }

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

        stage('Deploy New Container') {
            steps {
                echo 'Deploying new container on port 8083...'
                // FIXED: Space aur port mapping (:80) sahi kar di hai
                sh 'docker run -d -p 8083:80 --name my-running-site my-html-app'
                echo 'Waiting for 3 seconds to let the server start...'
                sh 'sleep 3' 
            }
        }

        stage('Automated Java Test') {
            steps {
                echo '🧪 Compiling and Running Java Integration Tests...'
                // 1. Java file ko compile karna
                sh 'javac WebpageTest.java'
                // 2. Class file ko run karna (Yeh hamari index.html ka content check karega)
                sh 'java WebpageTest'
            }
        }
    }

    post {
        failure {
            echo '🚨 TEST FAIL HO GAYA! Bad code detected. Stopping the container immediately...'
            // Agar Java test fail hua, toh yeh block chalega aur website ko band kar dega
            sh 'docker stop my-running-site || true'
        }
        success {
            echo '🚀 SAARE TESTS PASS! Website is live and verified by Java.'
        }
    }
}