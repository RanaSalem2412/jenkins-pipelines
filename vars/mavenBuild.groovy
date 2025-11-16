def call(globals) {
    // استخدم الـ credentials من Jenkins (ID = nexus-admin)
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
                # امسح كل الـ Spring Boot artifacts
                rm -rf ~/.m2/repository/org/springframework/boot/ || true
                
                # اعمل build مع تجاهل الـ tests تماماً
                mvn clean deploy -U -Dmaven.test.skip=true -DaltDeploymentRepository=${globals.nexus_repo}::default::${globals.nexus_url}/repository/${globals.nexus_repo}
            """
        }
    }
}
