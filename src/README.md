## 项目结构
<br>controller包下的对象为控制器，用于处理页面发出的请求。即当请求一个页面时，相应的控制器会接收这个请求并处理完毕后跳转到对应的页面
```
AuthorizeController->callabck方法：匹配处理"/callback"请求，用于获取对应的用户的github信息并保存到数据库中，并设置cookie，最后重定向到上一个页面
AuthorizeController->logout方法：匹配处理"/logout"请求，实现退出登录功能，主要过程为删除session域中的user对象以及删除浏览器对应的cookie
IndexController->index方法：匹配"/"请求，用于获取Cookie判断用户是否已登录，如果已登录跳过登录步骤显示个人信息看，否则出现登录选项
ProfileController->profile方法：匹配"/profile/{主要请求}"，即匹配符合"/profile/"前缀的请求，用于获取目标数据并显示
PublishController->publish方法：匹配"/publish"请求，用于获取用户发表问题的表单并存入数据库中
QuestionController->question方法：匹配"/question/{具体数字}"请求，用户获取指定的问题并显示
```
dto包下的对象为某些对象使用的数据的Bean，但是这些Bean不会持久化到数据库中，算是中间对象，dto中的对象用于构造进行数据传输的对象，
如我希望将user中的avatar属性和question中的description、id等属性展示到前端页面中，那么我可以在dto包中创建一个接收这些信息的对象
```
AccessTokenDTO：构造github链接请求获取AccessToken时使用的对象，用于构造请求体需要的Json参数
GithubUser：用于获取请求到的github中用户信息中业务需要的信息，如请求返回信息有a/b/c，我需要b/c就使用这个对象去接取我需要的数据
pageDTO：构造首页所需要的所有数据的传输对象
QuestionDTO：构造与问题列表有关的所有数据的传输对象
```
interceptors包下的对象包含所有与拦截器相关的操作
```
LoginInterceptor：实现拦截所有请求，并检查用户是否已登录，如果浏览器中存在对应cookie并且在数据库中找到对应的用户，则执行登录操作。
WebConfig：用于配置所有拦截器的类
```
mapper包下的对象包含所有与Mybatis有关的操作
```
UserMapper接口：有关操作user表中增删改查等操作的方法体
QuestionMapper接口：有关操作question表中增删改查等操作的方法体
```
model包下的对象是可能需要持久化到数据库中的Bean或用于保存从数据库中获取到的数据的Bean
```
User：映射数据库中user表的java bean
Question：映射数据库中question表的java bean
```
myUtil包下的所有对象处理因为业务相似或代码相似的代码块整合到一起，减少代码量,以及一些常用的步骤也整合到当前包下
```
ErrorMessage：整合在页面中显示相关错误信息的相似代码
LoginUtil：整合所有页面在检查登录过程中都会实现的相似代码
QuestionUtil：整合处理展示问题页面时所有相关类都会实现的相似代码
```
provider包下的对象用于实现与业务耦合度不大的操作，如A对象需要provider下的B对象，但是B对象除了支持A对象外，还支持C对象等。
```
GithubProvider->getAccessToken：获取AccessToken
GithubProvider->getUser：根据传入的AccessToken获取业务需要的github用户信息
```
services包下的所有对象用于实现service层的所有操作
```
QuestionService->questionSelect方法：根据传入的参数实现分页步骤，并调用questionUtil.getPageDTO方法获取pageDTO对象
QuestionService->selectUserQuestion方法：根据传入的参数实现分页步骤并获取当前登录的用户发表的问题，并调用questionUtil.getPageDTO获取pageDTO对象
QuestionService->getQuestionById方法：根据传入参数获取对应的问题，并返回QuestionDTO对象
```
## 新接触的知识点
```
OkHttp的 jar包，OKHttp用于请求数据
使用了BootStrap 框架作为前端框架：https://v3.bootcss.com/
使用了 H2 数据库作为保存网页数据的数据库
可以在 https://mvnrepository.com 中找到想要的jar包
flyway控制数据库同步问题：需要注意flyway中的sql文件命名规则：mvn flyway:migrate
lombok：https://www.projectlombok.org/，当前在model和dto下的类中使用了，需要在IDEA中安装lombok插件
热部署：spring中使用 Developer-Tools
mybatis不支持重载，也就是说在mybatis接口中不能出现两个名字相同但是变量不同的方法
```
## 根据视频中老师的讲解个人对service层、controller层、dto层拦截器等的主要工作的理解
<br>（1）controller层主要接收页面传入的表单数据并调用service层方法得到想要的数据结构<br>
（2）service层主要实现controller层不合适实现操作，controller主要的工作是接受请求的表单数据并调用service层的方法或Mapper中的方法得到
    目的数据，不需要关心这些数据是如何的来的，service主要接收controller传递过来的数据并处理成controller想要的数据<br>
