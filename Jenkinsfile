pipeline {
    agent any

    triggers {
        pollSCM('H/20 * * * *')
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube'
    }

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git"
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Dhafer99/DevOpsFinal.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Running Maven clean and install'
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis'
                withSonarQubeEnv('SonarQube') {
                    withCredentials([usernamePassword(credentialsId: '2',
                                                      usernameVariable: 'SONAR_USER',
                                                      passwordVariable: 'SONAR_PASS')]) {
                        sh 'mvn sonar:sonar -Dsonar.login=$SONAR_USER -Dsonar.password=$SONAR_PASS'
                    }
                }
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
