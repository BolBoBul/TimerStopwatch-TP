[![Release](https://img.shields.io/github/v/release/sgl-umons/TimerStopwatch?label=Latest%20Release)](https://github.com/sgl-umons/TimerStopwatch/releases/latest)
![Maven Build](https://github.com/sgl-umons/TimerStopwatch/actions/workflows/maven.yml/badge.svg)
![CodeQL](https://github.com/sgl-umons/TimerStopwatch/actions/workflows/codeql.yml/badge.svg)
[![OpenSSF Scorecard](https://api.securityscorecards.dev/projects/github.com/sgl-umons/TimerStopwatch/badge)](https://securityscorecards.dev/viewer/?uri=github.com/sgl-umons/TimerStopwatch)
[![SWH](https://archive.softwareheritage.org/badge/origin/https://github.com/sgl-umons/TimerStopwatch/)](https://archive.softwareheritage.org/browse/origin/?origin_url=https://github.com/sgl-umons/TimerStopwatch)

# TimerStopwatch
Java implementation of a statechart with Timer and Stopwatch functionality.
This repository contains a simple example used for teaching purposes by Tom Mens at the University of Mons.
The example is an event-based application for a timer and stopwatch. It can be controlled through 3 buttons/events.
The example comes with UML models (created with Visual Paradigm) and executable statechart models (made with Itemis Create).

The aim of the example is to illustrate how to model composite statecharts, 
how to implement them in Java in object-oriented style using the State Design Pattern,
and how to test them with JUnit unit tests.


### Video Tutorial
See https://www.youtube.com/watch?v=5uYOPUj5J3Q
for a tutorial on YouTube that explains (a slightly older version of) this example Java source code.

### Prerequisites

*  You will need to have a running version of Java 21 on your machine in order to be able to compile and execute this code.
*  You will also need to have a running version of Maven, since this project is accompanied by a pom.xml file so that it can be installed, compiled, tested and run using Maven.

### Installation and testing instructions

The code can be compiled, tested and executed with maven:

- "mvn compile" to only compile the code
- "mvn test" to compile and test
- "mvn package" to compile, test and create a jar file
- "mvn install" to additionally execute the code

## Built With

*  [Maven](https://maven.apache.org/) - an open source build automation and dependency management tool
*  [JUnit5](https://junit.org/junit5/) - a unit testing framework for Java

## License

Source code distributed under [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0) license.

## Acknowledgments

* Software Engineering Lab, Faculty of Sciences, University of Mons, Belgium.
