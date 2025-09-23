```shell
rm -rf Generator && dotnet ef dbcontext scaffold "Server=" \
MySql.EntityFrameworkCore \
--context-dir Generator/Repository \
--output-dir Generator/Domain \
--context AppDbContext \
--force \
-d \
--namespace WishServer
```