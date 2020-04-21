pipeline {
    agent any
    environment {
        SONAR_LOGIN_TOKEN = credentials('SONAR_LOGIN_TOKEN')
    }
    stages {
        stage('Build') {
            agent {
              docker {
               image 'maven:3.6.3-jdk-11-slim'
              }
            }
            steps {
                sh '(mvn clean package)'
                stash name: "app", includes: "**"
            }
        }
            stage('QualityTest') {
                 agent {
                  docker {
                   image 'maven:3.6.3-jdk-11-slim'
                  }
                }
                steps {
                    unstash "app"
                    sh '(mvn clean test)'
                    sh '(mvn sonar:sonar -Dsonar.projectKey=nathanlatino_Spring_Boardel -Dsonar.organization=nathanlatino -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=d4f9782c005a67b3ad5d7c60d1db6a304048a6b7)'
                }
            }
        stage('IntegrationTest') {
             agent {
              docker {
               image 'lucienmoor/katalon-for-jenkins:latest'
               args '-p 9999:9090'
              }
            }
            steps {
                unstash "app"
                sh 'java -jar target/boardel-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &'
                sh 'sleep 30'
                sh '(mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify)'
                sh '(mvn org.jacoco:jacoco-maven-plugin:report)'

                cleanWs()
//                 sh './runTest.sh'
            }

        }
    }
    post {
        always {
            echo 'always clean up'
            deleteDir()
        }
    }
}
