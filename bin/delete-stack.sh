#!/bin/bash
#
# Delete a CloudFormation stack. This is best effort and failing to delete a stack won't fail the script.
#
# This script assumes following are installed on the build host:
# - AWS CLI

usage() {
    echo "Usage:"
    echo "    delete-stack.sh -n <stack-name>      Delete CloudFormation stack by name."
    echo "Example:"
    echo "    delete-stack.sh -n my-stack"
}

arg_parse() {
    while getopts "n:" opt; do
      case ${opt} in
        n )
          STACK_NAME=${OPTARG}
          ;;
       \? )
         usage
         exit 1
         ;;
      esac
    done
    shift $((OPTIND -1))

    if [[ -z ${STACK_NAME} ]]
    then
      usage
      exit 1;
    fi
}

main() {
    arg_parse "$@"
    echo "Deleting stack: ${STACK_NAME}"
    aws cloudformation delete-stack --stack-name ${STACK_NAME}
    exit 0
}

# Spin up hook
main "$@"