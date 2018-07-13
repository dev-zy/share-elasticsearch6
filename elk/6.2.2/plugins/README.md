# Elasticsearch 5.x/6.x
1. run
./bin/elasticsearch-plugin install file:///home/yourFolder/elasticsearch-sql-x.x.x.x.zip
2. Use node to run site / just click on index.html (make sure to enable cors on elasticsearch.yml)
- download es-sql-site-standalone.zip and unzip it
- install node.js if you are not having one
- run the following commands and then visit http://yourHost:8080 (You can change port under site_configuration.json)

> cd site-server

> npm install express --save

> node node-server.js

3. Use elasticsearch sql site chrome extension (make sure to enable cors on elasticsearch.yml)
```yml
http.cors.enabled: true 
http.cors.allow-origin: "*"
```
