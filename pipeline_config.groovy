pipeline {
    template { 'templates/petclinicPipeline.groovy' } 
    libraries {
        jenkins-pipelines {
            version 'main'
        }
    }
}
