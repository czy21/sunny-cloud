#!/usr/bin/env python3
from .. import env_common

param_env_suffix = "local"

param_main_db_name = env_common.param_project_name
param_main_db_bak_name = "_".join([param_main_db_name, "bak"])

db_host = "192.168.2.18"

# mysql
param_main_db_mysql_host = db_host
param_main_db_mysql_port = "3306"
