node { 
properties([
    // Below line Sets "Diskard Builds more than 5"
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 


    // Below line triggers this job every minute 
    pipelineTriggers([pollSCM('* * * * *')])
       
        ])




stage("Pull Repo"){ 
 git 'https://github.com/farrukh90/cool_website.git'

} 

stage("Install Prerequisites"){
		sh """
		ssh centos@jenkins_worker1.theaizada.com                sudo yum install httpd -y
		
}

stage("Copy artifact"){ 
sh """
scp -r *  centos@jenkins_worker1.theaizada.com:/tmp
		ssh centos@jenkins_worker1.theaizada.com                 sudo cp -r /tmp/index.html /var/www/html/
		ssh centos@jenkins_worker1.theaizada.com                 sudo cp -r /tmp/style.css /var/www/html/
		ssh centos@jenkins_worker1.theaizada.com				   sudo chown centos:centos /var/www/html/
		ssh centos@jenkins_worker1.theaizada.com				   sudo chmod 777 /var/www/html/*
		
} 

stage("Restart web server"){ 
ssh centos@jenkins_worker1.theaizada.com                 sudo "systemstl restart httpd"
} 

stage("Slack"){
		slackSend color: '#BADA55', message: 'Hello, World!'
	}
}