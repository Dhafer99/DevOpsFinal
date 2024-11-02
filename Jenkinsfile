pipeline {
    agent any

    triggers {
        pollSCM('H/20 * * * *')
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        SONAR_TOKEN = credentials('SONAR')
    }

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git"
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Dhafer99/DevOpsFinal.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Running Maven clean and install'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running Unit Tests'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // Archiving test results
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis'
                sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
            }
        }

        stage('Show Date') {
            steps {
                script {
                    def date = new Date()
                    echo "Current system date and time: ${date}"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }
    }
}
