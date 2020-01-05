跨域相关的配置：

1、Access-Control-Allow-Origin，这里使用变量 $http_origin取得当前来源域，大家说用“*”代表允许所有，我实际使用并不成功，原因未知；

2、Access-Control-Allow-Credentials，为 true 的时候指请求时可带上Cookie，自己按情况配置吧；

3、Access-Control-Allow-Methods，OPTIONS一定要有的，另外一般也就GET和POST，如果你有其它的也可加进去；

4、Access-Control-Allow-Headers，这个要注意，里面一定要包含自定义的http头字段（就是说前端请求接口时，如果在http头里加了自定义的字段，这里配置一定要写上相应的字段），从上面可看到我写的比较长，我在网上搜索一些常用的写进去了，里面有“web-token”和“app-token”，这个是我项目里前端请求时设置的，所以我在这里要写上；

5、Access-Control-Expose-Headers，可不设置，看网上大致意思是默认只能获返回头的6个基本字段，要获取其它额外的，先在这设置才能获取它；

6、语句“ if ($request_method = 'OPTIONS') { ”，因为浏览器判断是否允许跨域时会先往后端发一个 options 请求，然后根据返回的结果判断是否允许跨域请求，所以这里单独判断这个请求，然后直接返回；



server {
	listen       80;
	server_name  xxx.com;

	location /xxx-web/papi {
		add_header 'Access-Control-Allow-Origin' $http_origin;
		add_header 'Access-Control-Allow-Credentials' 'true';
		add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
		add_header 'Access-Control-Allow-Headers' 'DNT,web-token,app-token,Authorization,Accept,Origin,Keep-Alive,User-Agent,X-Mx-ReqToken,X-Data-Type,X-Auth-Token,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
		add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
		if ($request_method = 'OPTIONS') {
			add_header 'Access-Control-Max-Age' 1728000;
			add_header 'Content-Type' 'text/plain; charset=utf-8';
			add_header 'Content-Length' 0;
			return 204;
		}
		root   html;
		index  index.html index.htm;
		proxy_pass http://127.0.0.1:7071;
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_connect_timeout 5;
	}
	 
	location /xxx-web {
		add_header 'Access-Control-Allow-Origin' $http_origin;
		add_header 'Access-Control-Allow-Credentials' 'true';
		add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
		add_header 'Access-Control-Allow-Headers' 'DNT,web-token,app-token,Authorization,Accept,Origin,Keep-Alive,User-Agent,X-Mx-ReqToken,X-Data-Type,X-Auth-Token,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
		add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
		if ($request_method = 'OPTIONS') {
			add_header 'Access-Control-Max-Age' 1728000;
			add_header 'Content-Type' 'text/plain; charset=utf-8';
			add_header 'Content-Length' 0;
			return 204;
		}
		root   html;
		index  index.html index.htm;
		proxy_pass http://127.0.0.1:8080;
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_connect_timeout 5;
	}
	 
	location / {
		root   /var/www/xxx/wechat/webroot;
		index  index.html index.htm;
	}
	 
	error_page   500 502 503 504  /50x.html;
	location = /50x.html {
		root   html;
	}
}
