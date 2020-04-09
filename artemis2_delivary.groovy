node { 

properties( 

[parameters( 

[choice(choices:  

[ 

'0.1',  

'0.2',  

'0.3',  

'0.4',  

'0.5'],  

description: 'Which version of the app should I deploy? ',  

name: 'Version'),
choice(choices:  
[ 
'dev1.theaizada.com',  
'qa1.theaizada.com',  

'stage1.theaizada.com',  

'prod1.theaizada.com'],  

description: 'Please provide an environment to build the application',  

name: 'ENVIR')])]) 


stage("Stage1"){ 

timestamps { 

ws { 

 checkout([$class: 'GitSCM', branches: [[name: '${Version}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/farrukh90/artemis.git']]])		}
} 

} 

stage("Get Credentials"){ 

timestamps { 

ws{ 

sh ''' 

aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis 

''' 

} 

} 

} 

stage("Build Docker Image"){ 

timestamps { 

ws { 

sh ''' 

docker build -t artemis:${Version} . 

''' 

} 

} 

} 

stage("Tag Image"){ 

timestamps { 

ws { 

sh ''' 

docker tag artemis:${Version} 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version} 

''' 

} 
} 

} 

stage("Push Image"){ 

timestamps { 

ws { 

sh ''' 

docker push 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version} 

''' 

} 

} 

} 

stage("Send slack notifications"){ 

timestamps { 

ws { 

echo "Slack" 

//slackSend color: '#BADA55', message: 'Hello, World!' 

} 

} 

} 


stage("Clean Up"){ 

timestamps { 

ws { 

try { 

sh ''' 

#!/bin/bash 

IMAGES=$(ssh centos@dev1.acirrustech.com docker ps -aq)  

for i in \$IMAGES; do 

ssh centos@dev1.acirrustech.com docker stop \$i 

ssh centos@dev1.acirrustech.com docker rm \$i 

done  

''' 

} catch(e) { 

println("Script failed with error: ${e}") 

} 

} 

} 

} 
stage("Run Container"){ 

timestamps { 

ws { 

sh ''' 

ssh centos@dev1.acirrustech.com docker run -dti -p 5001:5000 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version} 
''' 

} 

} 

} 

} 


 