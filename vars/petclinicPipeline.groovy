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
                    // بناء الصورة باسم spring-petclinic مع tag build number
                    def builtImage = dockerBuild([
                        imageName: 'spring-petclinic', 
                        imageTag: "${env.BUILD_NUMBER ?: 'latest'}"
                    ])
                    
                    // عمل tag لـ latest
                    sh "docker tag ${builtImage} spring-petclinic:latest"
                    echo "Built image: ${builtImage} and also tagged as spring-petclinic:latest"
                }
            }

            stage('Trivy Scan') {
                steps {
                    echo "Scanning Docker image for vulnerabilities..."
                    sh "trivy image --exit-code 1 --severity HIGH,CRITICAL spring-petclinic:latest --format json --output trivy-report.json"
                }
            }

            stage('Push Docker Image') {
                steps {
                    echo "Pushing Docker image to Nexus..."
                    dockerPush([image: "spring-petclinic:latest"])
                }
            }
        }
    }
}


