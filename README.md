# mbt-algorithms

This repository deals with the topic of [model-based testing](http://mit.bme.hu/~micskeiz/pages/modelbased_testing.html), even more concretely test case generation.

As models are usually represented as graphs, it is possible to use (simple) graph travelling algorithms for generating test cases from models. This repository contains implementation of a couple of these algorithms, a random one, another simple one, and Euler circle. Furthermore, it deals with algorithms, which can take a finite state machine from an unknown state to a known one, called [synchronizing sequence](https://www.springer.com/gp/book/9783540262787) and a similar one, called [homing sequence](https://www.springer.com/gp/book/9783540262787).

## Prerequisites
+ Java 1.8
+ Maven
