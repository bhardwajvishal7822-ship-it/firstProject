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
                // Ab 'dir' ki zaroorat nahi hai, Jenkins khud GitHub repository ke andar hi khulega
                sh 'docker build -t my-html-app .'
            }
        }

        stage('Deploy New Container') {
            steps {
                echo 'Deploying new container on port 8085...'
                sh 'docker run -d -p 8085:80 --name my-running-site my-html-app'
            }
        }
    }
}