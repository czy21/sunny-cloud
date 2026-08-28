#!/bin/bash

dir=$(cd "$(dirname "$0")"; pwd)
source ${dir}/_common.sh

$CLI --exec '
from domain.mysql import MySQLSource

db_source=MySQLSource(context)
db_source.restore()
' \
$@