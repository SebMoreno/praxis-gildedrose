pipeline {
  agent any
  stages{
   stage("Unit Testing") {
      agent { docker 'maven' }
      steps {
         sh 'mvn test -DDATABASE_HOST_IP=$BD_IP'
      }
    }
    stage("Build image") {
       steps {
         script {
            withCredentials([usernamePassword(
                    credentialsId: 'db',
                    usernameVariable: 'DB_USER',
                    passwordVariable: 'DB_PASS'
                )]) {
                sh 'docker build --build-arg DATABASE_HOST_IP=$BD_IP --build-arg DATABASE_USER=$DB_USER --build-arg DATABASE_PASS=$DB_PASS -t sebmoreno/gildedrose-backend .'
              }
          }
       }
    }
    stage("Login dockerhub") {
       steps {
          script {
            withCredentials([usernamePassword(
                    credentialsId: 'docker-hub',
                    usernameVariable: 'DOCKERHUB_USER',
                    passwordVariable: 'DOCKERHUB_PASS'
                )]) {
                sh 'echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin'
              }
          }
       }
    }
    stage("Push image to dockerhub") {
       steps {
          sh 'docker push sebmoreno/gildedrose-backend'
       }
    }
  }
  post {
    always {
        sh 'docker logout'
    }
  }
}
