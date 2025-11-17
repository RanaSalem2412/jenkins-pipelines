def call(Map config) {
    def imageName = config.imageName ?: 'myapp'
    def imageTag = config.imageTag ?: "${env.BUILD_NUMBER}"
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'
    
    stage('Build Docker Image') {
        echo "🔨 Building Docker image: ${imageName}:${imageTag}"
        sh """
            docker build -f ${dockerfile} -t ${imageName}:${imageTag} ${context}
        """
        echo "✅ Image built successfully: ${imageName}:${imageTag}"
    }
    
    // إرجاع اسم الـ image الكامل للاستخدام في المراحل التالية
    return "${imageName}:${imageTag}"
}
