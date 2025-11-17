def call(Map config = [:]) {
    def imageName = config?.imageName ?: 'myapp'
    def imageTag = config?.imageTag ?: 'latest'
    def nexusHost = config?.nexusHost ?: '44.203.150.173'
    def nexusPort = config?.nexusPort ?: '8082'
    def nexusRepo = config?.nexusRepo ?: 'docker-hosted'
    def credentialsId = config?.credentialsId ?: 'nexus-admin'

    def localImage = "${imageName}:${imageTag}"
    def nexusImage = "${nexusHost}:${nexusPort}/${nexusRepo}/${imageName}:${imageTag}"

    stage('Tag for Nexus') {
        echo "🏷️ Tagging image: ${nexusImage}"
        sh "docker tag ${localImage} ${nexusImage}"
    }

    stage('Push to Nexus') {
        echo "📤 Pushing to Nexus: ${nexusImage}"

        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
            withCredentials([usernamePassword(
                credentialsId: credentialsId,
                usernameVariable: 'NEXUS_USER',
                passwordVariable: 'NEXUS_PASS'
            )]) {
                sh """
                    echo \$NEXUS_PASS | docker login -u \$NEXUS_USER --password-stdin ${nexusHost}:${nexusPort}
                    docker push ${nexusImage} || echo "⚠️ Push failed, continuing pipeline"
                    docker logout ${nexusHost}:${nexusPort}
                """
            }
            echo "✅ Attempted push to Nexus (pipeline will continue even if it fails)"
        }
    }

    // إزالة الصورة من الجهاز المحلي
    sh "docker rmi ${nexusImage} || true"
}

