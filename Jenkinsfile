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
		    echo "Git Branch :"
		    echo env.GIT_BRANCH
		    echo env.GIT_LOCAL_BRANCH
		    sh "chmod +x gradlew"
                    sh "./gradlew compileJava"
               }
          }
          stage("Unit test") {
	      when {
		 expression {
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
     }
}
