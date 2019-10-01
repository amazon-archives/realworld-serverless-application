#!/bin/bash

# Package the SAM templates under sam folder for all the modules in the repository.
#
# This script assumes the following environment variable is set:
# - PACKAGE_BUCKET: the S3 bucket name used to store packaged artifacts
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing

DIR=`dirname "${BASH_SOURCE[0]}"`
MODULES=(aws-serverless-app-repo-reference-backend aws-serverless-app-repo-reference-ops aws-serverless-app-repo-reference-static-website)
echo "Packaging all modules: ${MODULES}"
for MODULE in ${MODULES[@]}
    do
        ${DIR}/package.sh -n ${MODULE}
    done