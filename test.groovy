node {
 properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
 artifactNumToKeepStr: '', 
 daysToKeepStr: '', 
 numToKeepStr: '5')), 
 disableConcurrentBuilds(), 
 parameters([choice(choices: ['v1', 
 'v2', 
 'v3', 
 'v4'],
  description: 'this is version of number ',
   name: 'version1'), 
   choice(choices: [
       'dev1.theaizada.com',
       'qa1.theaizada.com'
   ], 
   description: 'Please choose an envirenment', 
   name: 'aizada')]), 
   pipelineTriggers([pollSCM('* * * * *')])
   ])   
}

