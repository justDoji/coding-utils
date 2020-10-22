#!/usr/bin/env bash

rm -rf ./docs/*
mvn lombok:delombok
mvn javadoc:javadoc
