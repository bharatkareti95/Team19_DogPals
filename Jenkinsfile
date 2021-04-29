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
	    stage('Building training image'){
		    steps {
			    dir('DogPalsTraining') {
				    sh "pwd"
				    sh "./mvnw -DskipTests package -Pprod verify jib:build"
			    }
		    }
	    }
	    
		    
     
        //Retrieve image from dockerhub and run container on port 9000 with the same name as the image
        stage('Run the application') {
            steps {
                dir('DogPalsTraining') {
                    sh 'docker-compose -f src/main/docker/app.yml up -d'
                } 
            }  
        } 
    }
}
