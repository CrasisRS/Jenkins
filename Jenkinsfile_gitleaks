pipeline {
  agent any
  
  stages {
    stage('Cleanup') {
        steps {
            deleteDir()
        }
    }
    stage('Setup') {
        steps {
            script {
                def gitleaksInstalled = sh(returnStatus: true, script: 'which gitleaks > /dev/null') == 0
                    
                if (!gitleaksInstalled) {
                        sh 'mkdir -p bin'
                        sh 'curl -sfL https://install.goreleaser.com/github.com/zricethezav/gitleaks.sh | sh -s -- -b bin'                }
                env.PATH = "${env.WORKSPACE}/bin:${env.PATH}"
            }
        }
    }
    stage('Gitleaks') {
      steps {
        script {
          def repos = ['for_testing_only'] // list of repositories

          for (def repo : repos) {
            sh 'echo $PATH'
            sh "git clone https://github.com/CrasisRS/${repo}.git" // clone repository
            sh "gitleaks detect --source ./${repo} --report-path ./leaks_${repo}.json" // run Gitleaks on repository
            sh "rm -rf ./${repo}" // remove cloned repository
          }
        }
      }
    }
  }
}
