pipeline {
    stages {
        mavenBuild()
        sonarScan()
        trivyScan()
        nexusDeploy()
        dockerBuild()
    }
}

