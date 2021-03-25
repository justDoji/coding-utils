#!/usr/bin/bash

echo '  (>^v^)> -{ Rebuilding JavaDocs } '
rm -rf ./docs/*
rm -rf ./target/generated-sources/delombok

mvn lombok:delombok
mvn javadoc:javadoc

rm -rf ./target/generated-sources/delombok
