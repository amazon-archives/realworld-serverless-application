#!/bin/bash

# Builds and packages static website SAR static website module

set -e # fail script on any individual command failing

VUE_CLI=$(which vue)
if [ -z "$VUE_CLI" ] ; then
  echo "Cannot find vue CLI in path. Please ensure vue CLI is installed and in your path."
  exit 1
fi

DIR=`dirname "${BASH_SOURCE[0]}"`
# build production static website
cd ${DIR}/..
npm install
npm run ci

