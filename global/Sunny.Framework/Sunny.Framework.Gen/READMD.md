```shell
rm -rf Generator && dotnet ef dbcontext scaffold "Server=" \
MySql.EntityFrameworkCore \
--context-dir Generator/Data \
--output-dir Generator/Models \
--context AppDbContext \
--force \
-d
```