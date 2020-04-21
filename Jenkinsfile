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
	    stage('Test') {
            agent {
              docker {
               image 'maven:3.6.3-jdk-11-slim'
              }
            }
            steps {
                unstash "app"
                sh '(mvn clean test)'
                sh '(mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify)'
                sh '(mvn org.jacoco:jacoco-maven-plugin:report)'
                sh '(mvn sonar:sonar -Dsonar.projectKey=nathanlatino_Spring_Boardel -Dsonar.organization=nathanlatino -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=d4f9782c005a67b3ad5d7c60d1db6a304048a6b7)'
                sh 'java -jar target/SMF-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &'
                cleanWs()
//                 sh './runTest.sh'
            }

        }
        stage('Deploy') {
                    steps {
                        echo 'Deploying'
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
