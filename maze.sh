#!/bin/bash
arguments="$@"
mvn exec:java \
    --quiet \
    -Dexec.mainClass="no.ntnu.idata2302.lab05.cli.Runner" \
    -Dexec.args="${arguments}"
