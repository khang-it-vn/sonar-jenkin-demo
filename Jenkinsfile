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
                        sh """
                            docker build -t 13.212.127.164:8089/spring-boot-rest-api:${VERSION} .

                            echo "$nexus_credential" | docker login -u admin --password-stdin http://13.212.127.164:8089

                            docker push 13.212.127.164:8089/spring-boot-rest-api:${VERSION}

                            docker rmi 13.212.127.164:8089/spring-boot-rest-api:${VERSION}
                        """
                    }



                }
            }
        }
        stage('Identifying misconfigs using datree in helm charts'){
            steps{
                script{
                    dir('kubernetes/myapp/') {
                        sh 'helm datree test .'
                    }
                }
            }
        }
    }
    post{
        always{
            mail bcc: '',body: "<br>Project: ${env.JOB_NAME} <br> Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType:'text/html', replyTo: '', subject: "${currentBuild.result} CI project name -> ${env.JOB_NAME}", to: 'nguyenhoangkhangnsc19@gmail.com';
        }

    }
}