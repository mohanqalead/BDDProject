pipeline {
    agent any
     tools {
        maven 'Maven1' // Ensure Maven is installed on Jenkins; update version as needed
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the source code...'
                git url: 'https://github.com/mohanqalead/BDDProject.git', branch: 'main' // Replace with your repository URL
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running Selenium tests...'
                // Run tests in headless mode if needed
                sh 'mvn test'
            }
            post {
                always {
                    echo 'Archiving test results and screenshots...'
                    junit '**/target/surefire-reports/*.xml' // Adjust path based on your project structure
                    archiveArtifacts artifacts: '**/target/screenshots/*.png', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up the workspace...'
            deleteDir()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs for more details.'
        }
    }
}
