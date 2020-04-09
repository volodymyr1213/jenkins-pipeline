node { 

stage("Stage1"){ 
// pull the repo and perform the task 
git 'https://github.com/farrukh90/packer.git' 

} 

stage("Stage2"){ 

echo "hello" 

} 

stage("Stage3"){ 

echo "hello" 

} 

stage("Stage4"){ 

echo "hello" 

} 

stage("Stage5"){ 

echo "hello" 

} 

} 