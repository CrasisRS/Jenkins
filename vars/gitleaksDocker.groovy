def call() {

pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'zricethezav/gitleaks:latest'
        REPOS = 'https://github.com/CrasisRS/for_testing_only'
        EMAIL_TO = 'crasis89@web.de'
    }
    
    stages {
        stage('Clone Repositories') {
            steps {
                script {
                    def repos = env.REPOS.split(',')

                    for (repo in repos) {
                        sh "git clone --single-branch --depth 1 ${repo} ${env.WORKSPACE}/\$(basename ${repo})"
                    }
                }
            }
        }
        stage('Gitleaks Scan') {
            steps {
                script {
                    // Pull the Docker image
                    sh "docker pull ${DOCKER_IMAGE}"

                    def repos = env.REPOS.split(',')
                    
                    // Loop through each repository and run gitleaks in Docker
                    for (repo in repos) {
                        sh "docker run -v ${env.WORKSPACE}:/workspace ${DOCKER_IMAGE} detect --source=/workspace/\$(basename ${repo}) --report-path=/workspace/leaks_\$(basename ${repo}).json"
                    }
                    
                    // Send email with gitleaks report
                    emailext body: "Gitleaks report attached.", subject: "Gitleaks Scan Results", to: "${EMAIL_TO}", attachmentsPattern: "${env.WORKSPACE}/gitleaks-report.json"
                }
            }
        }
        
        stage('Cleanup') {
            steps {
                script {
                    // Clean up repos in workspace
                    def repos = env.REPOS.split(',')

                    for (repo in repos) {
                        sh "rm -rf ${env.WORKSPACE}/\$(basename ${repo})"
                    }
                }
            }
        }
    }
}
}
