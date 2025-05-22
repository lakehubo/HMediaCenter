# HMediaCenterServer

> **专业的本地多媒体资源中心，支持自动同步下载、管理、串流播放、智能设备联动（DLNA/Android 客户端）等功能**

---

## 🚀 项目简介

HMediaCenterServer 是一套基于 **Spring Boot 3.x**、**JWT** 鉴权、**MySQL** 存储的多媒体资源存储与播放服务端。  
支持自动下载同步影片至本地，API 管理多媒体元数据，并可为家庭电视、手机、智能设备提供高清流畅的串流播放服务。

---

## ✨ 主要特性

- **多媒体资源自动同步**：可定时自动下载、同步本地影片目录
- **多端兼容**：支持 DLNA、可对接自定义 Android/iOS 客户端
- **用户认证与权限管理**：JWT 鉴权、初始自动生成管理员账号
- **RESTful API + Swagger**：接口自带在线文档，方便调试与二次开发
- **高可用与易扩展**：核心模块解耦，便于二次开发与接入多种客户端

---

## 🛠 技术栈

- **后端框架**：Spring Boot 3.4.5
- **ORM**：Spring Data JPA (Hibernate)
- **数据库**：MySQL 8.x
- **安全认证**：Spring Security + JWT
- **文档/测试**：SpringDoc OpenAPI 3.x（Swagger UI）
- **工具库**：JJWT、Lombok

---

## 📂 目录结构

```text
HMediaCenterServer/
├── src/
│   ├── main/
│   │   ├── java/com/lake/hmediacenterserver/
│   │   │   ├── common/          # 通用工具/响应/异常定义
│   │   │   ├── config/          # 配置类（安全、Swagger等）
│   │   │   ├── controller/      # 控制器（REST API入口）
│   │   │   ├── dto/             # 数据传输对象（DTO）
│   │   │   ├── entity/          # 数据库实体
│   │   │   ├── repository/      # JPA数据访问
│   │   │   └── service/         # 业务逻辑层
│   │   └── resources/
│   │       ├── application.yml  # 核心配置
│   │       └── ...
├── pom.xml
└── README.md
