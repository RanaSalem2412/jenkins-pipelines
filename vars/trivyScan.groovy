def call() {
    sh """
        trivy image --severity HIGH,CRITICAL --exit-code 1 petclinic:latest
    """
}
