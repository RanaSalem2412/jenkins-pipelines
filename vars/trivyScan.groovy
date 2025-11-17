def call(Map config) {
    def imageName = config.imageName
    def severity = config.severity ?: 'HIGH,CRITICAL'
    def exitCode = config.exitCode ?: 1
    def reportFormat = config.reportFormat ?: 'json'
    def reportFile = config.reportFile ?: 'trivy-report.json'
    
    stage('Trivy Security Scan') {
        echo "🔍 Scanning image: ${imageName}"
        echo "Severity levels: ${severity}"
        
        // فحص الثغرات
        sh """
            trivy image --exit-code ${exitCode} --severity ${severity} ${imageName}
        """
        
        // توليد التقرير
        sh """
            trivy image --format ${reportFormat} --output ${reportFile} ${imageName}
        """
        
        echo "✅ Security scan completed"
        
        // أرشفة التقرير
        archiveArtifacts artifacts: reportFile, allowEmptyArchive: true
    }
}
