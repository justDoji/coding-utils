#!/usr/bin/env bash

mvn clean install
sh ./rebuildDocs.sh
rm -rf ./target/generated-sources/delombok
