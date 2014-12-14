# Arrking Services Tory
A mobile app for services store.

## What's arrking tory ?
As the Online to Offline Scenario has a big gap for service provider. Usually they
don't have a good enough IT System to support their business or they have systems
isolately.

Tory is a client tool to handle the interactions between staffs of service provider
and consumers.

## Persona

### Karen
Staff who works in kitchen, Karen can claim an order, then she begins to cook the food,
after all foods are done, she then mark her task as complete. The order then goes 
to Effie.

### Gail
Gail is the guest role, she choose what to eat, submit the order.

### Effie
Effie is a waitress, when karen has done her job, she then pick up the job.
She can also claim it and complete it. When she complete her job, the process is over.

## Project Management
Git Repository - https://github.com/arrking/com.arrking.tory 

## Engineering

### Installations

* Install xCode, NodeJS

* Install cordova, ionic
```
sudo npm install cordova@3.6.3-0.2.13 -g
sudo npm install ionic@1.2.8 -g
sudo npm install ios-sim@3.0.0 cordova-lib@4.0.0 -g
sudo gem update --system && sudo gem install compass
sudo npm install generator-ionic@0.6.1 -g
sudo npm update -g yo # make sure yo@1.1.2
```

### Get Source Code
```
git clone git@github.com:arrking/com.arrking.tory.git
```

### Launch Project
```
cd com.arrking.tory 
grunt platforms:add:android
grunt run:android
```


### Get started

* [Cordova](http://git.oschina.net/ubiware/tech-books/blob/master/apache-cordova-3-programming.pdf)
* [Ionic](http://ionicframework.com/)
* [AngularJS](http://git.oschina.net/ubiware/tech-books/blob/master/AngularJSIn60MinutesIsh_DanWahlin_May2013.pdf)


## Implementation of Tory

Tory share the same backend as [moBay](https://github.com/arrking/com.arrking.mobay).
The Backend Project is [Cafe](https://github.com/arrking/com.arrking.cafe).

## Android Commands
List Devices
```
adb devices
```

## Debugging
* Print logs

```
platforms/android/cordova/log | grep "Web Console"
```

* Launch App

```
grunt run:android
```

