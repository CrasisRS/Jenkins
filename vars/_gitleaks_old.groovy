pipeline {
  agent any
  stages {
    stage('Clone repo') {
      steps {
        git 'https://github.com/CrasisRS/for_testing_only.git'
      }
    }
    stage('Install gitleaks') {
      steps {
        sh 'wget https://github.com/zricethezav/gitleaks/releases/download/v7.6.0/gitleaks-linux-amd64'
        sh 'chmod +x gitleaks-linux-amd64'
      }
    }
    stage('Scan repository with gitleaks') {
      steps {
        sh './gitleaks-linux-amd64 --path .'
      }
    }
    post {
        always {
          sh 'rm -rf your-repo' // remove cloned repository

        }
    }
  }
}
