### 授权码模式  

​			--可刷新token

（A）用户访问客户端，后者将前者导向认证服务器。

（B）用户选择是否给予客户端授权。

（C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。

（D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。

（E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。

1.获取code 此时可以不带secret

​	http://localhost:8888/oauth2-server/oauth/authorize?client_id=client-demo&secret=123456&response_type=code&redirect_uri=http://localhost/client-demo/login

2.登陆成功之后浏览器会跳转到上一步的redirece_uri并携带code

​	http://localhost/client-demo/login?code=QCR5jm

3.申请令牌（code只能使用一次，无论有没有申请成功，之后code失效）

​	使用postman发送post请求必须带上secret

​	localhost:8888/oauth2-server/oauth/token?client_id=client-demo&grant_type=authorization_code&redirect_uri=http://localhost/client-demo/login&client_secret=123456&code=653lgc

参数：

- response_type：表示授权类型，必选项，此处的值固定为"code"
- client_id：表示客户端的ID，必选项
- redirect_uri：表示重定向URI，可选项（需在数据库中存在）
- scope：表示申请的权限范围，可选项
- state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值

### 密码模式

​		--可刷新token

（A）用户向客户端提供用户名和密码。

（B）客户端将用户名和密码发给认证服务器，向后者请求令牌。

（C）认证服务器确认无误后，向客户端提供访问令牌。

- grant_type：表示授权类型，此处的值固定为"password"，必选项。

- username：表示用户名，必选项。

- password：表示用户的密码，必选项。

- scope：表示权限范围，可选项。

  

1.使用postman Authorization选择Basic Auth 填写客户端名称及密码

2.http://localhost:8888/oauth2-server/oauth/token

body中选中form-data添加上面四个对应参数使用post提交 即可获取token



### 客户端模式

​		--不可刷新token

（A）客户端向认证服务器进行身份认证，并要求一个访问令牌。

（B）认证服务器确认无误后，向客户端提供访问令牌。

使用同密码模式 但grant_type需设置为client_credentials，此时不需要用户名密码



### 简化模式

​	--不可刷新token，token直接发给客户端 可见  不安全

（A）客户端将用户导向认证服务器。

（B）用户决定是否给于客户端授权。

（C）假设用户给予授权，认证服务器将用户导向客户端指定的"重定向URI"，并在URI的Hash部分包含了访问令牌。

（D）浏览器向资源服务器发出请求，其中不包括上一步收到的Hash值。

（E）资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌。

（F）浏览器执行上一步获得的脚本，提取出令牌。

（G）浏览器将令牌发给客户端。

- response_type：表示授权类型，此处的值固定为"token"，必选项。
- client_id：表示客户端的ID，必选项。
- redirect_uri：表示重定向的URI，可选项。
- scope：表示权限范围，可选项。
- state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。

1.浏览器直接获取

​	http://localhost:8888/oauth2-server/oauth/authorize?response_type=token&client_id=client-demo&redirect_uri=http://localhost/client-demo/login



####更新令牌

- grant_type：表示使用的授权模式，此处的值固定为"refresh_token"，必选项。
- refresh_token：表示早前收到的更新令牌，必选项。
- scope：表示申请的授权范围，不可以超出上一次申请的范围，如果省略该参数，则表示与上一次一致。

使用同密码模式 但grant_type需设置为refresh_token，此时只需传入refresh_token





#####java.io.EOFException

java.io.EOFException: No content to map to Object due to end of input
在oauth_client_details表中的字段additional_information要么为null要么为json格式字符串，否则报错，虽不影响使用

