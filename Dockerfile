FROM openjdk:8-jdk-alpine
## 建了一个目录 /video-website-backend-deploy，用于存放后续的文件和代码
#RUN mkdir -p /video-website-backend-deploy
#
## 指定了在接下来的命令中，工作目录都是 /video-website-backend-deploy
#WORKDIR /video-website-backend-deploy
#
## 定义了构建参数 JAR_FILE，并且给了它一个默认值/ruanfeng/video_website_backend/video_website_backend-1.0-SNAPSHOT.jar
## 这个参数可以在构建镜像时被传递，并在后续的命令中使用
#ARG JAR_FILE=/ruanfeng/video_website_backend/video_website_backend-1.0-SNAPSHOT.jar
#
## 这个命令将构建上下文中的 ${JAR_FILE} 路径下的文件复制到镜像中的
##/video-website-backend-deploy目录，并且重命名为 video_website_backend.jar
#COPY ${JAR_FILE} video_website_backend.jar
#
## 声明容器运行时监听的端口号
#EXPOSE 8081
#
## 设置两个环境变量 TZ 和 JAVA_OPTS
## 分别用于指定时区为亚洲/上海，并设置了 Java 虚拟机的参数
#ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
#
## 运行 java -jar app.jar 命令，并且传入 $JAVA_OPTS 参数
#CMD sleep 30; java -jar video_website_backend.jar $JAVA_OPTS

VOLUME /tmp

# 将本地JAR包打包到容器中，并重命名
ADD video_website_backend-1.0-SNAPSHOT.jar video_website_backend.jar

# 声明需要暴露的端口 在Dockerfile中声明了那些端口是需要开放的，在构建容器时通过-p可以随机映射端口，如果EXPOSE没有指定端口，那么使用-p参数无效。本配置只是声明，一般我们需要在构建docker容器时使用-p（小写的p）指定开放的端口。
EXPOSE 8081

ENV TZ=Asia/Shanghai

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","video_website_backend.jar"]



