def call() {
    withCredentials([usernamePassword(credentialsId: 'nexus-cred', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
        sh "mvn deploy -Dusername=$NEXUS_USER -Dpassword=$NEXUS_PASS"
    }
}

