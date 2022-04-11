pipeline{
  agent any
  stages{
    stage("Git Checkout"){
      steps{
            git credentialsId: 'github', url: 'https://github.com/nayala2/toDoListAttempt.git'
           }
          }
     stage("Maven Build"){
       steps{
            sh "mvn clean package"
            sh "mv target/*.war target/myweb.war"
             }
            }
     stage("deploy-dev"){
       steps{
          sshagent(['tomcat-dev1']) {
          sh """
          scp -o StrictHostKeyChecking=no target/myweb.war
          ubuntu@yourip:/opt/tomcat/webapps/
          ssh ubuntu@yourip /opt/tomcat/bin/shutdown.sh
          ssh ubuntu@yourip /opt/tomcat/bin/startup.sh
            """
            }
          }
        }
      }
    }