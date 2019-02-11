
peline{
    agent any
    options {
        disableConcurrentBuilds()
    }
    stages{
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage ('Testing'){
            steps {
                sh 'mvn -B test'
            }
        }
    }
}
