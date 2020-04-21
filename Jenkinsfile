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
                sh '(mvn checkstyle:check)'
                sh '(mvn clean test)'
                sh '(mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify)'
                sh '(mvn org.jacoco:jacoco-maven-plugin:report)'
//                 sh '(mvn sonar:sonar -Dsonar.projectKey=nathanlatino_Spring_Boardel -Dsonar.organization=nathanlatino -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=d4f9782c005a67b3ad5d7c60d1db6a304048a6b7)'
                sh './runTest.sh'
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
