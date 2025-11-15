libraries {
    // اسم الـ template اللي جايب مراحل pipeline
    template = "petclinicPipeline"

    // لو عندك libraries تانية اكتبيها
    merge = true
}

globals {
    // Maven
    maven_opts = "-B -DskipTests=false"

    // SonarQube
    sonar_project_key = "spring-petclinic"
    sonar_cred_id = "sonar-token"             // ID من Jenkins

    // Nexus
    nexus_repo = "maven-releases"
    nexus_cred_id = "nexus-creds"             // ID من Jenkins

    // Docker
    docker_registry = "nexus.example.com:8083"
    docker_repo = "petclinic"
    docker_cred_id = "docker-creds"           // ID من Jenkins
}
