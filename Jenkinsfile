pipeline{

    agent any

    stages{

        stage('sonar quality check'){
            agent{

                docker{
                    image 'maven:3.8.1-adoptopenjdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }

            steps{

                script{

                    withSonarQubeEnv(credentialsId: 'sonar-token-2') {

                        sh 'mvn clean install sonar:sonar'
                    }
                }
            }
        }
        stage('Quality Gate Status'){
            steps{
                script{
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                }
            }
        }
    }
}