# 基础镜像使用Java
FROM nginx
# 作者
MAINTAINER sk
# VOLUME指定了临时文件目录为/tmp
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
ADD static /usr/share/nginx/html

# 指定容器需要映射到主机的端口
EXPOSE 80