#!/bin/bash

# Deploy the packaged SAM templates under target/sam/app folder for a given module in the repository.
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing

usage() {
    echo "Usage:"
    echo "    deploy-module.sh -n <submodule-name> -s <stage>   Deploy SAM template for all modules."
    echo "Example:"
    echo "    deploy-module.sh -n backend -s dev"
}

arg_parse() {
    while getopts "n:s:" opt; do
      case ${opt} in
        n )
          MODULE=${OPTARG}
          ;;
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

    if [[ -z ${MODULE} ]] || [[ -z ${STAGE} ]]
    then
      echo ${MODULE} ${STAGE}
      usage
      exit 1;
    fi
}

deploy() {
    DIR=`dirname "${BASH_SOURCE[0]}"`
    echo "Deploying module: ${MODULE}"
    ${DIR}/deploy.sh -n ${MODULE}-${STAGE} -t ${DIR}/../${MODULE}/target/sam/app/packaged-template.yaml -o "Stage=${STAGE}"
}

main() {
    arg_parse "$@"
    deploy
}

# Spin up hook
main "$@"
