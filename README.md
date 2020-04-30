# httpclient-spring-boot-starter
HttpClient 的 SpringBoot的起步依赖
- `com.changhr.cbox.httpclient.autoconfig.*` starter的自动配置类
- `com.changhr.cbox.httpclient.properties.*` starter的自动配置属性类
- `com.changhr.cbox.httpclient.service.*` starter的服务类
- `com.changhr.cbox.httpclient.http.CustomTrustManager` 自定义信任证书管理器类
- `src/main/resources/META-INF/spring.factories` 注册配置文件

###可用配置
- 最大连接数
http.client.manager.maxTotal=100
- 并发数
http.client.manager.defaultMaxPerRoute=20
- 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout（单位：ms）
http.client.request.connectTimeout=1000
- 从连接池中获取到连接的最长时间（单位：ms），超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
http.client.request.connectionRequestTimeout=500
- 服务器返回数据(response)的时间，超过该时间抛出read timeout（单位：ms）
http.client.request.socketTimeout=10000
- 可用空闲连接过期时间，重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立（单位：ms）
http.client.manager.validateAfterInactivity=30000
- 重试次数
http.client.retry.retryCount=2
- 连接存活时间（单位：s）
http.client.manager.timeToLive=60
- 长连接保持时间（单位：s）
http.client.alive.keepAliveTime=30
- 代理的 host 地址
http.client.proxy.proxyHost=192.168.40.60
- 代理的端口号
http.client.proxy.proxyPort=8080

