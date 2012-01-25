#!/bin/bash
curl "http://localhost:8080/manager/html/undeploy?path=/mucflatshares&amp;org.apache.catalina.filters.CSRF_NONCE=D124FEBA050719D304AEA107035E0F11" -F Undeploy=Undeploy -u tomcat:tomcat > /dev/null
curl -F deployWar=@target/mucflatshares.war http://localhost:8080/manager/html/upload?org.apache.catalina.filters.CSRF_NONCE=4F9E49E131A252ABE50F8E21B43E149C -u tomcat:tomcat > /dev/null

