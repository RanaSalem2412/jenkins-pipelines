def call(Map config = [:]) {
    // اسم الصورة: افتراضي spring-petclinic:latest بدل myapp:latest
    def imageName = config?.imageName ?: 'spring-petclinic:latest'
    def severity = config?.severity ?: 'HIGH,CRITICAL'
    def exitCode = config?.exitCode ?: 1
    def reportFile = config?.reportFile ?: 'trivy-report.json'
    
    stage('Trivy Security Scan') {
        echo "🔍 Scanning image: ${imageName}"
        echo "Severity levels: ${severity}"
        
        // استخدام shell script لفحص الصورة بصيغة CLI
        sh """
            trivy image --exit-code ${exitCode} --severity ${severity} ${imageName}
            trivy image --format json --output ${reportFile} ${imageName}
        """
        
        echo "✅ Security scan completed"
        archiveArtifacts artifacts: reportFile, allowEmptyArchive: true
    }
}

