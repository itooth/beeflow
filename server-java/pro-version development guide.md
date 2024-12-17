# 环境部署

## 下载代码

请从github私有仓库下载代码，代码获取联系各自购买的负责人。

## 安装环境

### 前端

前端运行需要nodejs环境，版本 \> 14

注意：如果你的 node.js <= 16，那么需要修改下 package.json scripts 命令

```json
"BUILD":"SETNODEOPTONS=--OPENSS1-LEGACY-PROVIDER&&VUE"
"SETNODEOPTIONS=--OPENSS1-1EGACY-PROVIDER&&VUE-C."
```

NODE版本16及以下需要去除此配置

```json
&VUE-C1I-SERVICESERVE"
```

1. 执行 `npm install` 安装依赖
   - vue2 执行 `npm run serve` 即可启动
   - vue3 执行 `npm run dev` 即可启动

### 修改后台接口地址

- vue2 在 `util/Injection.js` 内
- vue3 在 `api/request.js` 内

接口地址为一个三元表达式，根据环境是开发还是生产环境来自动赋值接口地址

### 修改高德地图key

`main.js` 中配置了高德地图组件，用来选位置的，里面的key是我默认的，大家记得务必修改，不要直接上生产了！！

👉前往 [高德开放平台](https://lbs.amap.com/) 自行注册

### 打包

执行 `npm run build` 即可打包，生成的文件在 `dist` 目录，将其部署到nginx下即可

## 后端

默认项目运行需要以下环境，请自行安装

- mysql：版本 5.7 +
- jdk：8+
- maven：3.2+

建议使用开发工具IDEA打开本项目，其他工具自行摸索搭建java开发环境

### 修改配置

本地启动记得先切换 `application.yml` 里面的配置文件为 `dev`，否则日志将不输出到控制台

项目里面的配置文件中数据库配置需要修改，邮件的配置是给触发器节点发邮件用的，如果不需要也不需要配置。

### 初始化数据库

项目提供建表脚本，在 `sql` 目录下，脚本基于mysql创建，其他数据库需要自行修改

- FLOWABLEINITDX.SGL
- SAUPDATE.SQL
- FLOWABLEINIT.SGL
- WFLOWINIT.SGL

项目表分为两部分，一个是流程引擎自带的表，一个是wflow自定义表，项目运行需要手动导入 `wflow_init.sql`，流程引擎表启动会自己创建。

### 打包

在IDEA右侧面板执行 `maven install` 然后执行 `maven package`，在项目target目录下将会生成一个可执行jar包

## 部署

使用 `java -jar 项目jar名` 即可启动项目

## uniapp 移动端

uniapp移动端需要下载Hbuilder开发工具，请自行下载安装

### 配置项目

打开 `manifest.json` web配置，设置腾讯地图key，请自行前往 [腾讯地图开放平台](https://lbs.qq.com/map/) 注册，不要使用默认测试的key！！！

### 注意事项

#### 钉钉小程序

钉钉小程序编译后会样式错乱，是开发工具的bug，如果你的 Hbuilder版本 <= 3.99 请按如下方式修改.

uniapp社区对应问题：[【报Bug】钉钉小程序不支持styleIsolation](https://ask.dcloud.net.cn/question/172186)

评论区第一条是解决方案

---

# json 转 bpmn

## Introduction

The data format of wflow process designer is a nested structure JSON. The backend uses a flow engine based on bpmn2.0, flowable. Since the data formats are different, we need to convert the JSON from the process designer into a format acceptable by flowable. The implementation class for this converter is `WFlowToBpmnCreator`. For detailed content, refer to the project implementation.

## Implementation Principle

Let's first look at a sample `bpmn.xml` data:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" id="Definitions_Process_1686014563526" targetNamespace="http://bpmn.io/schema/bpmn">
    <bpmn:process id="Process_1686014563526" name="业务流程_1686014563526" isExecutable="true">
        <bpmn:startEvent id="Event_1jquky6" name="开始">
            <bpmn:outgoing>Flow_1sjxic8</bpmn:outgoing>
        </bpmn:startEvent>
        <bpmn:userTask id="Activity_1eko0k5" name="审批人1">
            <bpmn:incoming>Flow_1sjxic8</bpmn:incoming>
            <bpmn:outgoing>Flow_0z7rg7c</bpmn:outgoing>
        </bpmn:userTask>
        <bpmn:userTask id="Activity_1gkys96" name="审批人2">
            <bpmn:incoming>Flow_0z7rg7c</bpmn:incoming>
            <bpmn:outgoing>Flow_1yoq9zl</bpmn:outgoing>
        </bpmn:userTask>
        <bpmn:endEvent id="Event_1mun3j2" name="流程结束">
            <bpmn:incoming>Flow_1yoq9zl</bpmn:incoming>
        </bpmn:endEvent>
        <bpmn:sequenceFlow id="Flow_1sjxic8" sourceRef="Event_1jquky6" targetRef="Activity_1eko0k5"/>
        <bpmn:sequenceFlow id="Flow_0z7rg7c" sourceRef="Activity_1eko0k5" targetRef="Activity_1gkys96"/>
        <bpmn:sequenceFlow id="Flow_1yoq9zl" sourceRef="Activity_1gkys96" targetRef="Event_1mun3j2"/>
    </bpmn:process>
    <bpmndi:BPMNDiagram id="BPMNDiagram_1">
        流程节点布局
    </bpmndi:BPMNDiagram>
</bpmn:definitions>
```

The corresponding flowchart for the above XML and the wflow process chart is shown below.

![Image: Description of the image](https://cdn.nlark.com/yuque/0/2024/png/2819278/1712039069421-51a12acf-fe6d-4586-a8b4-384159a6e673.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_d2Zsb3ctcHJv5bel5L2c5rWB%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp%2Fresize%2Cw_254%2Climit_0)

The corresponding JSON for wflow process is as follows:

```json
{
    "id": "root",
    "desc": "任何人",
    "name": "发起人",
    "type": "ROOT",
    "props": {},
    "children": {
        "id": "node_151977794862",
        "parentId": "root",
        "props": {},
        "type": "APPROVAL",
        "name": "审批人1",
        "children": {
            "id": "node_152038799603",
            "parentId": "node_151977794862",
            "props": {},
            "type": "APPROVAL",
            "name": "审批人2",
            "children": {}
        }
    },
    "parentId": null
}
```

To establish a relationship between these, the goal is to construct XML from JSON to be consumed by the flowable process engine, which is implemented in Java.

## Example Tutorial

By introducing flowable's Maven dependencies in both frontend and backend, and setting up the following names in IDEA, we can begin the process.

![Image: Description of the image](https://cdn.nlark.com/yuque/0/2024/png/2819278/1712039069436-c38ea140-a79b-4b11-8329-fad737ab7690.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_50%2Ctext_d2Zsb3ctcHJv5bel5L2c5rWB%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp%2Fresize%2Cw_1746%2Climit_0)

The classes under `org.flowable.bpmn.model` correspond to the nodes in the BPMN, such as:
- StartEvent: Start event
- EndEvent: End event
- SequenceFlow: Flow connection line
- UserTask: User task node

Constructing a BPMN model involves creating these nodes and connecting them as well. The JSON structure is nested, so a recursive approach is needed to iterate through all nodes.

## Advanced

The process of creating a BPMN flowchart XML is demonstrated by constructing a basic example of a serial, no-branching process. The same principles apply to more complex processes involving branching, with parallel gateways and conditional gateways.

## Debugging Tips

During debugging, output the generated flowchart XML to a file and import it into a BPMN.js designer to visualize the flowchart. Verify if all necessary elements are present.

---

# 表单设计器实现原理

首先，wflow的表单是通过json动态渲染出来的，表单设计器生成的也是json，并非是生成代码，该json存储在 wflow_model_historys 和 wflow_models表的 form_items字段

表单渲染实现原理来源于vue的动态组件 <components is="表单组件名称"></components>

利用此特性，我们可以通过一个字符串，动态显示成一个vue组件

例如：有如下json

示例表单渲染json

```javascript
[
  {
    id:"field454894798",
    name:"TextInput", //表单组件名称
    title:"文本输入框",
    props:{
      //表单组件的自定义props配置项
    }
  },
  {
    id:"field45888888",
    name:"NumberInput", //表单组件名称
    title:"数字输入框",
    props:{
      //表单组件的自定义props配置项
    }
  }
]
```

那么我们可以使用定义如下表单组件，对其进行动态渲染

FormRender.vue 简单示例

```vue
<template>
  <el-form :model="formData">
    <el-form-item :label="cp.title" :prop="cp.id" v-for="cp in formJson" :key="cp.id">
      <components v-model="formData[cp.id]" :is="cp.name" v-bind="cp.props"/>
    </el-form-item>
  </el-form>
</template>

<script>
//这两个组件自己定义就好了
import TextInput from '../TextInput.vue'
import NumberInput from "./NumberInput.vue";

export default {
  name: "FormRender",
  props: {},
  data() {
    return {
      formData: {},
      formJson: [] //表单json
    }
  }
}
</script>
```

这样子，表单就渲染出来了，并且可以绑定表单值。理解原理就知道表单怎么搞了，复杂的组件自己再尝试扩充.

---

# Project Development Guide

## Introduction
Welcome to the project development guide! This guide will walk you through the essential steps to contribute to our open source project effectively.

## Setting Up Your Environment
To get started, make sure you have the following tools installed:
- Git
- IDE of your choice
- Browser for testing

## Cloning the Repository
Clone the project repository by running the following command:
```bash
git clone [repository_url]
```

## Making Changes
1. Create a new branch for your work:
```bash
git checkout -b new_branch_name
```
2. Make your changes and commit them:
```bash
git add .
git commit -m "Your commit message"
```
3. Push your changes to the remote repository:
```bash
git push origin new_branch_name
```

## Submitting a Pull Request
1. Go to the project repository on GitHub.
2. Click on "New Pull Request."
3. Select your branch and provide a brief description of your changes.
4. Submit the pull request for review.

## Continuous Integration
We use CI/CD pipelines to automate testing and deployment. Ensure your code passes all tests before submitting a pull request.

## Conclusion
Thank you for following our project development guide. Your contributions are valuable to the community!

---

# 人员分配

flowable有固定的人员分配规则以及动态的分配规则

为了实现灵活的人员分配机制，wflow使用表达式进行人员分配，当flowable驱动流程到达人员相关节点时，就会调用wflow预设的人员分配函数，见 ProcessTaskService.getApprovalUsers()，该函数返回一个userId集合，flowable将会给这个集合里面的人下派任务

举个栗子，例如设置审批人的配置

- 选择审批对象
- 多级部门主管
- 系统(自动拒绝)
- 发起人自己
- 表单内联系人
- 由其他节点指定
- 表单权限
- 操作权限
- 部门主管
- 表单内部门
- 设置审批人
- 系统角色
- 监听器
- 发起人自选
- 指定人员
- 指定部门
- 审批人

![image.png](https://cdn.nlark.com/yuque/0/2024/png/2819278/1713234468182-43d03c5c-b2f6-49d3-8a84-0c91976d24dd.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_d2Zsb3ctcHJv5bel5L2c5rWB%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

这些设置项都是为了去构建一个取人的规则，通过这些规则，我们在 ProcessTaskService.getApprovalUsers() 函数内进行解析，至于怎么实现，完全就是代码上面去处理了，前端构建好规则，后端解析规则，返回满足这个规则的人员，就是这么简单

系统内置规则不够用的话，大家还可以自由去扩充，可以添加设置项.

---

# 流程条件判断

wflow-pro的条件节点支持多种流程条件判断规则

## 条件设置模式

- 逻辑表达式
- 简单模式
- 网络请求
- JS解析

![image.png](https://cdn.nlark.com/yuque/0/2024/png/2819278/1721890900296-0f8291d3-0d99-447b-84e2-a7a1797a1521.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_d2Zsb3ctcHJv5bel5L2c5rWB%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

通过表达式调用java代码，来实现流程条件的解析，比UEL表达式更灵活强大。

### 原理

在流程发布的时候，WFlowToBpmnCreator中构造了条件解析表达式，nodeId为条件节点的ID

构造条件解析表达式

```java
private String conditionExplainCreator(String nodeId) {
    return "${uelTools.conditionCompare('" + nodeId + "', execution)}";
}
```

也就是说，当流程走到这个条件网关的时候，流程引擎将会遍历每个条件分支，调用 `uelTools.conditionCompare(nodeId, execution)` 函数，去判断这个条件分支满不满足条件。

如果这个函数返回 `true`，就代表这个条件满足，返回 `false` 代表不满足

- 发起人属于X科技有限公司
- 其他条件进入此分支
- 金额输入框 > 20
- 条件哈哈
- 所有人
- 默认条件
- 条件1
- 流程结束
- 添加条件

优先级:
1. 3
2. 1
3. 2

- 发起人

![image.png](https://cdn.nlark.com/yuque/0/2024/png/2819278/1721891315995-8ababa86-c4af-4ef0-a6ab-13249e7f873a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_38%2Ctext_d2Zsb3ctcHJv5bel5L2c5rWB%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

## 调试技巧，排错

如果大家发现发起一个流程，流程走到条件分支那时候，流程出现意外情况：

- 抛出异常：No outging sequence flow of the exclusive gateway 。。。。
- 没有走预计的那个分支

### 抛异常

抛这个异常是代表流程走到条件分支时候，发现所有的条件都不满足，没法继续往下执行了，先人肉判断下，如果发现按当前情况就应该会有满足的条件，debug下 `uelTools.conditionCompare(nodeId, execution)` 函数，看它是不是返回了 `false`，返回 `ture` 是满足，返回 `false` 是不满足。

### 没有走预计的路线

本来计划会走条件支路A的，发起实际走了支路B，这时候代表执行A支路节点条件 `uelTools.conditionCompare(nodeId, execution)` 函数时候返回了 `false`，B支路返回了 `true`，所以大家在这个函数打断点，看下A为何没返回 `true` 就行了

If you get gains, please give a like

[旅人](/lvren-ybpix)

08-13 03:35

698

IP region江西

Report

---

# 数据库说明

## wflow数据库清单

项目内包含两组数据库，一个是 wflow\_ 开头的，是wflow自带的数据库，一个是 act\_和 flw\_ 开头的，flowable 依赖的数据库， wflow\_  的数据库需要手动导入进行创建，flowable 默认配置会在项目启动时候自动建表。

每个表的字段在表内都有注释，可以自行查阅

- 表名（用户/组织架构相关表）：作用
  - wflow\_users: 用户表
  - wflow\_departments: 部门表
  - wflow\_roles: 角色表
  - wflow\_user\_departments: 用户部门关系表
  - wflow\_user\_roles: 用户角色关系表

- 表名（工作流相关表）：作用
  - wflow\_model\_groups: 表单流程模型分组表
  - wflow\_models: 表单流程模型表，流程最新的版本
  - wflow\_model\_historys: 表单流程模型历史表，每个版本都在这
  - wflow\_model\_perms: 表单流程用户可见权限表，可发起权限
  - wflow\_form\_data: 流程表单数据表
  - wflow\_form\_record: 表单修改历史记录表
  - wflow\_user\_agents: 用户代理设置表
  - wflow\_cc\_tasks: 抄送任务表
  - wflow\_notifys: 通知消息表

## Flowable表清单

数据库表命名规则

- ACT\_RE\_：其中“RE”表示repository（存储）的意思，是RepositoryService 接口操作的表。带此前缀的表包含的是静态信息，如，流程定义，流程的资源（图片，规则等）.

- ACT\_RU\_：其中“RU”表示runtime（运行时）的意思，是RuntimeService接口操作的表。存储着流程变量，用户任务，变量，职责（job）等运行时的数据. flowable 只存储实例执行期间的运行时数据，当流程实例结束时，将删除这些记录. 这就保证了这些运行时的表小且快.

- ACT\_ID\_：其中“ID”表示 identity (组织机构). 这些表包含标识的信息，如用户，用户组，等等，wflow没有使用它ID相关表，用的是自己的表.

- ACT\_HI\_：其中 “HI”表示 history（历史记录），是HistoryService接口操作的表. 这些表包含着历史的相关数据，如结束的流程实例，变量，任务，等等.

- ACT\_GE\_: 普通数据，各种情况都使用的数据.

表名（数据表）：作用，act的都是沿用activiti的表
- act\_ge\_bytearray: 数据存储，表单变量如果是对象也会被序列化存在这里，通用的流程定义和流程资源（二进制格式）
- act\_ge\_property: 系统相关属性
- 流程历史表: 流程历史数据，包含结束和正在运行的流程数据
- act\_hi\_actinst: 历史的流程实例
- act\_hi\_attachment: 历史的流程附件
- act\_hi\_comment: 历史的说明性信息
- act\_hi\_detail: 历史的流程运行中的细节信息
- act\_hi\_entitylink: 存储有关实例的父子关系的信息
- act\_hi\_identitylink: 历史的流程运行过程中用户关系
- act\_hi\_procinst: 历史流程实例
- act\_hi\_taskinst: 历史的任务实例
- act\_hi\_tsk\_log: 每一次执行可能会带上数据，存在这里
- act\_hi\_varinst: 历史的流程运行中的变量信息
- 流程模型及定义表: 流程信息
- act\_procdef\_info: 当通过缓存保存的流程信息
- act\_re\_deployment: 部署单元信息
- act\_re\_model: 模型信息
- act\_re\_procdef: 已部署的流程定义
- 流程运行实例表
  - act\_ru\_actinst: 运行时流程实例表
  - act\_ru\_deadletter\_job: 正在运行的任务表
  - act\_ru\_entitylink: 存储有关实例的父子关系的信息
  - act\_ru\_event\_subscr: 运行时事件
  - act\_ru\_execution: 运行时流程执行实例
  - act\_ru\_external\_job: ？
  - act\_ru\_history\_job: 历史作业表
  - act\_ru\_identitylink: 任务参与者数据表。主要存储当前节点参与者的信息
  - act\_ru\_job: 运行时作业表
  - act\_ru\_suspended\_job: 暂停作业表
  - act\_ru\_task: 运行时任务表
  - act\_ru\_timer\_job: 定时作业表
  - act\_ru\_variable: 运行时变量表
- flowable专有扩展表
  - flw\_channel\_definition: 泳池管道定义表
  - flw\_ev\_databasechangelog: Liquibase执行的记录
  - flw\_ev\_databasechangeloglock: Liquibase执行锁
  - flw\_event\_definition: 已部署事件定义的元数据
  - flw\_event\_deployment: 已部署事件部署元数据
  - flw\_event\_resource: 事件所需资源
  - flw\_ru\_batch: ？

## 表字段说明

[![表字段说明](https://mdn.alipayobjects.com/huamei_0prmtq/afts/img/A*khrYRYi6VN0AAAAAAAAAAAAADvuFAQ/original)](/dashboard)

wflow-pro

Outline

- [wflow数据库清单](#a2add469)
- [Flowable表清单](#5cb2a934)
- [数据库表命名规则](#68971755)
- [表字段说明](#dQSb7)

---

# 项目集成

## 集成说明

wflow是一个独立的工作流系统，为大家快速实现工作流功能所开发，它不负责组织架构、用户、角色、菜单的管理，市面上已经有很多优秀的后台管理系统了，所以wflow不去做这块的重复性工作，大家可以选择合适的后台管理系统做集成，例如 ruoyi、jeecg、pigx

wflow 可以用代码形式和独立部署形式进行集成，集成的话主要是实现wflow定义的组织架构查询接口及用户登录信息获取接口。

### OrgRepositoryService

读取组织架构用户

UserUtil.getLoginUserId()

从请求读取的登录人userId

wflow业务层

当系统需要获取用户信息及组织架构信息时，就会调用下面2个口子拿数据

后台管理系统

数据库（共享）

读取组织架构、用户、角色信息​

管理组织架构、用户、角色信息​

集成方式

- 代码集成：将本项目前后端代码与当前项目进行融合

- 独立部署集成：将本项目前后端进行独立部署，通过接口交互

- 异构系统集成：适用于非java的其他后台实现

## 项目结构

### 前端

#### 前端目录结构

```
├─api --与后端交互的接口
├─assets --静态资源
│ ├─iconfont
│ ├─image
│ └─theme
│ └─fonts
├─components --公共组件
│ └─common
├─router --路由
├─store --Vuex
├─utils --辅助js
└─views --页面组件
  ├─admin --表单流程设计器
  │ └─layout
  │ ├─form --表单
  │ ├─print --打印模板
  │ └─process --流程
  ├─common --一些公共组件
  │ ├─form --表单
  │ │ ├─components --表单组件库的组件
  │ └─process --流程
  │ ├─config --流程节点组件的配置面板
  │ └─nodes --流程节点组件
  ├─process --流程步骤进度渲染组件
  │ └─node
  └─workspace --工作区
  ├─approval --审批处理相关组件
  ├─my --个人中心
  └─oa --工作区的几个页面，我发起的、关于我的、带我处理。。。。
```

### 后端

后端为springboot + mybatis标准项目格式

#### 后端目录结构

```
├─bean 辅助业务实体类及dao层相关
│ ├─do\_
│ ├─entity
│ └─vo
├─config -项目配置类相关
├─controller -普通crud接口
├─exception -自定义异常
├─mapper -mapper接口
├─service
│ └─impl
├─utils -工具类
└─workflow -流程处理相关业务
  ├─bean
  │ ├─dto
  │ ├─process -wflow流程模型实体
  │ │ ├─enums
  │ │ ├─form
  │ └─props
  └─vo
  ├─catch\_
  ├─config -配置相关
  │ ├─callActivity
  │ ├─custom -自定义配置
  └─listener -监听器配置
  └─event
├─controller --流程核心业务服务接口
├─execute -动作执行器
├─extension -扩展
│ └─cmd
├─service -流程核心业务服务
│ └─impl
├─task -任务相关类
└─utils -工具类
```

If you get gains, please give a like

[旅人](/lvren-ybpix)

11-25 02:56

1986

IP region江西

Report

Markup comments (0)

Sign Up / Login Yuque to comment

[![](https://cdn.nlark.com/yuque/0/2024/png/2819278/1710810486051-avatar/6ed8d9fb-6745-48e2-b334-e8aa5503c947.png?x-oss-process=image%2Fresize%2Cm_fill%2Cw_32%2Ch_32%2Fformat%2Cpng)](/dashboard)

wflow-pro

---

# 前端集成

技术栈一致，推荐使用代码方式集成，将wflow前端代码合并到主体项目内。

## 合并静态资源

将wflow的 `public` 和 `assets` 目录下的静态资源文件转移到目标项目。

## 合并主体代码

1. 在大家项目的 `views` 目录下新建目录 `wflow`，将wflow项目的 `views` 目录下源码放入该目录下。
2. 把 wflow 的 `components/common` 目录下公共组件放到各位的项目对应目录下。

**注意：** 由于组件路径有变化，需要根据后续运行的组件报错信息去修改组件路径及静态资源的路径！！！

## 合并main.js

将wflow项目和目标项目的 `main.js` 中代码进行合并，主要是合并 `import` 导入的依赖还有注册全局组件，注意不要遗漏。

高德地图key记得替换成自己的，不要用公共的，默认key额度有限！

## 合并package.json依赖

合并依赖，注意下依赖版本，wflow和大家主项目依赖哪个新可以用哪个，有问题再换。

## 合并路由

把wflow的 `router/index.js` 里面配置的路由也合并到主项目，如果主项目是动态路由，这块就可以在后台管理系统里面去配置动态菜单。

## 合并store

### vuex

把 `store/index.js` 里面的内容加到项目里面就行，如果主项目的状态管理是分模块的，那么就放进新建的 `wflow.js` 模块里面。

### pina

和vuex的一样，新建 `module wflow.js`，参考如下：

```javascript
const useWflowStore = defineStore(
  'wflow', {
    state: () => ({
      nodeMap: new Map(),
      isEdit: null,
      loginUser: JSON.parse(localStorage.getItem('loginUser') || '{}'),
      selectedNode: {},
      selectFormItem: null,
      design: {},
    }),
  }
)

export default useWflowStore
```

为了兼容之前 Vuex 的写法 `this.$store.state.xxxx`，在 `main.js` 将 `useWflowStore` 注册到全局。

### 注册store到全局

```javascript
import useWflowStore from '@/store/modules/wflow'

const app = createApp(App)

window.$vueApp = app
window.$vCtx = app.config.globalProperties

import('./utils/Injection')

// 注册到全局后可以使用 this.$wflow.xxx 访问变量了
window.$vCtx.$wflow = useWflowStore()
```

然后全局替换 `this.$store.state.` 为 `this.$wflow.` 即可。

## 合并配置

### vue2版本

```javascript
const MonacoEditorPlugin = require('monaco-editor-webpack-plugin')

module.exports = {
  // 其他配置项......
  configureWebpack: {
    plugins: [
      new MonacoEditorPlugin({ languages: ['javascript', 'html', 'css', 'json'] })
    ]
  }
}
```

### vue3版本

```javascript
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
  // 其他配置项......
  plugins: [
    vue(),
    vueJsx()
  ],
  resolve: {
    alias: { // 配置路径别名
      '@': resolve('src')
    },
  },
  css: {
    preprocessorOptions: { // css预处理器
      less: {
        // 引入全局的Less库
        additionalData: '@import "./src/assets/theme.less";'
      },
    },
  },
  optimizeDeps: {
    include: [
      `monaco-editor/esm/vs/language/json/json.worker`,
      `monaco-editor/esm/vs/language/css/css.worker`,
      `monaco-editor/esm/vs/language/html/html.worker`,
      `monaco-editor/esm/vs/language/typescript/ts.worker`,
      `monaco-editor/esm/vs/editor/editor.worker`
    ],
  },
  esbuild: {
    jsxFactory: 'h',
    jsxFragment: 'Fragment',
    jsxInject: "import { h } from 'vue';"
  }
})
```

## 设置登录用户信息缓存

前端很多地方是要取当前登录用户的信息，这个信息默认会从store的 `loginUser` 取，所以我们集成的时候，在登录成功后需要对这个值进行设置。

**注意：** id默认是字符串类型，系统会做 `===` 判断，数字 `!=` 字符串。

### 登录用户的信息结构

```javascript
{
  id: '用户id', // 默认是字符串类型！！！
  name: '用户显示的名称',
  avatar: '头像图片，可为空',
  type: 'user' // 固定值
}
```

## 路由页面

如果是使用动态路由的话，路由就不用配置在代码中了，直接配置到后台管理系统的菜单管理中，以下是需要配置路由的页面：

- 审批列表：`@/views/wflow/workspace/oa/FromsApp`
- 待我处理：`@/views/wflow/workspace/oa/UnFinished`
- 已处理的：`@/views/wflow/workspace/oa/Finished`
- 我发起的：`@/views/wflow/workspace/oa/MySubmit`
- 关于我的：`@/views/wflow/workspace/oa/CcMe`
- 数据管理：`@/views/wflow/admin/ProcessInstanceManage`
- 流程管理：`@/views/wflow/admin/FormsPanel`
- 流程设计：`@/views/wflow/admin/FormProcessDesign`

🎈 **注意：** 流程设计这个路由不要显示到菜单里面！！！

## 样式冲突

引入wflow后可能由于目标项目存在一些全局样式类名与wflow内的类名重复，导致wflow系统组件样式被污染变化，这种情况需要手动F12打开开发者工具进行定位，手动修改复原wflow的样式。

### 修改技巧

先在浏览器内的开发者工具中进行样式修改，然后复原样式后根据修改的 class 类名全局搜索对应 `.vue` 组件 `<style>` 中的类名，修改对应样式。

If you get gains, please give a like.

[旅人](https://yuque.com/lvren-ybpix)

04-08 01:54

2038

IP region 江西

---

# 后端集成

后端集成主要是组织架构及登录这块进行集成，wflow需要读取主项目的组织架构信息，主要是实现2处。

## 1. 后端代码合并

代码合并分3种情况：微服务项目、单体单模块项目、单体多模块项目。

### 微服务

新建微服务模块wflow，命名根据系统风格要求来加上 -wflow 后缀即可，将wflow相关配置及代码配置到该模块内即可。

### 单体单模块项目

在主类同级中新建包 wflow，将wflow代码全部复制过去即可。

### 单体多模块项目

根据目标要求新建新maven模块，如上和微服务差不多，注意pom依赖项。

## 2. 合并pom依赖

这个没啥说的了，把 wflow的 pom 配置依赖都合并过去，注意和目标项目的依赖冲突，主要是mybatis依赖冲突，在mybatis-plus中已经包含了mybatis的依赖，无需额外引入，flowable-spring-stater包也包含了mybatis的依赖，注意需要排除。

```xml
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter-actuator</artifactId>
    <version>${flowable.version}</version>
    <exclusions>
        <exclusion>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

如果你的项目还存在例如pagehelper之类的包，注意它可能也包含了mybatis依赖，也需要排除掉，否则启动项目会出现找不到类的错误。

## 3. 合并application配置

以下几个配置项需要添加进去：

```yaml
spring:
  # 邮件发送配置，如果不需要发邮件功能这里可以不加，同时代码里面也要去除邮件功能
  mail:
    host: smtp.qq.com
    username: smartiots@qq.com
    password: fnrruelrccqaeaefx0
    protocol: smtps
    default-encoding: UTF-8
    properties:
      default-encoding: utf-8
    smtp:
      port: 465
      auth: true
      starttls:
        enable: true
        required: true
  management:
    health:
      # 关闭启动时邮件配置检查
      mail:
        enabled: false
  flowable:
    async-executor-activate: true
    # 关闭一些不需要的功能服务
    rest-api-enabled: false
    idm:
      enabled: false
    cmmn:
      enabled: false
    dmn:
      enabled: false
    form:
      enabled: false
    app:
      enabled: false
  wflow:
    file:
      max-size: 20 # 最大文件上传大小，MB
```

## 4. 启动项目，逐步排查错误

我们按上述步骤合并好代码后可能出现以下错误，需要逐步去排除。

### 包/类路径报错

由于代码是直接复制过来的，和当前项目包和类路径不一致，需要手动批量去引入，根据开发工具的错误提示去挨个修改即可，可以使用批量替换。

### 类冲突

wflow存在一些config配置类，例如MyBatisPlusConfig、AsyncTaskTheadPoolConfig等，去除不需要的配置类，这个AsyncTaskTheadPoolConfig是用来提供线程处理异步任务的，如果大家各自项目内已经有了线程池配置，那么就不需要这个，但是要在目标项目的线程池配置里面加两个静态属性，如下：

```java
@Configuration
public class ThreadPoolConfig {
    public static ThreadPoolTaskExecutor executor;
    public static ThreadPoolTaskScheduler taskScheduler;
}
```

在spring初始化这个类进行自动配置时，为上述两个属性赋值，taskScheduler这个如果本身项目没有的话可以这样配置：

```java
@Bean
@Qualifier(value="applicationTaskExecutor") // 这个注解下，防止线程池配置冲突
public ThreadPoolTaskScheduler threadPoolTaskScheduler(ThreadPoolTaskExecutor executor) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler = threadPoolTaskScheduler;
    threadPoolTaskScheduler.setThreadFactory(executor);
    return threadPoolTaskScheduler;
}
```

## 5. 集成组织架构及用户角色

wflow自带了组织架构表，参见[数据库说明文档](db.md)。

那几张组织架构表如果是集成的话，一般目标系统都自带了，就不需要了，可以去掉它们，我们接下来就是要把查询这块全部换成目标系统的。

集成组织架构及用户角色等信息只需要实现wflow定义好的一个接口`com.wflow.service.OrgRepositoryService`，wflow把需要读取组织架构相关信息的地方都封装到了这个接口中，用户就可以根据自己的系统去实现对应的查询即可，返回wflow指定的实体类，这样就能适应各类系统了，甚至是从接口查组织架构数据。

事实上，wflow自带的组织架构相关表和绝大多数RBAC系统设计的结构大致一样，因此可以直接根据wflow默认实现`DefaultOrgRepositoryServiceImpl`稍作修改即可兼容到现有系统。

### 示例

下面展示一个用户查询的集成，对应Mapper为`WflowUserMapper`。

```java
public interface WflowUsersMapper extends BaseMapper<WflowUsers> {
    /**
     * 查询该部门下的所有用户
     * @param deptId 部门ID
     * @return 用户列表 type为固定值user
     */
    @Select("SELECT ou.user_id id, ou.user_name `name`, 'user' AS 'type', ou.avatar FROM wflow_user_departments oud LEFT JOIN wflow_users ou ON ou.user_id = oud.user_id WHERE oud.dept_id = #{deptId}")
    List<OrgTreeVo> selectUsersByDept(@Param("deptId") String deptId);
}
```

以集成到ruoyi-vue为例，那么只需要把`BaseMapper<WflowUsers>`换成`BaseMapper<SysUser>`，然后由于若依的用户只能在一个部门下面，那么改造如下：

```java
public interface WflowUsersMapper extends BaseMapper<SysUser> {
    /**
     * 查询该部门下的所有用户
     * @param deptId 部门ID
     * @return 用户列表 type为固定值user
     */
    @Select("SELECT user_id id, nick_name `name`, 'user' AS 'type', avatar " +
            "FROM sys_user WHERE del_flag = 0 AND dept_id = #{deptId}")
    List<OrgTreeVo> selectUsersByDept(@Param("deptId") String deptId);
}
```

然后对应的`WflowUsers`都需要换成`SysUser`，其他需要修改的项为，部门、角色，注意有的表字段名称可能也不一样，需要修改。

wflow默认的用户、部门、角色等id类型是String，那么如果和目标系统是数值类型，那么需要在`DefaultOrgRepositoryServiceImpl`内和其他地方把这个id`toString()`或者`String.valueOf()`转换一下，注意有的地方id可能会是null，用`toString()`的话需要注意空指针。

还有一处查询涉及到了部门和用户关系，没有列入本接口，需要大家修改下这块。

```java
public interface WflowModelPermsMapper extends BaseMapper<WflowModelPerms> {
    @Select("这段sql需要修改下部门表名称为目标系统的表名，这里默认是wflow的部门表")
    List<ModelGroupVo.Form> selectByPerms(@Param("userId") String userId);
}
```

## 6. 集成用户ID获取

wflow的流程和用户身份信息相关，因此系统需要去读取当前操作流程的人是谁，集成的话需要实现一个方法`com.wflow.utils.UserUtil.getLoginUserId()`。

```java
public class UserUtil {
    /**
     * 获取当前登录用户的id
     * @return 用户ID
     */
    public static String getLoginUserId() {
        // 这里默认是用satoken实现的，需要换成各位自己的逻辑
        return StpUtil.getLoginIdAsString();
    }
}
```

例如：在ruoyi里面是`String.valueOf(SecurityUtils.getUserId())`。

## 8. 组织架构缓存

组织架构存在深层嵌套关系，但是wflow里面有很多地方用到组织架构用户归属关系判断，例如判断张三是不是属于某个部门的人员，判断E部门是不是A部门的子部门等。

我们不知道部门会有多少层级，在wflow中支持递归层级的，这种层级递归需要查询很频繁，那么在wflow中，系统启动的时候会直接把所有部门与用户的归属关系加载到内存中，维护2个Map，如下：

```java
@Slf4j
@Service
public class MemoryOrgOwnershipServiceImpl implements OrgOwnershipService {
    // 用户ID与其所有层级所属部门ID级联映射
    private static final Map<String, Set<String>> userDeptMap = new ConcurrentHashMap<>();
    
    // 部门ID与其所有父级部门ID级联关系映射
    private static final Map<String, Set<String>> deptAndDeptMap = new ConcurrentHashMap<>();
}
```

通过这种方式，系统启动后即可通过缓存关系迅速判断这个归属关系逻辑，无需每次都递归查询数据库。

通常情况下，系统的组织架构及人员数据库会变更，那么这时候缓存就要重新加载了，`OrgOwnershipService`接口提供2个方法用来重载组织架构及用户归属关系缓存，直接调用即可。

```java
/**
 * 重载用户与部门关系
 * @param userId 用户ID
 * @param isRemove 是移除还是加入
 */
void reloadUserDept(String userId, boolean isRemove);

/**
 * 重载部门与部门关系
 */
void reloadDeptAndDept();
```

## 9. 集成部门负责人

在wflow中存在部门负责人的概念，每个部门可以配置一个部门负责人，这个字段默认为leader，如果大家要集成的系统中不存在该字段，那么可以在部门表中新增这个字段，存储作为该部门负责人的ID即可。

## 10. 相关问题及报错

集成过程可能出现一些问题，上述过程有些章节已经有说明，当然可能补充的也不够完善，考虑不到所有的情况，欢迎大家上报给我相关问题及当时的解决方案以做补充 💕

If you get gains，please give a like

[旅人](/lvren-ybpix)

04-08 03:22

2171

IP region 江西

Report

---

# 独立部署集成

Back to document

wflow首选方式是代码集成到各自的项目里面，但是也可以独立运行，使用接口进行交互。

独立部署的话，需要解决2个问题：

- 如何传递登录用户信息
- 如何共享组织架构

## 方案A，共享数据库

wflow与后台管理系统共享同一个数据库，这样后台管理系统负责组织架构用户管理，而wflow则只读取它的数据即可。

```
2%

wflow
后台管理系统
同一个数据库

读取组织架构、用户
管理组织架构、用户

后端
前端
```

### Iframe 嵌入wflow页面

- 主系统
  - Iframe 嵌入wflow页面1
  - Iframe 嵌入wflow页面2
- 主系统菜单
  - wflow菜单1
  - wflow菜单2

```
wflow
后台管理系统
同一个数据库

读取组织架构、用户
管理组织架构、用户

后端
前端
```

### Iframe 嵌入wflow页面

- 主系统
  - Iframe 嵌入wflow页面1
  - Iframe 嵌入wflow页面2
- 主系统菜单
  - wflow菜单1
  - wflow菜单2

---

If you get gains, please give a like

[旅人](/lvren-ybpix)

05-29 21:00

1808

IP region 江西

[![](https://cdn.nlark.com/yuque/0/2024/png/2819278/1710810486051-avatar/6ed8d9fb-6745-48e2-b334-e8aa5503c947.png?x-oss-process=image%2Fresize%2Cm_fill%2Cw_32%2Ch_32%2Fformat%2Cpng)](/dashboard)

## Outline

[方案A，共享数据库](#rtRlh)