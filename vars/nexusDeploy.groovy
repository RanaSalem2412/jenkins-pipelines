def call(Map config = [:]) {
    def imageName = config?.imageName ?: 'myapp'
    def imageTag = config?.imageTag ?: 'latest'
    def nexusUrl = config?.nexusUrl
    def nexusRepo = config?.nexusRepo ?: 'docker-hosted'
    def credentialsId = config?.credentialsId ?: 'nexus-credentials'
    
    if (!nexusUrl) {
        error("❌ nexusUrl is required!")
    }
    
    def localImage = "${imageName}:${imageTag}"
    def nexusImage = "${nexusUrl}/${nexusRepo}/${imageName}:${imageTag}"
    
    stage('Tag for Nexus') {
        echo "🏷️ Tagging image: ${nexusImage}"
        sh "docker tag ${localImage} ${nexusImage}"
    }
    
    stage('Push to Nexus') {
        echo "📤 Pushing to Nexus: ${nexusImage}"
        
        withCredentials([usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASS'
        )]) {
            sh """
                echo \$NEXUS_PASS | docker login -u \$NEXUS_USER --password-stdin ${nexusUrl}
                docker push ${nexusImage}
                docker logout ${nexusUrl}
            """
        }
        
        echo "✅ Image pushed successfully to Nexus"
    }
    
    sh "docker rmi ${nexusImage} || true"
}
