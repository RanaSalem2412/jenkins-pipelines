def call() {
    pipeline {
        agent { label 'docker-agent' }

        stages {
            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'https://github.com/RanaSalem2412/spring-petclinic.git'
                }
            }

            stage('Maven Build') {
                steps {
                    mavenBuild()
                }
            }

            stage('SonarQube Scan') {
                steps {
                    sonarScan()
                }
            }

            stage('Build Docker Image') {
                steps {
                    dockerBuild()
                }
            }

            stage('Trivy Image Scan') {
                steps {
                    trivyScan()
                }
            }

            stage('Push to Nexus') {
                steps {
                    nexusDeploy()
                }
            }
        }
    }
}

