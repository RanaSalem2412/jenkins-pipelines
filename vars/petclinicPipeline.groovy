def call(globals) {   // تمرير globals
    pipeline {
        agent { label 'docker-agent' }
        stages {
            stage('Build') {
                steps {
                    echo "Building the project..."
                    mavenBuild(globals)  // ينفذ mvn clean install/deploy
                }
            }
            stage('SonarQube Scan') {
                steps {
                    echo "Running SonarQube scan..."
                    sonarScan(globals)  // ينفذ تحليل SonarQube
                }
            }
            stage('Docker Build') {
                steps {
                    echo "Building Docker image..."
                    dockerBuild(globals)  // ينشئ صورة Docker
                }
            }
            stage('Trivy Scan') {
                steps {
                    echo "Scanning Docker image for vulnerabilities..."
                    trivyScan(globals)  // يفحص الصورة بحثاً عن ثغرات أمنية
                }
            }
            stage('Push Docker Image') {
                steps {
                    echo "Pushing Docker image to Nexus..."
                    dockerPush(globals)  // يدفع الصورة للـ Nexus/Docker registry
                }
            }
        }
    }
}

