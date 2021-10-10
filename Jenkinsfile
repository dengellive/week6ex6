pipeline {
     agent any
     triggers {
          pollSCM('* * * * *')
     }
     stages {
          stage("Compile") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "./gradlew compileJava"
               }
          }
          stage("Unit test") {
	      when {
		 expression {
			  return env.GIT_BRANCH == "origin/feature"
			  return env.GIT_BRANCH != "origin/playground"             
		 }
	      }
	       steps {
                    sh "./gradlew test"
               }
          }
          stage("Code coverage") {
               when {
		  expression {
			   return env.GIT_BRANCH == "origin/main"
			   return env.GIT_BRANCH != "origin/playground" 
		  }
	       }
	       steps {
                    sh "./gradlew jacocoTestReport"
                    sh "./gradlew jacocoTestCoverageVerification"
               }
          }
          stage("Static code analysis") {
	      when {
		 expression {
			  return env.GIT_BRANCH == "origin/feature"               
			  return env.GIT_BRANCH != "origin/playground"
		 }
	      }
               steps {
                    sh "./gradlew checkstyleMain"
               }
          }
          stage("Package") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "./gradlew build"
               }
          }

          stage("Docker build") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "docker build -t leszko/calculator:${BUILD_TIMESTAMP} ."
               }
          }

          stage("Docker login") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials',
                               usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                         sh "docker login --username $USERNAME --password $PASSWORD"
                    }
               }
          }

          stage("Docker push") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "docker push leszko/calculator:${BUILD_TIMESTAMP}"
               }
          }

          stage("Update version") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "sed  -i 's/{{VERSION}}/${BUILD_TIMESTAMP}/g' calculator.yaml"
               }
          }
          
          stage("Deploy to staging") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "kubectl config use-context staging"
                    sh "kubectl apply -f hazelcast.yaml"
                    sh "kubectl apply -f calculator.yaml"
               }
          }

          stage("Acceptance test") {
 	      when {
		 expression {
			  return env.GIT_BRANCH == "origin/feature"               
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sleep 60
                    sh "chmod +x acceptance-test.sh && ./acceptance-test.sh"
               }
          }

          stage("Release") {
 	      when {
		 expression {
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
               steps {
                    sh "kubectl config use-context production"
                    sh "kubectl apply -f hazelcast.yaml"
                    sh "kubectl apply -f calculator.yaml"
               }
          }
          stage("Smoke test") {
 	      when {
		 expression {
			  return env.GIT_BRANCH == "origin/feature"               
			  return env.GIT_BRANCH != "origin/playground"               
		 }
	      }
              steps {
                  sleep 60
                  sh "chmod +x smoke-test.sh && ./smoke-test.sh"
              }
          }
     }
}
