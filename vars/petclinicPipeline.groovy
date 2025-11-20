def call(globals) {   
    pipeline {
        agent { label 'docker-agent' }

        environment {
            IMAGE_NAME = 'spring-petclinic'
            IMAGE_TAG  = "${env.BUILD_NUMBER ?: 'latest'}"
        }

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
                    sonarScan(globals)  
                }
            }

            stage('Docker Build') {
                steps {
                    echo "Building Docker image..."
                    script {
                        def builtImage = dockerBuild([
                            imageName: env.IMAGE_NAME,
                            imageTag: env.IMAGE_TAG
                        ])
                        
                        sh "docker tag ${builtImage} ${env.IMAGE_NAME}:latest"
                        echo "Built image: ${builtImage} and also tagged as ${env.IMAGE_NAME}:latest"
                    }
                }
            }

            stage('Trivy Scan') {
                steps {
                    echo "Scanning Docker image for vulnerabilities..."
                    trivyScan(globals)  
                }
            }

            stage('Push Docker Image') {
                steps {
                    echo "Pushing Docker image to Nexus..."
                    script {
                        nexusDeploy([
                            imageName: env.IMAGE_NAME,
                            imageTag: env.IMAGE_TAG,
                            nexusHost: '44.203.150.173',
                            nexusPort: '8082',
                            nexusRepo: 'docker-hosted',
                            credentialsId: 'nexus-admin'
                        ])
                    }
                }
            }
        }
    }
}

