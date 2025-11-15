def call() {
    node {
        stage('Build with Maven') {
            mavenBuild()
        }
        stage('SonarQube Scan') {
            sonarScan()
        }
        stage('Trivy Scan') {
            trivyScan()
        }
        stage('Deploy to Nexus') {
            nexusDeploy()
        }
        stage('Docker Build & Push') {
            dockerBuild()
        }
    }
}
