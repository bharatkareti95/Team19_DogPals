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
	     stage('Building training image and pushing it to registry'){
		     steps {
		 	    dir('DogPalsTraining') {
		 		    sh "pwd"
				    sh "./mvnw -ntp -DskipTests verify jib:build -Djib.to.image=bharatkareti/dogpals_training -Djib.to.tags=$BUILD_NUMBER,latest"
		 		    //sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_training -Djib.to.tags=$BUILD_NUMBER,latest"
		 	    }
		     }
	     }
	     stage('Building forum image and pushing it to registry'){
		     steps {
		 	    dir('DogPalsForum') {
		 		    sh "pwd"
				    sh "./mvnw -ntp -DskipTests verify jib:build -Djib.to.image=bharatkareti/dogpals_forum -Djib.to.tags=$BUILD_NUMBER,latest"
		 		    //sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_forum -Djib.to.tags=$BUILD_NUMBER,latest"
		 	    }
		     }
	     }
	     stage('Building front-end image and pushing it to registry'){
	 	    steps {
	 		    dir('dogPals') {
	 			    sh "pwd"
				    //sh "sudo -s"
				    //script{
					//    System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "86400");
				    //}
				    sh "./mvnw -ntp -DskipTests verify jib:build -Djib.to.image=bharatkareti/dogpals_frontend -Djib.to.tags=$BUILD_NUMBER,latest"
	 			    //sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_frontend -Djib.to.tags=$BUILD_NUMBER,latest"
	// //		            sh "./mvnw -DskipTests package -Pprod verify jib:build -Djib.to.image=bharatkareti/dogpals_frontend:latest"
                         }
	 	    }
	     }
	    
		    
     
        //Retrieve image from dockerhub and run container on port 9000 with the same name as the image
         //stage('Deploying via docker-compose') {
             //steps {
                 //dir('docker-compose') {
                        //sh "pwd"
                         //System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "86400");
		 			//sh 'docker-compose down --volumes'
		 			//sh 'docker-compose up -d'
        //             //sh 'docker-compose -f src/main/docker/app.yml up -d'
                 //} 
             //}  
         //} 
		stage('Pulling and Deploying jhipster-registry image'){
			steps {
				sh'echo "Starting to deploy docker image.."'
				sh 'docker pull jhipster/jhipster-registry'
				sh 'docker ps -q --filter ancestor=jhipster/jhipster-registry | xargs -r docker stop'
				sh 'docker run -d -p 8761:8761 jhipster/jhipster-registry'
			}
		}
		stage('Pulling and Deploying training image'){
			steps {
				sh'echo "Starting to deploy docker image.."'
				sh 'docker pull bharatkareti/dogpals_training:latest'
				sh 'docker ps -q --filter ancestor=bharatkareti/dogpals_training:latest | xargs -r docker stop'
				sh 'docker run -d -p 8081:8081 bharatkareti/dogpals_training:latest'
			}
		}
	    	stage('Pulling and Deploying forum image'){
			steps {
				sh'echo "Starting to deploy docker image.."'
				sh 'docker pull bharatkareti/dogpals_forum:latest'
				sh 'docker ps -q --filter ancestor=bharatkareti/dogpals_forum:latest | xargs -r docker stop'
				sh 'docker run -d -p 8082:8082 bharatkareti/dogpals_forum:latest'
			}
		}
		stage('Pulling and Deploying front end image'){
			steps {
				sh'echo "Starting to deploy docker image.."'
				sh 'docker pull bharatkareti/dogpals_frontend:latest'
				sh 'docker ps -q --filter ancestor=bharatkareti/dogpals_frontend | xargs -r docker stop'
				sh 'docker run -d -p 8080:8080 bharatkareti/dogpals_frontend'
			}
		}
    }
}
