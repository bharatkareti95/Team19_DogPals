pipeline {
    agent any
    
    // tools { 
    //     //Configuring what tools to use according to what was saved in Jenkins
    //     Maven 'Maven 3.6.0'
    //     JDK 'jdk8'
    // }

    stages {
		// stage('Checkout source code') {
        //     steps {
        //         script {
        //             checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], 
        //             userRemoteConfigs: [[credentialsId: 'github-credentials', 
        //             url: 'https://github.com/bharatkareti95/Team19_DogPals.git']]])
        //         }
        //     }
        // }
        //Building jar file to test if code changes are ok and there is no error
		// stage('Build front end Jar file') {
        //     steps {
        //         dir('dogPals') {
        //             sh "pwd"
        //             sh 'mvn clean install'
        //             }

        //         // script {
                
        //         //     //sh 'cd dogPals'
        //         //     sh 'mvn clean install'
        //         // }
        //     }
        //         // }
        //     }
        // }
        //Build docker image from blueprint in dockerfile. The arguments passed are: dockerRepoName:imageTag
        //Note that imageTag has build number variable for release management
        stage('Building our image') {
            steps {
                dir('dogPals') {
                     sh "pwd"
                     dockerImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
                // script {
                //     dockerImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
                // }
                     }
                // script {
                //     dockerImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
                // }
            }
        }
        //Push our newly created image to dockerhub
        stage('Push image to Dockerhub') {
            steps {
                script {
                    //Assume the Docker Hub registry by passing an empty string as the first parameter
                    docker.withRegistry('' , 'docker-hub') {
                        dockerImage.push()
                    }
                }
            }
        }
        //Retrieve image from dockerhub and run container on port 9000 with the same name as the image
        stage('Run the application') {
            steps {
                script {
                    sh 'docker run -p 9000:9000 --name dogpals_presentation_container$BUILD_NUMBER dogpals_presentation:$BUILD_NUMBER'
                    
                }
}
