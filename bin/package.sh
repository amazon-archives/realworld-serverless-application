#!/bin/bash

# Packages SAM templates for a specific module, such as backend
#
# This script assumes the following environment variable is set:
# - PACKAGE_BUCKET: the S3 bucket name used to store packaged artifacts
#
# This script assumes following are installed on the build host:
# - AWS CLI

set -e # fail script on any individual command failing

usage() {
    echo "Usage:"
    echo "    package.sh -n <submodule-name> [-b <package-bucket>]     Package SAM template and upload artifacts to the package bucket."
    echo "Example:"
    echo "    package.sh -n backend"
}

arg_parse() {
    while getopts "n:b:" opt; do
      case ${opt} in
        n )
          MODULE=${OPTARG}
          ;;
        b )
          PACKAGE_BUCKET=${OPTARG}
          ;;
       \? )
         usage
         exit 1
         ;;
      esac
    done
    shift $((OPTIND -1))

    if [[ -z ${MODULE} ]]
    then
      usage
      exit 1;
    fi

    if [[ -z ${PACKAGE_BUCKET} ]]
    then
      echo "PACKAGE_BUCKET must be set either as environment variable or via the command"
      usage
      exit 1;
    fi
}

package() {
    module_dir=`dirname "${BASH_SOURCE[0]}"`/../${MODULE}

    for component in app cicd
    do
        template_file=${module_dir}/sam/${component}/template.yaml
        if [[ -f ${template_file} ]]; then
            output_template_file=${module_dir}/target/sam/${component}/packaged-template.yaml

            echo "Packaging SAM template: ${template_file}"
            mkdir -p `dirname ${output_template_file}`

            aws cloudformation package \
              --template-file ${template_file} \
              --s3-bucket ${PACKAGE_BUCKET} \
              --output-template-file ${output_template_file}
        else
            echo "Template file ${template_file} does not exist. Skip packaging."
        fi
    done
}

main() {
    arg_parse "$@"
    package
}

# Spin up hook
main "$@"
