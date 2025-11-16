def call(globals) {
    withMaven(
        maven: 'Maven', 
        mavenSettingsConfig: 'global-maven-settings'
    ) {
        sh "mvn clean deploy -DskipTests=false -DaltDeploymentRepository=${globals.nexus_repo}::default::${globals.nexus_url}/repository/${globals.nexus_repo}"
    }
}
