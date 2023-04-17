pipeline{

    agent any
    environment{
         VERSION = "${env.BUILD_ID}"
    }
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

                    withSonarQubeEnv(credentialsId: 'sonar-token') {

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
        stage('docker build & docker push to Nexus repo'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'nexus_cres', variable: 'nexus_credential')]) {
                        sh '''
                            docker build -t 13.212.127.164:8085/spring-boot-rest-api:${VERSION} .

                            docker login -u admin -p $nexus_credential 13.212.127.164:8085

                            docker push 13.212.127.164:8085/spring-boot-rest-api:${VERSION}

                            docker rmi  13.212.127.164:8085/spring-boot-rest-api:${VERSION}

                        '''

                }
            }
        }
    }
}