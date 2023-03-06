def call(){

    pipeline {
        agent any
    
        stages {
            stage('Clone repository') {
                steps {
                    script{
                        def repos = ['for_testing_only'] // list of repositories
                        sh "git clone https://github.com/CrasisRS/${repo}.git" // clone repository
                    }
                }
            }
        
            stage('Pull every 10 seconds for 10 minutes') {
                steps {
                    script {
                        def repos = ['for_testing_only'] // list of repositories
                        for (int i = 1; i <= 60; i++) {
                        sh "git pull https://github.com/CrasisRS/${repo}.git"
                            sleep 10
                        }
                    }
                }
            }
        }
    
        post {
            always {
            cleanWs()
            }
        }
    }
}