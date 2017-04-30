node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a Gitlab repository
      git credentialsId: 'Steve\'s Gitlab Creds', url: 'https://StevenPG@gitlab.com/StevenPG/DynamicLinksCatalog.git'
      // Get the Maven tool.
      mvnHome = tool 'mvn'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' clean install"
      } else {
         bat(/"${mvnHome}\bin\mvn" clean install/)
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}