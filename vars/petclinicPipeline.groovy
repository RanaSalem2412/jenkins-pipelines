def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo "Building the project..."
                    mavenBuild()
                }
            }
            stage('Docker') {
                steps {
                    echo "Building Docker image..."
                    dockerBuild()
                }
            }
            stage('SonarQube Scan') {
                steps {
                    echo "Running SonarQube scan..."
                    sonarScan()
                }
            }
            stage('Deploy') {
                steps {
                    echo "Deploying to Nexus..."
                    nexusDeploy()
                }
            }
        }
    }
}
