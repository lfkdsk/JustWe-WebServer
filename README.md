# JustWe-WebServer  

Android手机上的Http服务器，可以用于内网／外网的数据交换。  
![logo](https://github.com/lfkdsk/JustWeTools/blob/master/picture/justwe.png)  
ps: 这个项目是[JustWeEngine](https://github.com/lfkdsk/JustWeEngine)游戏框架中处理网络事件的一部分。

## 如何使用

    设置as Library或直接将代码拷出，如果使用JustWeEngine可以直接使用Gradle或者Maven构建。

## 快速入门
### 1.添加Service：
使用前请先添加Service到manifest文件：  
    
    <service android:name=".WebServer.WebServerService"/>

### 2.初始化\打开\关闭：  
		
	 private WebServer server;
	 server = new WebServer(MainActivity.this, new OnLogResult() {
        @Override
        public void OnResult(String log) {
                
        }

        @Override
        public void OnError(String error) {

        }
     });
     server.initWebService();
    
     
初始化的时候推荐实现一个OnLogResult用于接受log日志和错误。
当然也有其他的构造方法：

	public WebServer(Activity engine)；
	public WebServer(Activity engine, OnLogResult logResult, int webPort)； // 端口
	
初始化之后：

    server.startWebService();
    server.stopWebService();


使用该方法打开监听\关闭。  

### 3.添加路由：

        server.apply("/lfk", new OnWebStringResult() {
            @Override
            public String OnResult() {
                return "=======";
            }
        });

        server.apply("/main", new OnWebFileResult() {
            @Override
            public File returnFile() {
                return new File(WebServerDefault.WebServerFiles+"/"+"welcome.html");
            }
        });
        
 可以通过此种方法添加路由，并返回数据或者文件。
 需要表单提交的如Post可以使用如下接口，返回一个HashMap存储key和value。

 
        server.apply("/lfkdsk", new OnPostData() {
            @Override
            public String OnPostData(HashMap<String, String> hashMap) {
                String S = hashMap.get("LFKDSK");
                Logger.e(S);
                return "==";
            }
        });
        
### 4.获取／提交数据：

   向服务器提交数据，只需使用正常的get / post即可。 
   
##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件:lfk_dsk@hotmail.com  
* weibo: [@亦狂亦侠_亦温文](http://www.weibo.com/u/2443510260)  
* 博客:  [刘丰恺](http://www.cnblogs.com/lfk-dsk/)  

## License

    Copyright 2015 [刘丰恺](http://www.cnblogs.com/lfk-dsk/)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
  