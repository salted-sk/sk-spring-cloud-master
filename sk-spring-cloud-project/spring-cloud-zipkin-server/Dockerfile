FROM java:8
#jar包下载地址https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
ADD *.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
# 将如下参数填写在command中
#--zipkin.collector.rabbitmq.addresses=47.104.23.202:8672
#\
#--zipkin.storage.type=mysql
#\
#--zipkin.storage.mysql.host=112.124.3.225
#\
#--zipkin.storage.mysql.port=3333
#\
#--zipkin.storage.mysql.username=root
#\
#--zipkin.storage.mysql.password=frzxbkw
#\
#--zipkin.storage.mysql.db=zipkin