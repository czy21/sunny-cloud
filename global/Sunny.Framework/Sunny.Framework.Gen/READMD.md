```shell
dotnet ef dbcontext scaffold "Server=" \
Pomelo.EntityFrameworkCore.MySql \
--context-dir Generator/Data \
--output-dir Generator/Models \
--context AppDbContext \
--force
-d
```