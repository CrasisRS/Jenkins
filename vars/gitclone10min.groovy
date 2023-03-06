def call(){

    pipeline {
        agent any
    
        stages {
            stage('Clone repository') {
                steps {
                    retry(5){
                    script{
                        //def repos = ['for_testing_only'] // list of repositories
                        sh "git clone https://gitasdfasdf.com/CrasisRS/for_testing_only.git" // clone repository
                    }
                    }
                }
            }
        
            stage('Pull every 10 seconds for 10 minutes') {
                steps {
                    script {
                        sh "cd for_testing_only"
                        //def repos = ['for_testing_only'] // list of repositories
                        for (int i = 1; i <= 60; i++) {
                        sh "git fetch https://gitasdfasdf.com/CrasisRS/for_testing_only.git"
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
