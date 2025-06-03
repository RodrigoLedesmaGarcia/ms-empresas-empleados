pipeline {
    agent any

    stages {
        stage('Mensaje') {
            steps {
                echo 'Jenkins está ejecutando el pipeline correctamente 🚀'
            }
        }
    }
    post {
        success {
            echo '🎉 Prueba exitosa'
        }
        failure {
            echo '💥 Prueba fallida'
        }
        always {
            echo 'Pipeline terminado.'
        }
    }
}
