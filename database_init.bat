docker exec -t -i mbe-db sed -i "s/#shared_preload_libraries = ''/shared_preload_libraries = 'pg_stat_statements'/g" /var/lib/postgresql/data/postgresql.conf
docker exec -t -i mbe-db psql -U postgres -d postgres -c "CREATE EXTENSION pg_stat_statements;"
docker restart mbe-db