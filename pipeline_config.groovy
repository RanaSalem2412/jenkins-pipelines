pipeline {
    agent { label 'docker-agent' }

    template 'templates/petclinicPipeline.groovy'

    libraries {
        jenkins-pipelines {
            version 'main'
        }
    }
}
