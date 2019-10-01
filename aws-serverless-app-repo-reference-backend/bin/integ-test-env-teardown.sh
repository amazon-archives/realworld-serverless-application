#!/bin/bash

#
# Tear down integration tests environment.
#
# This script assumes following are installed on the build host:
# - AWS CLI

usage() {
    echo "Usage:"
    echo "    integ-test-env-teardown.sh -n <stack-name>  -i <integ-test-env-stack-name> Tear down integration tests environment."
    echo "Example:"
    echo "    integ-test-env-teardown.sh -n integ-test -i integ-test-env"
}

arg_parse() {
    while getopts "n:i:" opt; do
      case ${opt} in
        n )
          STACK_NAME=${OPTARG}
          ;;
        i )
          INTEG_RESOURCE_STACK_NAME=${OPTARG}
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

teardown() {
    MODULE_DIR=`dirname "${BASH_SOURCE[0]}"`/..
    ${MODULE_DIR}/../bin/delete-stack.sh -n ${STACK_NAME}
    ${MODULE_DIR}/../bin/delete-stack.sh -n ${INTEG_RESOURCE_STACK_NAME}
}

main() {
    arg_parse "$@"
    teardown
}

# Spin up hook
main "$@"
