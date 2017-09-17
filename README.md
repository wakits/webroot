# Web Root

Angular + Java webApp base

Angular installation from scratch:
- Install nodejs, com nodejs.com
  - download and install latest version, at the end:
  Node.js was installed at
   /usr/local/bin/node
  npm was installed at
   /usr/local/bin/npm
  Make sure that /usr/local/bin is in your $PATH.
  Make sure is OK on you console: 
  > $ node -v
  v6.11.3
- Install Angular CLI
	- npm install -g @angular/cli
- Install Typescript
    - npm install -g typescript
- Create new angular project:
	- under webcli folder run > ng new angular
- check the version created:
	- inside angular folder run > ng -v
- Run the client:
	- ng serve --port 42100
- Check on browser:
	- http://localhost:42100/ 

INSTALL MAVEN and make angular mananged by Maven
 - mvn archetype:generate -DgroupId=webroot.webcli -DartifactId=webcli -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
 - cd into src/main/
 - mkdir resources
 - mkdir webapp
 - mkdir ngapp
 
------ GIT commands usefully ------

Create the branch on your local machine and switch in this branch :

$ git checkout -b [name_of_your_new_branch]

Change working branch :

$ git checkout [name_of_your_new_branch]

Push the branch on github :

$ git push origin [name_of_your_new_branch]

When you want to commit something in your branch, be sure to be in your branch. Add -u parameter to set upstream.

You can see all branches created by using :
$ git branch

Which will show :
* approval_messages
  master
  master_clean

Add a new remote for your branch :

$ git remote add [name_of_your_remote] 

Push changes from your commit into your branch :

$ git push [name_of_your_new_remote] [name_of_your_branch]

Update your branch when the original branch from official repository has been updated :

$ git fetch [name_of_your_remote]

Then you need to apply to merge changes, if your branch is derivated from develop you need to do :

$ git merge [name_of_your_remote]/develop

Delete a branch on your local filesystem :

$ git branch -d [name_of_your_new_branch]

To force the deletion of local branch on your filesystem :

$ git branch -D [name_of_your_new_branch]

Delete the branch on github :

$ git push origin :[name_of_your_new_branch]

The only difference is the : to say delete, you can do it too by using github interface to remove branch : https://help.github.com/articles/deleting-unused-branches.
