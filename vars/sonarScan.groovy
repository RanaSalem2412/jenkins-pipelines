def call() {
    withSonarQubeEnv('SonarQube') {
        sh "mvn sonar:sonar"
    }
}

