pipeline {

    environment {
	  
	    dockerTrainingImage = ""
    }

    agent any
    
    // tools { 
    //     //Configuring what tools to use according to what was saved in Jenkins
    //     Maven 'Maven 3.6.0'
    //     JDK 'jdk8'
    // }

    stages {
        //Build docker image from blueprint in dockerfile. The arguments passed are: dockerRepoName:imageTag
        //Note that imageTag has build number variable for release management
	    //stage('Building training image and pushing it to registry'){
		//    steps {
		//	    dir('DogPalsTraining') {
		//		    sh "pwd"
		//		    sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_training:$BUILD_NUMBER"
		//	    }
		//    }
	    //}
	    //stage('Building forum image and pushing it to registry'){
		//    steps {
		//	    dir('DogPalsForum') {
		//		    sh "pwd"
		//		    sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_forum:$BUILD_NUMBER"
		//	    }
		//    }
	    //}
	    stage('Building front-end image and pushing it to registry'){
		    steps {
			    dir('dogPals') {
				    sh "pwd"
				    sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_frontend:$BUILD_NUMBER -Djib.to.tags=['latest']"
			    }
		    }
	    }
	    
		    
     
        //Retrieve image from dockerhub and run container on port 9000 with the same name as the image
        stage('Deploying via docker-compose') {
            steps {
                dir('docker-compose') {
			sh 'docker-compose down --volumes'
			sh 'docker-compose up'
                    //sh 'docker-compose -f src/main/docker/app.yml up -d'
                } 
            }  
        } 
    }
}
