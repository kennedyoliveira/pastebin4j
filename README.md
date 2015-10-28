# PasteBin4j
[![Build Status](https://travis-ci.org/kennedyoliveira/pastebin4j.svg?branch=master)](https://travis-ci.org/kennedyoliveira/pastebin4j)
![Latest Release](https://github-basic-badges.herokuapp.com/release/kennedyoliveira/pastebin4j.svg?text=latest--release)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.kennedyoliveira/pastebin4j.svg)](http://search.maven.org/#artifactdetails%7Ccom.github.kennedyoliveira%7Cpastebin4j%7C1.0.0%7Cjar)
![License](https://github-basic-badges.herokuapp.com/license/kennedyoliveira/pastebin4j.svg)
[![PayPayl donate button](http://img.shields.io/paypal/donate.png?color=yellow)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=CR4K3FDKKK5FA&lc=BR&item_name=Kennedy%20Oliveira&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted "Donate with paypal if you feels like helping me out :D")

### Paste bin API Implementation for Java

Super simples and efficient library implementing all the options provided by the PasteBin api.

With this library you can easily:

- Create pastes for users and as a Guest
- Delete pastes
- List users pastes
- Fetch user information
- Create User Session Key (Actually you doesn't need to do that, the library will handle it, but if you want you can create too)
- Get pastes contents (Currently just **PUBLIC** or **UNLISTED** pastes, **PRIVATE** pastes are not supported)

## Build Status Note
The build status may show as failed even if there are no errors, because the currently PasteBin API has a limit for free accounts, and since the tests use an free account to run, the limits can be already used in the day and the tests will fail, so, if the build is showing failed, doesn't mean it has any problem, can be just the free account limits.

More information on tests in the [Running tests](#running-tests) section. 

## Table of Contents
- [Build Status Note](#build-status-note)
- [Installation](#installation)
  - [Maven](#maven)
  - [Local installation](#local-installation)
- [Requirements](#requirements)
- [Examples](#examples)
  - [Core class](#core-class)
  - [Listing your pastes:](#listing-your-pastes)
  - [Creating a new Paste](#creating-a-new-paste)
  - [More examples](#more-examples)
- [Running tests](#running-tests)
- [JavaDocs](#javadoc)
- [Contribution](#contribution)
- [Problems & Suggestions](#problems--suggestions)
- [License](#license)
- [Donations](#donations)

## Installation

### Maven

You can add the following dependency to your maven or gradle based project:

```xml
<properties>
    <pastebin4j.version>1.0.0<pastebin4j.version>
<properties>

<dependency>
    <groupId>com.github.kennedyoliveira</groupId>
    <artifactId>pastebin4j</artifactId>
    <version>${pastebin4j.version}</version>
</dependency
```

You can get the last version looking at the top in the "maven-central" badge and replace in your `pom.xml` or `build.gradle`

### Local installation

You can build the project with gradle using the following command to install to you local repository:

```
git clone https://github.com/kennedyoliveira/pastebin4j.git
cd pastebin4j
./gradlew clean build install -x test
```

Exclude the tests because for testing you need to provide a `devkey`, `username` and `password`.

This command will build and install the project into your local maven repository and the artifacts generated will be in the `build/libs` directory inside your project folder.

## Requirements

To use the APi you'll need to have a developer key, that you can get from the PasteBin site, you go to this link: [Developer Key](http://pastebin.com/api#1).

## Examples

### Core class
All the interaction is provided by the `PasteBin` class, you need to create one passing your credentials and that is it!

### Listing your pastes:

```java
// Configuration for the Credentials
final String devKey = "dev-key";
final String userName = "user-name";
final String password = "password";

// Create a PasteBin object with the credentials
final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

// List all the pastes from the user!
// Pretty easy, isn't?
final List<Paste> pastes = pasteBin.listUserPastes();

// The method never returns null, so you can check if the list is empty to see if you have pastes or not
if (pastes.isEmpty()) {
    System.out.println("You don't have any pastes :(");
    return;
}

// Getting a paste
final Paste paste = pastes.get(0);

// Current info on pastes
System.out.println("Title: " + paste.getTitle());
System.out.println("Visibility: " + paste.getVisibility().name());
System.out.println("Unique Key: " + paste.getKey());
System.out.println("Syntax Highlight: " + paste.getHighLight().name());
System.out.println("Paste Date: " + paste.getLocalPasteDate());
System.out.println("Paste Expiration: " + paste.getExpiration());
System.out.println("Paste Expiration Date: " + paste.getLocalExpirationDate());
System.out.println("Hits: " + paste.getHits());
System.out.println("URL:  " + paste.getUrl());
System.out.println("Size: " + paste.getSize());

// Prints all information of all pastes
pastes.forEach(System.out::println);
```

### Creating a new Paste

```java
final String devKey = "dev-key";
final String userName = "user-name";
final String password = "password";

final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

//  Basic creation
final Paste paste = new Paste();

paste.setTitle("Testing API");
paste.setExpiration(PasteExpiration.ONE_HOUR);
paste.setHighLight(PasteHighLight.Java);
paste.setVisibility(PasteVisibility.UNLISTED);
paste.setContent("public class Teste { }");

final String url = pasteBin.createPaste(paste);

System.out.println("Paste created at url: " + url);
```

The api gives you enums with all the information so you can just select it easy, doesn't need to remember, the class `PasteHighLight` has all the SyntaxHighLight currently implemented in the PasteBin, the class `PasteExpiration` has all the possible values for Expiration in a Paste and the `PasteVisibility` has the visibility status of a paste.

You can create a guest Paste by using a `GuestPaste` instead of a paste.

```java
Paste p = new GuestPaste();
```

Even if you specify your `username` and `password` using this class your paste will be created as guest.

There are builder for creating the pastes that you can access by `Paste.newBuilder()`.

### More examples
Check the `src/examples` for more examples, there you can see how to list trends pastes, get user information and more!

## Running tests
To run the tests you need to specify an devkey, username and password.

Use it in command line like the following:

```
.\gradlew -Dpastebin4j.devkey=your-dev-key -Dpastebin4j.username=your-user-name -Dpastebin4j.password=your-password test
```

**Important Note:** The tests will create some pastes, so expect it :D

## JavaDoc
Check the JavaDocs here: [JavaDocs](http://kennedyoliveira.github.io/pastebin4j/javadoc/index.html).
The JavaDocs are for the latest release.

## Contribution
If you want to contribute you can fork the project and send pull requests, you can even provide your own implementation of the API just by creating a class that implements the `PasteBinApi` interface and pass it to the `PasteBin` constructor.

## Problems & Suggestions
If you encounter any problem, please report at [Issues](https://github.com/kennedyoliveira/pastebin4j/issues) i'm work on it the fast as i can.

## License
This project is licensed with MIT License, so you can freely use, modify and distribute.

## Donations
If this projects helped you and you feels like helping me back, consider a donation, it'll help me a lot!
Anyway, if this helped you i'm glad i could help you!
