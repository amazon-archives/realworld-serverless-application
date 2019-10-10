#!/bin/bash

#
# Set up integration tests environment.
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing


usage() {
    echo "Usage:"
    echo "    integ-test-env-setup.sh -n <stack-name> -i <integ-test-env-stack-name> [-o <parameter-overrides>]  Set up integration tests environment."
    echo "Example:"
    echo "    integ-test-env-setup.sh -n integ-test -i integ-test-env -o \"Stage=prod\""
}

arg_parse() {
    while getopts "n:i:o:" opt; do
      case ${opt} in
        n )
          STACK_NAME=${OPTARG}
          ;;
        i )
          INTEG_RESOURCE_STACK_NAME=${OPTARG}
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

    if [[ -z ${STACK_NAME} ]] || [[ -z ${INTEG_RESOURCE_STACK_NAME} ]]
    then
      usage
      exit 1;
    fi
}

setup() {
    MODULE_DIR=`dirname "${BASH_SOURCE[0]}"`/..
    TEMPLATE=${MODULE_DIR}/target/sam/app/packaged-template.yaml
    INTEG_ENV_TEMPLATE=${MODULE_DIR}/src/test/resources/integ-test-env.template.yaml
    if [[ -z "${PARAMETERS}" ]]
    then
        ${MODULE_DIR}/../bin/deploy.sh -n ${STACK_NAME} -t ${TEMPLATE}
        ${MODULE_DIR}/../bin/deploy.sh -n ${INTEG_RESOURCE_STACK_NAME} -t ${INTEG_ENV_TEMPLATE}
    else
        ${MODULE_DIR}/../bin/deploy.sh -n ${STACK_NAME} -t ${TEMPLATE} -o ${PARAMETERS}
        ${MODULE_DIR}/../bin/deploy.sh -n ${INTEG_RESOURCE_STACK_NAME} -t ${INTEG_ENV_TEMPLATE} -o ${PARAMETERS}
    fi
}

main() {
    arg_parse "$@"
    setup
}

# Spin up hook
main "$@"
