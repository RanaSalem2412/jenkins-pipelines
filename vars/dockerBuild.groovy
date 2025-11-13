def call() {
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            docker build -t $DOCKER_USER/petclinic:latest .
            docker login -u $DOCKER_USER -p $DOCKER_PASS
            docker push $DOCKER_USER/petclinic:latest
        """
    }
}

