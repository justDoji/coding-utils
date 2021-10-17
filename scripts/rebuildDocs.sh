#!/usr/bin/bash

echo '  (>^v^)> -{ Rebuilding JavaDocs } '
rm -rf ./docs/javadoc/*
rm -rf ./target/generated-sources/delombok

mvn lombok:delombok
mvn javadoc:javadoc

rm -rf ./target/generated-sources/delombok
