def call(Map config = [:]) {

    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {

        sh '''
        mvn -B \
            -DskipTests=false \
            sonar:sonar \
            -Dsonar.projectKey=spring-petclinic \
            -Dsonar.projectName=spring-petclinic \
            -Dsonar.host.url=http://http://44.203.150.173:9000/ \
            -Dsonar.login=$SONAR_TOKEN
        '''
    }
}
