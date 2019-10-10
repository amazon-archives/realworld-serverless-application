#!/bin/bash

#
# Deploy a SAM template.
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing

usage() {
    echo "Usage:"
    echo "    deploy.sh -n <stack-name> -t <template> [-o <parameter-overrides>]     Deploy SAM template."
    echo "Example:"
    echo "    deploy.sh -n realworld-serverless-application-backend-prod -t backend/target/sam/app/packaged-template.yaml -o \"Stage=prod\""
}

arg_parse() {
    while getopts "n:t:o:" opt; do
      case ${opt} in
        n )
          STACK_NAME=${OPTARG}
          ;;
        t )
          TEMPLATE=${OPTARG}
          ;;
        o )
          PARAMETERS=${OPTARG}
          ;;
       \? )
         usage
         exit 1
         ;;
      esac
    done
    shift $((OPTIND -1))

    if [[ -z ${STACK_NAME} ]] || [[ -z ${TEMPLATE} ]]
    then
      usage
      exit 1;
    fi
}

deploy() {
    echo "Deploying SAM template: ${TEMPLATE} to Stack ${STACK_NAME} with parameter overrides ${PARAMETERS}"
    if [[ -z "${PARAMETERS}" ]]
    then
        aws cloudformation deploy \
          --template-file ${TEMPLATE} \
          --stack-name ${STACK_NAME} \
          --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
    else
        aws cloudformation deploy \
          --template-file ${TEMPLATE} \
          --stack-name ${STACK_NAME} \
          --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND \
          --parameter-overrides ${PARAMETERS}
    fi
}

main() {
    arg_parse "$@"
    deploy
}

# Spin up hook
main "$@"
