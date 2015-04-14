# sense
Demo app that subscribes to Twitter and publishes messages between tiny services to parse the data and display it on a JavaFX Dashboard.

This application is used to demonstrate how Java 8 features such as lambdas and streams can be used in a real application, and I use it as the framework for a live demo [more details and resources here](http://trishagee.github.io/presentation/java8_in_anger/).

There are several branches - on master, you will see the fully working application, although if you want to connect to Twitter you'll need to create an oauth.properties file with the appropriate token values [see the Twitter documentation](https://dev.twitter.com/oauth/application-only).

The `skeleton` branch is the start-point of the demo, so if you want to build up the application from the "beginning" (much of the infrastructure code is provided for you), this is the branch to check out.

As I give the demo at various conferences, there will be branches for each conference showing the code that was implemented during that presentation.
