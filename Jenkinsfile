pipeline {
    agent any

    triggers {
        pollSCM('H/20 * * * *')
    }

   environment {
       SONARQUBE_SERVER = 'SonarQube'
       SONAR_TOKEN = credentials('SONAR')
         IMAGE_NAME = "dhafersouid/springbootprojectdevops" // Name of the Docker image to be built
       DOCKER_COMPOSE_FILE = 'docker-compose.yml'
       DOCKER_CREDENTIALS = credentials('dockerhubcredentials')
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


        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis'
                sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
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

        stage('Deployment') {
                    steps {
                        echo 'Running Deployment'
                        sh 'mvn deploy'
                    }
                }
          stage('Building Image') {
                            steps {
                             echo "Building Docker image"
                             sh "docker build -t $IMAGE_NAME ."
                            }
                        }

          stage('Docker Compose Up') {
                      steps {
                          script {
                              echo 'Starting Docker Compose services'
                              sh "docker-compose -f $DOCKER_COMPOSE_FILE up -d"
                          }
                      }
                  }

          stage('Push Docker Image') {
              steps {
                  script {
                      echo "Logging in and pushing Docker image"
                      sh "echo $DOCKER_CREDENTIALS_PSW | docker login -u $DOCKER_CREDENTIALS_USR --password-stdin"
                      sh "docker push $IMAGE_NAME"
                  }
              }
          }

          stage('Docker Compose Down') {
                      steps {
                          script {
                              echo 'Stopping and removing Docker Compose services'
                              sh "docker-compose -f $DOCKER_COMPOSE_FILE down"
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
