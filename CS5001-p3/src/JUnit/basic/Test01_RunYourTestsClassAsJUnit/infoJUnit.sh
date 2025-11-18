#!/bin/bash
JUNITPATH=$TESTDIR/../../lib
ALONEPATH=$TESTDIR/../../lib-alone

java -cp "$JUNITPATH/*:$ALONEPATH/*:." org.junit.platform.console.ConsoleLauncher execute --disable-ansi-colors --details=tree -p=test | grep -a --invert-match -e 'at [osj][rua][gnv].[jr][ue][nf][il][te]'
