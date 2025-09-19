SELECT 

total_worker_time/1000 AS [总消耗CPU 时间(ms)],execution_count [运行次数],

 qs.total_worker_time/qs.execution_count/1000 AS [平均消耗CPU 时间(ms)],

 last_execution_time AS [最后一次执行时间],max_worker_time /1000 AS [最大执行时间(ms)],

 SUBSTRING(qt.text,qs.statement_start_offset/2+1,

 (CASE WHEN qs.statement_end_offset = -1

 THEN DATALENGTH(qt.text)

 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2 + 1)

AS [使用CPU的语法], qt.text [完整语法],

dbname=db_name(qt.dbid),

object_name(qt.objectid,qt.dbid) ObjectName

FROM sys.dm_exec_query_stats qs

CROSS apply sys.dm_exec_sql_text(qs.sql_handle) AS qt

WHERE execution_count>1

ORDER BY total_worker_time DESC



SELECT request_session_id spid, OBJECT_NAME( resource_associated_entity_id )
tableName FROM sys.dm_tran_locks WHERE resource_type = 'OBJECT'

kill 109


WITH CTE_SID ( BSID, SID, sql_handle )
          AS ( SELECT   blocking_session_id ,
                        session_id ,
                        sql_handle
               FROM     sys.dm_exec_requests
               WHERE    blocking_session_id <> 0
               UNION ALL
               SELECT   A.blocking_session_id ,
                        A.session_id ,
                        A.sql_handle
               FROM     sys.dm_exec_requests A
                        JOIN CTE_SID B ON A.SESSION_ID = B.BSID
             )
    SELECT  C.BSID ,
            C.SID ,
            S.login_name ,
            S.host_name ,
            S.status ,
            S.cpu_time ,
            S.memory_usage ,
            S.last_request_start_time ,
            S.last_request_end_time ,
            S.logical_reads ,
            S.row_count ,
            q.text
    FROM    CTE_SID C 
            JOIN sys.dm_exec_sessions S ON C.sid = s.session_id
            CROSS APPLY sys.dm_exec_sql_text(C.sql_handle) Q
    ORDER BY sid
	
	
	
	
	
	
	
	
	
	
	
	