#!/bin/bash

# Build all the modules in the repository.
#

set -e # fail script on any individual command failing

echo "Building all modules..."
DIR=`dirname "${BASH_SOURCE[0]}"`
cd ${DIR}/..
mvn clean package
aws-serverless-app-repo-reference-static-website/bin/package-static-website.sh