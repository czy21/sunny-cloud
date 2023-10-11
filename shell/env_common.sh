#!/bin/bash
shopt -s expand_aliases
source ~/.bash_aliases

sh_name="$(basename ${0%.*})"
env_py=${dir}/_env.py
run_py=${dir}/../../script/run.py