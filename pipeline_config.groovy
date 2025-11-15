library(
    identifier: 'jenkins-pipelines@main',
    retriever: modernSCM([
        $class: 'GitSCMSource',
        remote: 'https://github.com/RanaSalem2412/jenkins-pipelines.git'
    ])
)

pipelineTemplate = 'templates/petclinicPipeline.groovy'
