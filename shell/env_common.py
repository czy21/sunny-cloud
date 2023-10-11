#!/usr/bin/env python3

from domain.default import path as default_path
from utility import path as path_util

shell_path = path_util.join_path(default_path.root_path, "shell")

param_main_db_mysql_user = "admin"
param_main_db_mysql_pass = ""

param_main_db_mysql_file_path = path_util.join_path(default_path.project_db, "mysql", "1_version")
