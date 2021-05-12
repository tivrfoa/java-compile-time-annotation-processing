#!/bin/sh

echo '----------- Compiling Get.java'
javac Get.java
echo '----------- Compiling GetProcessor.java'
javac GetProcessor.java
echo '----------- Compiling Person.java'
javac Person.java
echo '----------- Compiling main.java'
javac main.java
echo '----------- Running main'
java main
