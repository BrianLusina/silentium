# Silentium

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d50fb64b11a74899ae6c8285f96e1ed8)](https://www.codacy.com/app/BrianLusina/silentium?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=BrianLusina/silentium&amp;utm_campaign=Badge_Grade)

Silentium means silent in Latin, this is a toy app that allows your device to go silent on certain pre-define regions that you select. This utilizes [GeoFences](https://developer.android.com/training/location/geofencing.html) API from Google.

## Getting Started

You will need an API key to get started and this should be the first step.
Get an API key from [here](https://console.developers.google.com) from an existing Google Project or you can create a new one in the console.

Set the API key in a `gradle.properties` file at the root of the project. This file should not be pushed to VCS and the [`.gitignore`](./.gitignore) file has this included>

```properties
GEO_API_KEY=<YOUR_API_KEY>
SILENTIUM_SERVICE_ACCOUNT_EMAIL=<PUBLISH_KEY>
RELEASE_TRACK=<YOUR_PREFERRED_RELEASE_TRACK>
VERSION_CODE=<VERSION_CODE>
VERSION_NAME=<VERSION_NAME>
```

The [`app`](./app) module's [build.gradle](./app/build.gradle) file has this set to Build fields making the Key available in the code:

```groovy
buildTypes.each {
    it.buildConfigField "String", "GEO_API_KEY", GEO_API_KEY
    it.buildConfigField "String", "GEO_API_KEY", GEO_API_KEY
}
```

Also adding this line to the `defaultConfig` block in the build.gradle file will ensure that this key is available in the [AndroidManifest.xml](./app/src/main/AndroidManifest.xml) file as a field property:

```groovy
android{
    defaultConfig{
        // ...
        manifestPlaceholders = [geoApiKey: GEO_API_KEY]
    }
    // ...
}
```

And lastly adding the field to the AndroidManifest:

```xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
      
      <!--.... -->    
      <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="${geoApiKey}" />
            
</application>
```
> Note the ${geoApiKey} in the meta-data tag

This allows us to hide the key from pushing it to VCS and can be build on any dev machine by simply updating the gradle.properties file.


## Pre-requisites

You will need the latest version of [Android Studio](https://developer.android.com/studio/index.html), as of writing that is 3.0.1, a later version will also be a great advantage.

A good working knowledge of [Kotlin](https://kotlinlang.org/) as this project is written in the Kotlin language and also a good understanding of [Dependency Inject](https://en.wikipedia.org/wiki/Dependency_injection)/Inversion of Control(IoC)

## Running Tests
 
 Tests have been written around Presenter Logic and will mostly run on the JVM as this is faster than running tests on an Emulator or a connected device. This is enabled due to the structure around [MVP](https://antonioleiva.com/mvp-android) to separate business logic from the UI/View.
 
 Running the tests can be done as follows:
 
```bash
./gradlew test
```
> Use the wrapper to run commands, as this is better than downloading a version of gradle on your development machine to run tasks. This will do the downloading and setting the correct gradle version to use.

To get a list of more tasks to run. Use:

```bash
./gradlew tasks
```
> Will display all the tasks available to you


## Deployment

You can deploy this project to any platform that allows hosting of apk files. The preferred destination being Google Play Store. However, you will need to update the package name of the application to your own, i.e. changing from `com.silentium` to `com.your.package.name` in the [AndroidManifest.xml](./app/src/main/AndroidManifest.xml) and [build.gradle](./app/build.gradle) files.

## Built With

+ [Kotlin Programming Language](https://kotlinlang.org) - Language used to write up the application
+ [Dagger](https://google.github.io/dagger/) - For dependency injection to allow creating an MVP structure for the application
+ [Anko Commons](https://github.com/Kotlin/anko) - syntactic sugar around using common utils in the Android API. 
+ [Gradle](https://gradle.org/) Build system for building the  application and obtaining dependencies
+ Coffee and Music - for keeping my eyelids open and mind awake

## Contributing

Please read [CONTRIBUTING](./CONTRIBUTING.md) for details on contribution and [CODE_OF_CONDUCT](./CODE_OF_CONDUCT.md) for details on code of conduct, and the process for submitting pull requests read [PULL_REQUEST_TEMPLATE](./PULL_REQUEST_TEMPLATE.md)

## Versioning

[SemVer](https://semver.org/) is used for versioning. For the versions available, see the [tags](https://github.com/BrianLusina/silentium/tags) on this repository.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

### Screenshots

![Screenshot1](screenshots/screen_1.png) ![Screenshot2](screenshots/screen_2.png) ![Screenshot3](screenshots/screen_3.png)
![Screenshot4](screenshots/screen_4.png) ![Screenshot5](screenshots/screen_5.png) ![Screenshot6](screenshots/screen_6.png)


### Report Issues
Notice any issues with a repository? Please file a github issue in the repository.

[![forthebadge](http://forthebadge.com/images/badges/built-for-android.svg)](http://forthebadge.com)
[![forthebadge](http://forthebadge.com/images/badges/built-with-love.svg)](http://forthebadge.com)