（3）dto层主要用于构建页面需要的数据，如首页需要用户数据，问题列表，以及控制翻页的光标，这些操作需要的数据都整合在dto层的某一个类中<br>
（4）model层的对象都需要与数据库中的某个表的每个字段对应，用于实现java类到数据库的映射<br>
（5）拦截器主要是拦截指定的请求并做相关的操作，在拦截器方法 return true后该请求才会被controller层接收，如果return false则该请求不会被controller接收
## IDEA快捷键记录
<br>（1）在windows系统下 shift+Alt+Ins 可以快速的弹出构造方法选项和重写方法选项和toString选项等开发中常用的方法。<br>
（2）双击shift可以快速弹出搜索指定文件的搜索框<br>
（3）在被提示的方法或字段上进行鼠标悬浮后使用 Alt+Enter可以快速弹出解决当前问题的所有可选项，使用Shift+Alt+Enter可以快速选择默认
选项。
## 热部署和热加载
<br>在项目运行时无需重启服务器的情况下升级项目（即修改代码后无需关闭项目重启，一般只适合在开发环境中使用）。其中热加载是根据java的类
加载机制实现的，热加载是在项目运行过程中检测类文件的时间戳变化重新加载该类的过程。热部署的过程与热加载相似，但是热部署会直接重新
加载整个应用，即将项目在内存中的数据完全清除，所以热部署耗时比热加载长，常用的工具包有 devtools和 Jrebel等。
## 创建H2数据库注意事项
<br>创建数据库时选择连接模式为emdedded，无需设置user和password<br>
将url改为：jdbc:h2:~/myWebProject 表示创建一个myWebProject数据库，<br>
连接路径为相对于当前项目的路径
创建数据库后在H2控制台中执行
```
create user if not exists sa password '123'
alter user sa admin true
```
来设置H2的用户为 sa以及登录密码为123.
## 登录过程和退出登录过程的实现
```
因为当前项目出现刷新不同的页面时需要重新登录的问题，所以使用spring boot 的拦截器去解决需要重复登录登录的问题。其中拦截器只拦截action请求，
即拦截前端对后端的方法请求。拦截器主要使用Java反射机制实现，可以访问action请求中的上下文（即action请求中的数据），可以在项目运行期间
多次调用
```
### 登录过程
<br>（1）在provider.GithubProvider对象中实现一个getAccessToken方法请求 github的“https://github.com/login/oauth/access_token”链接获取
AccessToken，传入参数为dto.AccessTokenDTO，是一个请求链接时需要的参数对象，需要使用Json包转换为Json格式的对象才可以使用OkHttp包
发出post请求
<br>
（2）在provider.GithubProvider对象中实现一个getUser方法请求github的“https://api.github.com/user?access_token=”请求用户数据对象，
其中传入参数为上一步得到的AccessToken，返回对象是我们自定义的需要的数据结构dto.GithubUser
<br>
（3）根据得到的数据设置一个Cookie值到浏览器中，在controller中的AuthorizeController中实现，使用HttpServletResponse对象中的setCookie
方法，并将用户对象及其Cookie保存到数据库中
<br>
（4）使用HttpServletResquest对象请求获Cookie数组，在controller.IndexController中的index方法中实现，并根据业务要求获取到对应的Cookie
对象，然后查询数据库是否存在相应的Cookie，如果存在则从数据库中获取用户对象并改变登录状态为在线状态，否则为登录状态。
### 退出登录过程
（1）将session域中的user对象删除<br>
（2）将浏览器中的对应的cookie删除<br>
（3）刷新页面
## 分页过程实现（可以使用pageHelper实现分页功能，后期学习）
<br>使用sql 语句：select * from question limit 0,5; 即获取 question表中的数据从游标 0开始获取，获取5条数据。<br>
分页过程：游标计算公式是
```
p = 5 * (i-1) 
```
其中 i 为我们需要的第 i 页展示的数据，从 1~n自增1。<br>
计算分页数公式：
```
total = count(question)/ 5
如果 count(question)%5 != 0
total = total + 1
```
即如果question表中数据除以页面最多可以显示的数据得到的余数不为零，总页面数需要向上取1
