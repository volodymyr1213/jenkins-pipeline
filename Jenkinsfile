pipeline {
  agent any
  stages {
    stage('pull repo ') {
      steps {
        git 'https://github.com/aizada92/jenkins-pipeline.git'
      }
    }

    stage('Stage2') {
      steps {
        sh 'echo "Hello"'
      }
    }

  }
}