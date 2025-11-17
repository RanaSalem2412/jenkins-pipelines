def call(Map config = [:]) {
    def imageName = config?.imageName ?: 'spring-petclinic'
    def imageTag = config?.imageTag ?: "${env.BUILD_NUMBER ?: 'latest'}"
    def dockerfile = config?.dockerfile ?: 'Dockerfile'
    def context = config?.context ?: '.'

    stage('Build Docker Image') {
        echo "🔨 Building Docker image: ${imageName}:${imageTag}"
        sh """
            docker build -f ${dockerfile} -t ${imageName}:${imageTag} ${context}
        """
        echo "✅ Image built successfully: ${imageName}:${imageTag}"

        // إضافة Tag باسم latest
        echo "🏷️ Tagging Docker image as ${imageName}:latest"
        sh "docker tag ${imageName}:${imageTag} ${imageName}:latest"
    }

    return "${imageName}:${imageTag}"
}
