# Android Application using Retrofit
An Experimental Android application of Mine built to get and send data by HTTP Request using Retrofit, which request to Fake Online API from https://jsonplaceholder.typicode.com/

## Explanation
### Retrofit
An awesome library to do HTTP Request, it turns API to Java Interface, of course by cooperating with its model either

### GSON
This library helps Serialization and Deserialization of JSON Object

### Logging Interceptor
This library will capture every log of activity made by our request to server

## Tech Stack
- Java Language
- Android SDK version : 28
- Retrofit library, implement following code in your app build.gradle
```
implementation 'com.squareup.retrofit2:retrofit:2.4.0'
```
- Also implement gson
```
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
```
- Implement logging interceptor
```
implementation 'com.squareup.okhttp3:logging-interceptor:3.12.1'
```
