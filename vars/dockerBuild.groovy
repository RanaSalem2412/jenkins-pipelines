def call() {
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        // Build image locally
        sh "docker build -t $DOCKER_USER/petclinic:latest ."

        // Push to Docker Hub
        sh """
            docker login -u $DOCKER_USER -p $DOCKER_PASS
            docker push $DOCKER_USER/petclinic:latest
        """
    }

    // Push to Nexus Docker registry
    withCredentials([usernamePassword(credentialsId: 'nexus-admin', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
        def NEXUS_URL = "44.203.150.173:8082"  // عدلي حسب عنوانك
        def NEXUS_REPO = "repository/docker-hosted"

        // Tag image for Nexus
        sh "docker tag $DOCKER_USER/petclinic:latest $NEXUS_URL/$NEXUS_REPO/petclinic:latest"

        // Login & push to Nexus
        sh """
            docker login $NEXUS_URL -u $NEXUS_USER -p $NEXUS_PASS
            docker push $NEXUS_URL/$NEXUS_REPO/petclinic:latest
        """
    }
}

