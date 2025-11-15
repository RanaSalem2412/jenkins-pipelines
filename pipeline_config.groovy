libraries {
    template = "petclinicPipeline"
    merge = true
}

globals {
    maven_opts = "-B -DskipTests=false"
    sonar_project_key = "spring-petclinic"
    sonar_cred_id = "sonar-token"

    nexus_repo = "maven-releases"
    nexus_cred_id = "nexus-creds"

    docker_registry = "nexus.example.com:8083"
    docker_repo = "petclinic"
    docker_cred_id = "docker-creds"
}
