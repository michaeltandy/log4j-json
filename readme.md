# log4j-json

A log4j Layout to produce JSON logs.
These are formatted with one logging event per line.

This is useful if you want to use your log files with something that runs on JSON
such as [Google BigQuery](https://bigquery.cloud.google.com/).

## What does the output look like?

```
{"timestamp":1352412458890,"date":"Nov 8, 2012 10:07:38 PM","hostname":"michael1","username":"michael","level":"INFO","thread":"main","classname":"uk.me.mjt.log4jjson.SimpleJsonLayoutTest","filename":"SimpleJsonLayoutTest.java","linenumber":25,"methodname":"testDemonstration","message":"Example of some logging"}
```
You can customise the format if you like, by extending SimpleJsonLayout and overriding the method after() - or you can fork the project and change it however you like!

## What should I put in my log4j properties file?

To log to the console:
```
log4j.rootLogger=INFO, a
log4j.appender.a=org.apache.log4j.ConsoleAppender
log4j.appender.a.layout=uk.me.mjt.log4jjson.SimpleJsonLayout
```

Some log details are [slower to gather than others](https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html).
If you want to disable logging slow details,
or just log them for events above a certain severity,
you can set a minimum level like this:
```
log4j.appender.a.layout.MinimumLevelForSlowLogging=WARN
```

You can log MDC properties by listing the keys you want to log for, 
comma separated, like so:
```
log4j.appender.a.layout.MdcFieldsToLog=asdf,zxcv
```
If your MDC fields have the same names as existing fields, 
or have commas in their names,
or have names starting or ending with whitespace,
that's unsupported sorry.

## I want to upload to Google BigQuery, what schema should I use?

Here's the one I used:
```
timestamp:integer, date:string, hostname:string, username:string, level:string, thread:string, ndc:string, classname:string, filename:string, linenumber:integer, methodname:string, message:string, throwable:string
```

## What license is it under?

This project is (c) Michael Tandy
it's released under the [MIT license](http://en.wikipedia.org/wiki/MIT_License).