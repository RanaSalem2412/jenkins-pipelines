def call() {
    withMaven(maven: 'Maven_3.9.11', mavenSettingsConfig: '1d55bb71-5054-437b-85d4-bcd6dd462e7f') {
        sh "mvn clean install"
    }
}

