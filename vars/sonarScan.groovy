def call(Map config = [:]) {

    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {

        def mavenOpts = config.maven_opts ?: ""
        def sonarKey = config.sonar_project_key ?: ""
        def sonarName = config.sonar_project_name ?: ""
        def sonarHost = config.sonar_host_url ?: ""

        sh """
            mvn ${mavenOpts} sonar:sonar \
                -Dsonar.projectKey=${sonarKey} \
                -Dsonar.projectName=${sonarName} \
                -Dsonar.host.url=${sonarHost} \
                -Dsonar.login=$SONAR_TOKEN
        """
    }
}
