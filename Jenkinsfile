pipeline {

    environment {
	    dockerPresentationImage = ""
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
				    script {
					System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "86400");
					dockerTrainingImage = docker.build "bharatkareti/dogpals:dogpals_training$BUILD_NUMBER"
				     }
			    }
		    }
	    }
	    
	    stage('Building presentation image'){
		steps {
			dir('dogPals') {
			sh "pwd"
			script {
				System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "86400");
				dockerPresentationImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
				}
			    }
		    }
	    }
		    
	    
	    
	    
 //       stage('Building our image') {
   //         steps {
     //           dir('dogPals') {
       //              sh "pwd"
         //            script {
	//		System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "86400");
          //              dockerImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
            //         }
              //  }
                // script {
                //     dockerImage = docker.build "bharatkareti/dogpals:dogpals_presentation$BUILD_NUMBER"
                // }
           // }
        //}
        //Push our newly created image to dockerhub
        stage('Push image to Dockerhub') {
            steps {
                script {
                    //Assume the Docker Hub registry by passing an empty string as the first parameter
                    docker.withRegistry('' , 'docker-hub') {
			    dockerPresentationImage.push()
			    dockerTrainingImage.push()
		
			   
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
        } 
    }
}
