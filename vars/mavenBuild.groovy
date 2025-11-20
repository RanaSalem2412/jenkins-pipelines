def call(globals) {
    
    withCredentials([
        usernamePassword(
            credentialsId: 'nexus-admin', 
            usernameVariable: 'NEXUS_USERNAME',
            passwordVariable: 'NEXUS_PASSWORD'
        )
    ]) {
        withMaven(
            maven: 'Maven', 
            mavenSettingsConfig: 'global-maven-settings'
        ) {
            sh """
                
                rm -rf ~/.m2/repository/org/springframework/boot/ || true
                
               
                mvn clean deploy -U -Dmaven.test.skip=true -DaltDeploymentRepository=${globals.nexus_repo}::default::${globals.nexus_url}/repository/${globals.nexus_repo}
            """
        }
    }
}
