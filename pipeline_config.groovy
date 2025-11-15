import org.boozallen.plugins.jte.init.governance.config.dsl.PipelineConfigurationDsl

PipelineConfigurationDsl.configure(this) {

    // المكتبة اللي هتستخدم
    library('jenkins-pipelines') {
        default_version = 'main'
    }

    // Templates اللي الـ pipeline هيستخدمها
    templates = [
        'petclinicPipeline'  
    ]
}
