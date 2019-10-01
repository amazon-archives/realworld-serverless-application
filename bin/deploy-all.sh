#!/bin/bash

# Deploy the packaged SAM templates under target/sam/app folder for all the modules in the repository.
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing

usage() {
    echo "Usage:"
    echo "    deploy-all.sh -s <stage>   Deploy SAM template for all modules."
    echo "Example:"
    echo "    deploy-all.sh -s dev"
}

arg_parse() {
    while getopts "s:" opt; do
      case ${opt} in
        s )
          STAGE=${OPTARG}
          ;;
       \? )
         usage
         exit 1
         ;;
      esac
    done
    shift $((OPTIND -1))

    if [[ -z ${STAGE} ]]
    then
      usage
      exit 1;
    fi
}

deploy_all() {
    DIR=`dirname "${BASH_SOURCE[0]}"`
    MODULES=(aws-serverless-app-repo-reference-backend aws-serverless-app-repo-reference-ops aws-serverless-app-repo-reference-static-website)
    echo "Deploying all modules..."
    for MODULE in ${MODULES[@]}
        do
            ${DIR}/deploy-module.sh -n ${MODULE} -s ${STAGE}
        done
}

main() {
    arg_parse "$@"
    deploy_all
}

# Spin up hook
main "$@"
