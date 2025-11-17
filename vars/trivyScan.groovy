def call(Map config = [:]) {
    def imageName = config?.imageName ?: 'spring-petclinic:latest'
    def severity = config?.severity ?: 'HIGH,CRITICAL'
    def reportFile = config?.reportFile ?: 'reports/trivy-report.json'

    stage('Trivy Security Scan') {
        echo "🔍 Scanning image: ${imageName}"
        echo "Severity levels: ${severity}"
        
        // إنشاء المجلد لو مش موجود
        sh "mkdir -p reports"
        
        // نفحص الصورة بدون أن يتوقف الـ pipeline لو فيه vulnerabilities
        sh """
            set +e
            trivy image --exit-code 1 --severity ${severity} ${imageName}
            trivy image --format json --output ${reportFile} ${imageName}
            set -e
        """
        
        echo "✅ Security scan completed (vulnerabilities won't fail the job)"
        archiveArtifacts artifacts: reportFile, allowEmptyArchive: true
    }
}

