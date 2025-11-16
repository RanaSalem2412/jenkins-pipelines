def call(globals) {
    withMaven(
        maven: 'Maven', 
        mavenSettingsConfig: 'global-maven-settings'
    ) {
        sh """
            # امسح كل الـ Spring Boot artifacts
            rm -rf ~/.m2/repository/org/springframework/boot/ || true
            
            # اعمل build مع تجاهل الـ tests تماماً (مش هيعمل compile ولا run)
            mvn clean deploy -U -Dmaven.test.skip=true -DaltDeploymentRepository=${globals.nexus_repo}::default::${globals.nexus_url}/repository/${globals.nexus_repo}
        """
    }
}
