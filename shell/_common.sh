#!/bin/bash

shopt -s expand_aliases

if [ -f  "~/.bash_aliases" ];then
  source ~/.bash_aliases
fi

python_exec="$HOME/.python3/Scripts/python3"

if [ -f "$HOME/.python3/bin/python3" ];then
  python_exec="$HOME/.python3/bin/python3"
fi

if [ -f "${python_exec}" ];then
  alias python3="${python_exec}"
fi

type python3

sh_file="${0}"
sh_name="$(basename ${0})"
run_py=${dir}/../script/run.py

CLI="${python_exec} -B ${run_py} --file ${sh_file}"