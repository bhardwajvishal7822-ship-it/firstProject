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

        stage('Check What Jenkins Can See') {
            steps {
                echo 'Checking files inside the folder...'
                dir('/mnt/c/Users/Vishal bhardwaj/Desktop/Project1') {
                    // Yeh command batayegi ki Jenkins ko folder mein kya dikh raha hai
                    sh 'pwd'
                    sh 'ls -la'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building new Docker image...'
                dir('/mnt/c/Users/Vishal bhardwaj/Desktop/Project1') {
                    sh 'docker build -t my-html-app .'
                }
            }
        }

        stage('Deploy New Container') {
            steps {
                echo 'Deploying new container on port 8084...'
                sh 'docker run -d -p 8084:80 --name my-running-site my-html-app'
            }
        }
    }
}