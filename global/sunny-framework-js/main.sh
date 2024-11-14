#!/bin/bash

cd $(cd "$(dirname "$0")"; pwd)
registry=http://nexus.czy21-internal.com/repository/npm-hosted/


for pkg in `ls -d packages/*`;do
  if [ "$(basename $pkg)" != "vue" ];then
    continue
  fi

  rm -rf $pkg/dist
  pkg_name=$(basename $pkg)
  npm run build -w @sunny-framework-js/$pkg_name
  if [ "$1" == "--deploy" ];then
    npm publish --registry $registry -w @sunny-framework-js/$pkg_name
  fi
done