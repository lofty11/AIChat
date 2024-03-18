**如果对变更文件目录结构、文件部署有问题的可以参照此文档**



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
或者
yarn dev（但是这个我没成功）
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


## Vuex

### 结构

![image-20240220115211987](./assets/image-20240220115211987.png)

### 设计思想

![image-20240220115326145](./assets/image-20240220115326145.png)

## git管理

![image-20240220152947982](./assets/image-20240220152947982.png)

## setting.js和permission.js

![image-20240220112303702](./assets/image-20240220112303702.png)

![image-20240220112435923](./assets/image-20240220112435923.png)

<img src="./assets/image-20240220112503140.png" alt="image-20240220112503140" style="zoom:67%;" />

## icon

### svg图标

![image-20240220115440261](./assets/image-20240220115440261.png)

svgicon+svg图标即可显示

![image-20240220115706500](./assets/image-20240220115706500.png)



### 设置svg-icon方法

![image-20240220120112825](./assets/image-20240220120112825.png)

只要把svg文件拖入svg目录下即可

![image-20240220150714811](./assets/image-20240220150714811.png)

## Utils文件夹

放公用文件函数

# 路径问题

## Src/router/index.js文件

修改为../layout

*后续因分开前后端，这里不需要修改，如果是@也无所谓

![image-20240220100252907](./assets/image-20240220100252907.png)

# 待完成的工作

## 项目logo

设计UI，插入左侧栏，logo图片放入assets/common下

## 页面获取数据

等后端封装完数据之后，就不用样例数据了，具体教程看[这个](https://www.bilibili.com/video/BV1Te411X7Wz/?p=42&spm_id_from=pageDriver&vd_source=211c68fed6443485e46395a3663fc0a6)

