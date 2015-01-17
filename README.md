## com.arrking.express

Process Orders in [BIZFLOW Service](https://github.com/arrking/com.arrking.bizflow), if tasks are handled, the consumers get notifications in [moBay](https://github.com/arrking/com.arrking.mobay) .

## Implementations

* Android Client Development
Android has linux in the underground, on top of that, is a Java Runtime. One must has a good understanding about Linux and Java to be master of Android. Tools used in com.arrking.express are Android Studio, Android Development Toolkit, Genymotion Android Emulator. 

* Backend APIs
The backend, com.arrking.bizflow is based on OpenSource BPM Engine, [activiti](http://www.activiti.org/). Activiti is a process builder, friendly to Business Guys and Developers. One can use activiti to make a flow without coding. As a developer, one can use java, javascript and groovy to make integrations easier.

In com.arrking.express, the communications are all based on REST API with bizflow server. To get a fullview of these apis, visit [http://www.activiti.org/userguide/index.html#_rest_api](http://www.activiti.org/userguide/index.html#_rest_api) .


## Engineering

### Installations
* Java SDK 7+
* Ant 
* Gradle
* Android Studio
* Genymotion

### Clone repo and config 
```
git clone git@github.com:arrking/com.arrking.express.git
cd com.arrking.bizflow
echo "sdk.dir=YOUR_ANDROID_SDK_PATH" > local.properties # e.g. in windows, something like this, D\:\\AndroidDevelopment\\sdk
./gradlew clean assembleRelease # it will take some time
./gradlew idea # generate Android Studio Project
./gradlew task # other tasks
```

## Auto Build

## Testing