def call() {
    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
        sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN"
    }
}
