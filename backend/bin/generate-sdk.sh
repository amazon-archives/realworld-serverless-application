#!/bin/bash

#
# Generate SDK from API Gateway and put it under test sources
#
# This script assumes the following are installed on the host:
# - AWS CLI

set -e # fail script on any individual command failing

usage() {
    echo "Usage:"
    echo "    generate-sdk.sh -i <rest-api-id>     Generate SDK from API Gateway Rest API ID"
    echo "Example:"
    echo "    generate-sdk.sh -i 4a9oerm0eh"
}

arg_parse() {
    while getopts "i:" opt; do
      case ${opt} in
        i )
          REST_API_ID=${OPTARG}
          ;;
       \? )
         usage
         exit 1
         ;;
      esac
    done
    shift $((OPTIND -1))

    if [[ -z ${REST_API_ID} ]]
    then
      usage
      exit 1;
    fi
}

generate_sdk() {
    MODULE_DIR=`dirname "${BASH_SOURCE[0]}"`/..
    mkdir -p ${MODULE_DIR}/target

    ZIP_PATH=${MODULE_DIR}/target/api-sdk.zip
    echo "Generating SDK from API Gateway Rest API ${REST_API_ID} to ${ZIP_PATH}"

    aws apigateway get-sdk \
        --rest-api-id ${REST_API_ID} \
        --stage-name Prod \
        --sdk-type java \
        --parameters service.name='AWSServerlessApplicationRepository',java.package-name='software.amazon.serverless.apprepo.api.client' ${ZIP_PATH}

    UNZIP_DIR=${MODULE_DIR}/target/sdk
    echo "Unzipping SDK zip to directory ${UNZIP_DIR}"
    mkdir -p ${UNZIP_DIR}
    unzip ${ZIP_PATH} -d ${UNZIP_DIR}

    echo "Copying generated SDK to test source directory"
    rsync -avr --exclude=${UNZIP_DIR}/generated-code/pom.xml \
               --exclude=${UNZIP_DIR}/generated-code/README.html \
               --exclude=${UNZIP_DIR}/generated-code/README.md \
               ${UNZIP_DIR}/generated-code/src/main/* ${MODULE_DIR}/src/test/
}

main() {
    arg_parse "$@"
    generate_sdk
}

# Spin up hook
main "$@"