# 项目简介

## 下载依赖

### 前端

先进入前端

```
cd front
```

在终端输入如下命令下载依赖

```
npm install
```

启动前端

```
npm run dev
```

### 后端

进入后端
```
cd back
```
在终端输入如下命令启动后端
```
mvn spring-boot:run
```

# 成员分工

徐迟：插件和函数管理前端

严梦婷：问答接口和应用服务后端

张梦芮：应用服务前端和问答应用前后端

廖锟鹏：插件和函数管理后端

# 已完成的工作

## 前端
1. 插件和函数部分页面实现效果
2. 应用服务API部分页面实现效果
3. 问答应用部分页面实现效果

### 插件

- 插件管理                

![image-20240523163036098](assets/image-20240523163036098.png)

- 新增插件

![image-20240523163144016](assets/image-20240523163144016.png)

-  编辑插件

![image-20240523163244020](assets/image-20240523163244020.png)

- 配置插件

![image-20240523163307024](assets/image-20240523163307024.png)

- 新增用户参数

![image-20240523163328317](assets/image-20240523163328317.png)

- 添加插件参数

![image-20240523163342824](assets/image-20240523163342824.png)

### 函数

- 根据名字查询

![image-20240523163407422](assets/image-20240523163407422.png)

- 新建函数

![image-20240523163416336](assets/image-20240523163416336.png)

- 编辑函数

![image-20240523163426525](assets/image-20240523163426525.png)

### 应用服务API
- 新建API
![](assets/新建API.png)
- 查看API
![](assets/查看API详情.png)
- 删除API
![](assets/删除API.png)
- 新建参数
![](assets/新建参数.png)
- 删除参数
![](assets/删除参数.png)
- 查询API
![](assets/查询.png)
### 问答应用
- 新建对话
![](assets/新建对话.png)
- 编辑对话名称
![](assets/编辑对话名称.png)
- 展示名片
![](assets/展示名片.png)
- 问答
![](assets/天气查询.png)
- 插件
![](assets/文生图.png)
### 后端
主要为问答接口实现效果
- 天气预报查询
![1](assets/1.png)
- 查询快递状态
![2](assets/2.png)
- 查询银行货币汇率
![3](assets/3.png)

插件和函数管理后端部分代码展示
- 插件模块controller
![image-20240524222509.png](assets/image-20240524222509.png)

![image-20240524222841.png](assets/image-20240524222841.png)

![image-20240524222845.png](assets/image-20240524222845.png)