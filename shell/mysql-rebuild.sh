#!/bin/bash

dir=$(cd "$(dirname "$0")"; pwd)
source ${dir}/_common.sh

$CLI --exec '
from domain.source.mysql import MySQLSource

db_source=MySQLSource(context)
db_source.assemble()
db_source.recreate()
db_source.execute()
' \
$@