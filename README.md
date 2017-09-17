# Web Root

Angular + Java webApp base

#Angular installation from scratch follow by SpringBoot and maven:
– Java 1.8
– Maven 3.3.9
– Spring Tool Suite – Version 3.9.0.RELEASE
– Spring Boot: RELEASE
– Angular 4.x
– Node.js

#NodeJS
- Install nodejs from nodejs.com it returns:
  - download and install latest version, at the end:
  Node.js was installed at
   /usr/local/bin/node
  npm was installed at
   /usr/local/bin/npm
  Make sure that /usr/local/bin is in your $PATH.
  Make sure is OK on you console: 
  > $ node -v
  v6.11.3

#Angular and Typescript
  
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

#SpringBoot and Maven

INSTALL Springboot that includes maven too: form https://start.spring.io/
- It downloads a zip with the content that already includes the pom configuration.
- Create a Controlle: WebRestController.java mapped to 'api/hi'

#Integrate SpringBoot server and Angular 4 client

– Create a file proxy.conf.json under project angular project root with:
{
	"/api": {
		"target": "http://localhost:8080",
		"secure": false
	}
}
– Edit package.json file for “start” script and change:

"start": "ng server --proxy-config proxy.conf.json"

- Build angular4 client with command ng build -prod
> ng build -prod
will result in the content of the dist folder

- Update maven to copy the result of the dist folder from angular to static resources on springboot check: maven-resources-plugin on pom.xml
– Build: mvn clean install
– Run: mvn spring-boot:run
– Check http://localhost:8080/ and – http://localhost:8080/api/hi



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
