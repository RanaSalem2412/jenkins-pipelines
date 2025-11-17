def call(Map config = [:]) {
    def imageName = config?.imageName ?: 'myapp:latest'
    def severity = config?.severity ?: 'HIGH,CRITICAL'
    def exitCode = config?.exitCode ?: 1
    def reportFile = config?.reportFile ?: 'trivy-report.json'
    
    stage('Trivy Security Scan') {
        echo "🔍 Scanning image: ${imageName}"
        echo "Severity levels: ${severity}"
        
        sh """
            trivy image --exit-code ${exitCode} --severity ${severity} ${imageName}
            trivy image --format json --output ${reportFile} ${imageName}
        """
        
        echo "✅ Security scan completed"
        archiveArtifacts artifacts: reportFile, allowEmptyArchive: true
    }
}
