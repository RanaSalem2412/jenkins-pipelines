pipeline {
    agent { label 'docker-agent' }

    template 'petclinicPipeline'

    libraries {
        jenkins-pipelines {
            version 'main'
        }
    }
}
