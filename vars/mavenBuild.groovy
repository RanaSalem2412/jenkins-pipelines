def call() {
    // استخدمنا الـ Maven installation اللي مسميه Maven_3.9.11
    // واستخدمنا الـ Global Maven settings عن طريق الـ file ID مباشرة
    withMaven(
        maven: 'Maven', 
        mavenSettingsConfig: 'global-maven-settings'
    ) {
        sh "mvn clean deploy -DskipTests=false -DaltDeploymentRepository=${globals.nexus_repo}::default::${globals.nexus_url}/repository/${globals.nexus_repo}"

    }
}

