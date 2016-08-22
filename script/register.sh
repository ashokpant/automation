#!/usr/bin/env bash

TARGET_PATH=/home/ashok/Projects/ashok/automation/automation/target
PROGRAM_JAR_FILE=automation-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar ${TARGET_PATH}/${PROGRAM_JAR_FILE} $1 $2 $3

