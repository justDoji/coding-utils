# coding-utils

Generic utilities to promote a clean design of the code we write on a daily basis.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sddevelopment-be_coding-utils&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sddevelopment-be_coding-utils)

## Structure

The source code will contain the classes needed to simplify your code. If you are interested in
seeing them in action, take a look at the test cases for a certain utility. These will give you a
very concrete and runnable example of the utility class in action.

## Usage

### Maven

Include one of the following dependencies in your `pom.xml` file:

**Test utilities:**
```xml
<dependency>
    <groupId>be.sddevelopment.commons</groupId>
    <artifactId>commons-testing</artifactId>
    <version>1.0.1</version>
    <scope>test</scope>
</dependency>
```

**Generic code utilities:**
```xml
<dependency>
    <groupId>be.sddevelopment.commons</groupId>
    <artifactId>commons</artifactId>
    <version>1.0.1</version>
</dependency>
```

### API Documentation

If you are wondering how you can use this project, you should do the old-school thing and RTFM (read
the flippin' manual).

The code is documented using standard Java practices, meaning that the public facing APIs are
documented in JavaDoc. This JavaDoc is then processed through maven and hosted on a github-pages
webspace, which you can find here:
[deployed javadocs](https://sddevelopment-be.github.io/coding-utils/javadoc/). To learn more about
github-pages, take a look at the [github documentation](https://pages.github.com/).

## Contributing

Thank you for your interest in this repository. If you would like to help out, consider the
following:

There are various ways to contribute to this repository. You can help us out by:

* Using the library and providing feedback
* Come up with ideas or patterns to be included
* Log issues in the github issue tracker
* Send a Pull-Request

You will need to use a compatible code style. The IntelliJ stylesheet
is [included in the ./docs/utils](./docs/utils/SDDStyle.xml) folder. 




