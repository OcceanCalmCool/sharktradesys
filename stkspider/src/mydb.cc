#include "mydb.hh"
 
CMyDB::CMyDB()
{  
    connection = mysql_init(NULL);
    if(connection == NULL)
    {
        perror("mysql_init");
        exit(1);
    }
}
CMyDB::~CMyDB()
{  
    if(connection != NULL)
    {
        mysql_close(connection);
    }
}

bool CMyDB::initDB(string server_host , string user, string password , string db_name, unsigned int port)
{  
    connection = mysql_real_connect(connection , 
                                    server_host.c_str() , 
                                    user.c_str() , 
                                    password.c_str() , 
                                    db_name.c_str() , 
                                    port , 
                                    NULL , 
                                    0);
    if(connection == NULL)
    {
        perror("mysql_real_connect");
        exit(1);
    }
    return true;
}
bool CMyDB::executeSQL(string sql_str)
{
    if(mysql_query(connection, "set names utf8"))
    {
        fprintf(stderr, "%d: %s\n",mysql_errno(connection), mysql_error(connection));
    }
    int t = mysql_query(connection,  sql_str.c_str());
    if(t)
    {
        printf("Error making query: %s\n" , mysql_error(connection));
        exit(1);
    }
    else
    {  
        res = mysql_use_result(connection);
        if(res)
        {
            for(int i = 0 ; i < mysql_field_count(connection) ; i++)
            {  
                row = mysql_fetch_row(res);    
                if(row <= 0)
                {
                    break;
                }
                for(int r = 0 ; r  < mysql_num_fields(res) ; r ++)
                {
                    printf("%s\t" , row[r]);   
                }
                printf("\n");
            }
        }
        mysql_free_result(res);
    }
    return true;
}

bool CMyDB::create_table(string table_str_sql)
{
    int t = mysql_query(connection , table_str_sql.c_str());
    if(t)
    {
        printf("Error making query: %s\n" , mysql_error(connection));
        exit(1);
    }
    return true;
}
