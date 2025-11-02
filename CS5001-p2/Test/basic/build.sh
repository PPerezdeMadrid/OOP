#!/bin/bash
JUNITPATH=$TESTDIR/../lib
ALONEPATH=$TESTDIR/../lib-alone
FILES=$(find . -name '*.java' ! -path './staffprivatetest/*')
javac -Xlint:none -cp "$JUNITPATH/*:$ALONEPATH/*:." $FILES
