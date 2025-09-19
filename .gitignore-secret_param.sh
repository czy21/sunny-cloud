#!/bin/bash

param_sed_args="sed "
function remove_secret_param() {
    secret_value=${2:-"<secret_value>"}
    param_sed_args+=" -e \"s|^\($1:\)\(.*\)|\1 $secret_value|\""
}

remove_secret_param 'param_main_db_\(.*\)_password'

param_sed_args+=" $1"
echo $param_sed_args > .gitignore-secret_param.log
eval $param_sed_args