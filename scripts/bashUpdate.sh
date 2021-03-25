#!/usr/bin/bash

echo ''
cat ./aliases >> ~/.bash_aliases
# shellcheck disable=SC1090
source ~/.bash_aliases
