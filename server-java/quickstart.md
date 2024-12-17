# Appearance

## 开始

willian fu 2022 ~

## [开始](#开始)

### [简介](#简介)

wflow工作流是一个简单易用，面向普通用户的工作流系统，用户只需要接受简单的教学即可上手使用，自行创建所需的日常审批流程。

---

**👀起因：**

第一次接触工作流是由于当时公司业务部门提了一个需求，希望可以实现新产品从提出意向到开发结束的整个生命周期流程的管控，里面包含各级人员和领导等不同角色的审批，任务交接流转。于是网上搜索了一圈，发现了 `activiti`、 `flowable` 这两个开源的流程引擎，功能上很强大，能满足需求，但是流程设计器上手真的比较困难，这对于普通用户来说没有经过专业人员进行辅助或者专门培训，基本很难用起来。当时公司用的是某D办公，自带的审批功能在操作一番后感觉相当的友好，遂萌生实现一套类似系统的想法。

### [一起交流](#一起交流)

👩‍👦‍👦大家可扫码加入交流群，如果二维码失效了，可以加我微信 `willianfu_` 备注 `wflow` 拉你入群。

**未完成/待完善的功能**

- 表单明细表组件数据绑定，校验
- 分栏布局组件内表单数据绑定、校验
- 提交表单部分，分栏、明细表及其他组件回显校验
- 提交表单时根据人员设置限制表单权限
- 提交时根据审批流程设计渲染已确定的执行流程步骤及指定人员

### [起步](#起步)

#### [下载并启动项目](#下载并启动项目)

**注意：**作者的开发环境是 `node14.18.0` 、 `vuecli 4.1.1`、 `edge浏览器` , 高版本node 可能需要在package.json的scripts项内命令添加启动参数。

```bash
# 克隆源码
git clone https://gitee.com/willianfu/jw-workflow-engine.git
cd jw-workflow-engine

# 修改main.js中后端接口地址，破坏这个表达式，使其为公开的服务器IP
Vue.prototype.BASE_URL = 'http://' + (process.env.NODE_ENV === 'development-' ? "localhost" : "47.100.202.245");

# 小弱鸡服务器，大家不要造垃圾数据，且珍惜😘

# 安装依赖
npm install
# 启动
npm run serve
```

📢 如果启动报错请检查错误信息看看是否是依赖版本和当前所安装的 node 版本不兼容，自行安装兼容版本的依赖。

👍 启动成功后访问 [http://localhost:88](http://localhost:88) 即可打开页面。

![image-20220720094501757](https://pic.rmb.bdstatic.com/bjh/3da7567c3f36a4d2178d310390bfc1f2.png)

### [操作教程](#操作教程)

#### [工作区](#工作区)

点击工作区，进入审批列表，按分组可以进行展开折叠。

![image-20220724225022126](https://pic.rmb.bdstatic.com/bjh/1a9bd706586f6cbbc64721ba152edd1a.png)

##### [发起审批](#发起审批)

点击对应项可弹出提交表单窗口。

![image-20220724225156405](https://pic.rmb.bdstatic.com/bjh/5bfb33c6bfd47a327ad3dda3e0d7771f.png)

#### [管理后台](#管理后台)

从首页进入管理后台，展示出来的是所有已经设计好的审批列表。

##### [审批列表](#审批列表)

审批列表按分组展示，点击每个分组右侧可对分组进行编辑及删除，如果分组内有表单，则删除分组后内部表单会被移动到其他分组（默认）。

![image-20220724225423708](https://pic.rmb.bdstatic.com/bjh/91d4d8cdaf632f67916846a69e18d508.png)

##### [分组及表单拖拽排序](#分组及表单拖拽排序)

###### [分组排序](#分组排序)

![image-20220724225749669](https://pic.rmb.bdstatic.com/bjh/e509e35de64a32b949d8cbd48b49fe2a.png)

![image-20220724225853154](https://pic.rmb.bdstatic.com/bjh/bd6f2801c0e87eda065a1bf072d693d2.png)

###### [表单排序](#表单排序)

![image-20220724230032985](https://pic.rmb.bdstatic.com/bjh/b820afc407597c6c8900043c62bd4c65.png)

#### [表单流程设计器](#表单流程设计器)

点击 `新建表单/编辑` 进入 `审批表单流程设计器`。

##### [基础设置](#基础设置)

![image-20220724231222926](https://pic.rmb.bdstatic.com/bjh/9b9a47e62cf4d52acaa7eaffa319f688.png)

##### [表单设计](#表单设计)

> 表单设计是用来设计发起审批流程时填写的表单的，拖拽式交互。

从左侧组件库选取组件放置到中间设计区域，点击设计区组件，可在右侧面板配置组件的设置项。

![image-20220724222124900](https://pic.rmb.bdstatic.com/bjh/096e0dacdbc8ced5db475517c325601c.png)

###### [分栏布局](#分栏布局)

> 普通组件是独占一行的，假设我们要让2个组件并排显示，就需要使用分栏了。

![image-20220724233902503](https://pic.rmb.bdstatic.com/bjh/a91a26ab472f42f1d7ebf598641929e4.png)
![image-20220724231814888](https://pic.rmb.bdstatic.com/bjh/8e195c2275b19067f21bfc2c48bb1771.png)

**❗ 注意事项：** 分栏组件会自动按每2个组件为一行进行布局，假设分栏组件内放了5个子组件，那么前四个会两两占用一列， **剩下多的一个会独占一行**。

**分栏组件内可以放置其他组件，也可以放置分栏组件进行嵌套！！！！！**

![image-20220724233253171](https://pic.rmb.bdstatic.com/bjh/67cb9c1292785061f49229ae8e5e1163.png)

###### [明细表](#明细表)

> 有时候我们需要提交一系列数据，这些数据有表格的性质，这时候可以使用明细表组件。

明细表类似分栏容器，可以在内部放置其他组件。

![image-20220724234359498](https://pic.rmb.bdstatic.com/bjh/a85f8d76572255ee017a36af0ba1f962.png)

##### [审批流程设计](#审批流程设计)

> 审批流程设计，顾名思义，用来设计用户提交的表单流程，需要经过哪些人的审批流转。

![image-20220724234519380](https://pic.rmb.bdstatic.com/bjh/906bfdf30712a132c9b8c906e56e6eea.png)

**支持多种类型节点**

###### [发起人](#发起人)

> 也就是发起，提交这个流程的人员。

###### [审批人](#审批人)

> 当任务流转到审批人时，这个节点设置的相关人员需要对这个提交的流程进行审批（同意/驳回）。

###### [抄送人](#抄送人)

> 当流程到达抄送人节点时，将会通知到相关人员。

###### [条件分支](#条件分支)

> 有时候我们的流程需要动态的情况，根据一些条件进行不同的处理流程，这时候就可以添加条件分支，设置进入每个流程分支的条件。

![image-20220724235146294](https://pic.rmb.bdstatic.com/bjh/e060d19a925bee4f4d10382d2b3a770e.png)

###### [并行分支](#并行分支)

> 有时候我们需要审批流程不需要条件选择，同时进行几个步骤，这时候可以利用并行分支，当所有分支的步骤都结束后才会进行到下一步。

![image-20220724235422400](https://pic.rmb.bdstatic.com/bjh/4251844922f6ae63cec916ffde02968c.png)

###### [延时处理](#延时处理)

> 有时候我们需要让整个流程卡在某个地方，等待一段时间再继续，这时候可以在需要等待的地方插入延时处理节点。

###### [触发器](#触发器)

> 本节点属于扩展功能，流程在到达此处时会触发一个动作，应当由开发人员使用，用来打通与其他系统的交互。

通过Http请求，来动态修改表单数据或者将表单数据传递给外部系统。

---

### ON THIS PAGE

- [简介](#简介)
- [一起交流](#一起交流)
- [起步](#起步)
- [下载并启动项目](#下载并启动项目)
- [操作教程](#操作教程)
- [工作区](#工作区)
- [管理后台](#管理后台)
- [表单流程设计器](#表单流程设计器)

---

### 公告

#### QQ/微信 交流群（📢 尽量加微信群）

- Q群③：854047337 ✔️
- Q群①：156972829 (已满) 🚫
- Q群②：853185510 (已满) 🚫
- 微信群：添加作者 willianfu_ 拉群
- 🎈🎈 pro商业版用户请联系作者加pro专属微信群，备注pro加群，pro暂无QQ群

---

#### GitHub/Gitee

- [Gitee码云](https://gitee.com/willianfu/jw-workflow-engine)
- [Github](https://github.com/willianfu/wflow)

---

# 项目介绍

willian fu 2022 ~

## 依赖相关

本项目基于 `Vue2.x`、`ElementUI` 构建，css使用 `Less`，请注意 `node` 与 `less` 的版本兼容问题。

## 目录结构

```
└─src
    ├─api （Api接口）
    ├─assets （静态资源）
    │  └─image
    ├─components （公共组件，空）
    │  └─common
    ├─router （路由）
    ├─store （Vuex）
    ├─utils （工具函数）
    └─views （页面）
        ├─admin （表单流程设计器）
        │  └─layout （布局设计）
        │      ├─form （表单布局设计）
        │      └─process （流程布局设计）
        ├─common （组件）
        │  ├─form （表单组件）
        │  │  ├─components （拖拽设计的表单元素组件）
        │  │  └─config （表单元素组件的右侧配置项面板组件）
        │  └─process （流程组件）
        │      ├─config （流程设计的流程节点组件的配置面板组件）
        │      └─nodes （流程设计的流程节点组件）
        ├─process （空）
        │  └─node
        └─workspace （工作区，发起流程，设计出来的流程列表）
```

## 集成到现有前端项目

### vue项目

#### 仅使用流程设计器

流程设计器入口文件为 `src\views\admin\layout\ProcessDesign.vue`，与之相关的所有文件都要引入，主要文件在下图所示，或者也可以参考 `master-precessDesign` 分支（不一定最新）。

![image-20220731221004670](https://pic.rmb.bdstatic.com/bjh/185556f44bdd7c99c427cfde4039b578.png)

#### 使用整个表单流程设计器

表单流程设计器入口在 `src\views\admin\FormProcessDesign.vue`，可以直接引入。

如果你也需要那个表单列表，文件为 `src\views\adminFormsPanel.vue`。

### 其他框架

如果是其他不兼容的框架/版本，可以单独部署成一个独立的前端项目，再使用 `<iframe>` 进行引入。

---

### 公告

#### QQ/微信 交流群（📢 尽量加微信群）

- Q群③：854047337 ✔️
- Q群①：156972829 (已满) 🚫
- Q群②：853185510 (已满) 🚫
- 微信群：添加作者 willianfu_ 拉群
- 🎈🎈pro商业版用户请联系作者加pro专属微信群，备注pro加群，pro暂无QQ群

---

### GitHub/Gitee

- [Gitee码云](https://gitee.com/willianfu/jw-workflow-engine)
- [Github](https://github.com/willianfu/wflow)

---

# 表单设计

willian fu 2022 ~

## 表单设计

### 数据结构

为了考虑后期对不同UI框架的兼容及数据传输的便捷，表单使用json来进行描述，前端通过json再反向渲染该表单。

表单数据存储在vuex中，具体对象为 `$store.state.process.formItems`，是个数组。

### 表单组件

表单组件是构成表单的基本元素，一个表单可以有多个组件，在UI中体现为如下图：

![image-20220724222124900](https://pic.rmb.bdstatic.com/bjh/096e0dacdbc8ced5db475517c325601c.png)

#### 组件值类型

每个组件都有对应的值，我们需要定义值的类型，有如下类型定义：

```javascript
const ValueType = {
  string: 'String',
  object: 'Object',
  array: 'Array',
  number: 'Number',
  date: 'Date', // yyyy-MM-dd xxx类型的字符串日期格式
  user: 'User', // 人员
  dept: 'Dept', // 部门
  dateRange: 'DateRange'
}
```

#### 组件数据结构

每个组件需要预先定义好数据结构，存在于文件 `/src/views/common/form/ComponentsConfigExport.js` 中，此文件中定义的组件将被展示到表单设计器左侧组件候选区。

**结构如下：**

```javascript
{
  title: '多行文本输入', // 组件标题
  name: 'TextareaInput', // 组件名，组件是根据组件名来决定渲染哪个组件的
  icon: 'el-icon-more-outline', // 组件在设计器候选区的图标
  value: '', // 组件的值
  valueType: ValueType.string, // 组件值数据类型
  props: { // 组件的附加属性
    required: false,  // 公共属性，是否必填
    enablePrint: true // 公共属性，是否允许打印
    // 组件其他设置项，根据组件类型来自定义
  }
}
```

### 表单组件开发

`wflow` 中自带的组件可能并不满足大家的需求，这时候就需要开发自定义组件了，对组件库进行扩充。

#### 组件规范

开发的组件尽量符合统一规范，每个组件都以一个独立的 `.vue` 文件存在，组件结构定义应如下：

```html
<template>
  <div>
    <div v-if="mode === 'DESIGN'">
      <!-- 组件在设计器中的样子 -->
    </div>
    <div v-else>
      <!-- 组件在预览及真实显示的样子 -->
    </div>
  </div>
</template>

<script>
// 混入配置
import componentMinxins from '../ComponentMinxins'

export default {
  mixins: [componentMinxins],
  name: "组件名称",
  components: {},
  props: {
    placeholder: {
      type: String,
      default: '请输入内容'
    }
  },
  data() {
    return {}
  },
  methods: {}
}
</script>
```

#### 示例

我们以系统自带组件库中的 `AmountInput.vue` （金额输入框）组件为例：

![image-20220720112056889](https://pic.rmb.bdstatic.com/bjh/f6f53d67ba3929cdc30b21ff59c2a152.png)

##### 定义组件数据结构

打开 `/src/views/common/form/ComponentsConfigExport.js`，往内添加一项：

```javascript
{
  title: '金额输入框',
  name: 'AmountInput', // 定义组件名称
  icon: 'el-icon-coin',
  value: '',
  valueType: ValueType.number, // 金额的值类型为数值
  props: {
    required: false,
    enablePrint: true,
    precision: 1, // 数值精度，允许的小数位数
    showChinese: true // 是否展示中文大写
  }
}
```

##### 定义组件

打开 `/src/views/common/form/components/` 目录，往内新建一个文件 `AmountInput.vue`，内容如下：

```html
<template>
  <div style="max-width: 350px">
    <div v-if="mode === 'DESIGN'">
      <el-input size="medium" disabled :placeholder="placeholder"/>
      <div style="margin-top: 15px" v-show="showChinese">
        <span>大写：</span>
        <span class="chinese">{{chinese}}</span>
      </div>
    </div>
    <div v-else>
      <el-input-number :min="0" controls-position="right" :precision="precision" size="medium" clearable v-model="_value" :placeholder="placeholder"/>
      <div v-show="showChinese">
        <span>大写：</span>
        <span class="chinese">{{chinese}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import componentMinxins from '../ComponentMinxins'

export default {
  mixins: [componentMinxins],
  name: "AmountInput",
  components: {},
  props: {
    placeholder: {
      type: String,
      default: '请输入金额'
    },
    // 是否展示中文大写
    showChinese: {
      type: Boolean,
      default: true
    },
    // 数值精度
    precision: {
      type: Number,
      default: 0
    }
  },
  computed:{
    // 计算属性绑定金额
    chinese(){
      return this.convertCurrency(this.value)
    },
  },
  data() {
    return {}
  },
  methods: {
    // 数字转中文大写金额
    convertCurrency(money) {
      // 汉字的数字
      const cnNums = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
      // 基本单位
      const cnIntRadice = ['', '拾', '佰', '仟'];
      // 对应整数部分扩展单位
      const cnIntUnits = ['', '万', '亿', '兆'];
      // 对应小数部分单位
      const cnDecUnits = ['角', '分', '毫', '厘'];
      // 整数金额时后面跟的字符
      const cnInteger = '整';
      // 整型完以后的单位
      const cnIntLast = '元';
      // 最大处理的数字
      let maxNum = 999999999999999.9999;
      // 金额整数部分
      let integerNum;
      // 金额小数部分
      let decimalNum;
      // 输出的中文金额字符串
      let chineseStr = '';
      // 分离金额后用的数组，预定义
      let parts;
      if (money === '') {
        return '';
      }
      money = parseFloat(money);
      if (money >= maxNum) {
        // 超出最大处理数字
        return '';
      }
      if (money === 0) {
        chineseStr = cnNums[0] + cnIntLast + cnInteger;
        return chineseStr;
      }
      // 转换为字符串
      money = money.toString();
      if (money.indexOf('.') === -1) {
        integerNum = money;
        decimalNum = '';
      } else {
        parts = money.split('.');
        integerNum = parts[0];
        decimalNum = parts[1].substr(0, 4);
      }
      // 获取整型部分转换
      if (parseInt(integerNum, 10) > 0) {
        var zeroCount = 0;
        var IntLen = integerNum.length;
        for (let i = 0; i < IntLen; i++) {
          let n = integerNum.substr(i, 1);
          let p = IntLen - i - 1;
          let q = p / 4;
          let m = p % 4;
          if (n == '0') {
            zeroCount++;
          } else {
            if (zeroCount > 0) {
              chineseStr += cnNums[0];
            }
            // 归零
            zeroCount = 0;
            chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
          }
          if (m == 0 && zeroCount < 4) {
            chineseStr += cnIntUnits[q];
          }
        }
        chineseStr += cnIntLast;
      }
      // 小数部分
      if (decimalNum !== '') {
        let decLen = decimalNum.length;
        for (let i = 0; i < decLen; i++) {
          let n = decimalNum.substr(i, 1);
          if (n !== '0') {
            chineseStr += cnNums[Number(n)] + cnDecUnits[i];
          }
        }
      }
      if (chineseStr === '') {
        chineseStr += cnNums[0] + cnIntLast + cnInteger;
      } else if (decimalNum === '') {
        chineseStr += cnInteger;
      }
      return chineseStr;
    }
  }
}
</script>

<style scoped>
.chinese {
  color: #afadad;
  font-size: smaller;
}
</style>
```

##### 定义组件配置面板

每个组件的设置项都有可能不一样，因此为了统一，我们给每个组件都添加一个设置面板。

在路径 `/src/views/common/form/config/` 目录下，新建一个 `AmountInputConfig.vue` 文件：

```html
<template>
  <div>
    <el-form-item label="提示文字">
      <el-input size="small" v-model="value.placeholder" placeholder="请设置提示语"/>
    </el-form-item>
    <el-form-item label="保留小数">
      <el-input-number controls-position="right" :precision="0"
                       :max="3" :min="0" size="small"
                       v-model="value.precision"  placeholder="小数位数"/>
      位
    </el-form-item>
    <el-form-item label="展示大写">
      <el-switch v-model="value.showChinese"></el-switch>
    </el-form-item>
  </div>
</template>

<script>
export default {
  name: "AmountInputConfig",
  components: {},
  props: {
    // value为定义组件的数据结构里面的 props 对象
    value: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {}
  },
  methods: {}
}
</script>
```

建议直接使用 `el-form-item` 组件，方便布局：

```html
<el-form-item label="设置项名称">
  <!-- 设置的组件，比如输入框、下拉选择等 -->
</el-form-item>
```

最终效果如下图：

![image-20220720135853094](https://pic.rmb.bdstatic.com/bjh/39e6ac34982ed22f4e9340628638ada6.png)

---

### 组件的开发技巧

#### 与后端接口数据交互

有时候我们可能需要一个从后端获取数据的组件，以上面的 `金额输入框组件` 为例。

> 🤐 假设我们需要从后端获取表单提交人账户的可用余额，来限制金额输入框的最大值。

此时可以将API请求写在组件的生命周期钩子函数中。

```html
<template>
  <div style="max-width: 350px">
    <div v-if="mode === 'DESIGN'"></div>
    <div v-else>
      <el-input-number :min="0" :max="maxLimit" controls-position="right"
                       :precision="precision" size="medium" clearable v-model="_value"
                       :placeholder="placeholder"/>
      <div v-show="showChinese">
        <span>大写：</span>
        <span class="chinese">{{chinese}}</span>
      </div>
      <div>
        <span>可用余额：</span>
        <span>￥ {{maxLimit}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import componentMinxins from '../ComponentMinxins'
// 引入接口
import {getAmount} from '../api'

export default {
  mixins: [componentMinxins],
  name: "组件名称",
  components: {},
  props: {},
  data() {
    return {
      maxLimit: 0
    }
  },
  created() {
    // 组件创建完成后加载可用余额
    this.loadAmount()
  },
  methods: {
    loadAmount() {
      getAmount(localStorage.getItem('userId')).then(res => {
        this.maxLimit = res.data
      }).catch(err => {
        //......
      })
    }
  }
}
</script>
```

#### 组件内引用其他组件

如果组件过于复杂，可以将组件进行多文件拆分，最后用父组件进行渲染。

### 组件动态渲染原理

参见1： [组件基础 — Vue.js](https://cn.vuejs.org/v2/guide/components.html#%E5%8A%A8%E6%80%81%E7%BB%84%E4%BB%B6)

参见2： [渲染函数 & JSX — Vue.js](https://cn.vuejs.org/v2/guide/render-function.html)

---

##### GitHub/Gitee

- [Gitee码云](https://gitee.com/willianfu/jw-workflow-engine)
- [Github](https://github.com/willianfu/wflow)

##### QQ/微信 交流群（📢 尽量加微信群）

- Q群③：854047337 ✔️
- Q群①：156972829 (已满) 🚫
- Q群②：853185510 (已满) 🚫
- 微信群：添加作者 willianfu 拉群
- 🎈🎈pro商业版用户请联系作者加pro专属微信群，备注pro加群，pro暂无QQ群

---

# Appearance

## 流程设计

willian fu 2022 ~

### 流程设计

流程设计器是用来设计审批流程的，也就是绘制流程图

> 📢 鉴于部分同学针对流程设计器的需求，把流程设计器单独抽取出来，放在 `master-processDesign` 分支

📋 流程设计器核心文件为 `src\views\admin\layout\process\ProcessTree.vue`，整个流程设计器都是基于此组件渲染，手撸 vue-render😅。

## 流程节点数据结构

### 节点数据

整个流程设计器数据存放于 vuex `$store.state.design.process` 中，是一个以根节点为起始的深层嵌套对象，结构如下：

```json
{
    "id": "90aasvbsh8a0a7f",
    "parentId": "7d89sf7d8sasf",
    "type": "ROOT",
    "name": "发起人",
    "props": {}, //节点属性，见下方props说明
    "children": {},
    "branchs": []
}
```

| 字段名      | 类型     | 含义                | 说明                                                        |
| ----------- | -------- | ------------------- | ----------------------------------------------------------- |
| id          | String   | 节点ID             | 当前流程内唯一，由前端随机生成                             |
| parentId    | String   | 父级节点ID         | 用来向上搜索，关联子父                                     |
| type        | String   | 节点类型           | ROOT(发起人，根节点)、APPROVAL(审批)、CC(抄送)、CONDITIONS(条件组)、CONCURRENTS（并行节点组）、CONDITION(条件子分支)、CONCURRENT（并行子分支）、DELAY(延时节点)、TRIGGER(触发器)、EMPTY(空节点，占位) |
| name        | String   | 节点名称           | 显示在设计器中卡片头部名称                               |
| props       | Object   | 节点属性设置       | 节点的设置项内容                                         |
| children    | Object   | 子节点项           | 节点下方的子节点，无限嵌套，内部字段与当前结构相同     |
| branchs     | Array    | 子分支项           | 当type 为 CONDITIONS / CONCURRENTS 时，该项存在，内容为条件或并行节点内的所有分支 |

### 节点props设置项

> 每种节点的props设置项结构是不一样的，各自如下

#### ROOT (根节点)

> 根节点是最顶层节点，发起人节点

```json
{
    // 发起人权限，哪些 人/部门 可以发起这个审批
    "assignedUser": [
        {
            "id": "user_id_001",
            "name": "张三",
            "type": "user" //根据类型判断是人或者部门
        },
        {
            "id": "dept_id_001",
            "name": "研发部",
            "type": "dept" //根据类型判断是人或者部门
        }
    ],
    "formPerms": [] //表单权限设置
}
```

#### APPROVAL（审批节点）

> 审批节点设置审批人及审批规则

```json
{
    // 审批处理的类型 ASSIGN_USER 指定人员、SELF_SELECT 发起人自选、LEADER_TOP 连续多级主管、LEADER 主管、ROLE 指定角色、SELF 发起人自己、REFUSE 自动拒绝、FORM_USER指定表单联系人
    "assignedType": "类型",
    "mode": "AND", //多人审批时的审批模式，AND 会签、OR 或签、NEXT 顺序依次审批
    "sign": false, //审批同意时是否需要签字
    "formPerms": [], //表单权限设置
    // 审批人为空时的规则
    "nobady": {
        "handler": "PASS", //PASS 直接通过、 TO_ADMIN 转交主管理员、TO_USER 转交指定人员
        "assignedUser": [] //TO_USER 时的指定人员
    },
    // 审批超时限制设置
    "timeLimit": {
        // 超时时间限制
        "timeout": {
            "unit": "H", //时间单位 M分钟、H小时、D天
            "value": 2 //时间值
        },
        // 超时后的处理规则
        "handler": {
            "type": "REFUSE", //PASS 自动通过、REFUSE 自动驳回、NOTIFY 发送通知进行提醒
            "notify": {
                "once": true, //是否只提醒一次
                "hour": 1 //重复提醒，几小时提醒一次
            }
        }
    },
    "assignedUser": [], //指定审批人员 ASSIGN_USER 时不为空
    // 发起人自选
    "selfSelect": {
        "multiple": true // 是否多选 true/false
    },
    // 连续多级主管
    "leaderTop": {
        "endCondition": "TOP", //结束条件 TOP 直到最上级主管、 LEVEL 指定不超过多少级主管
        "endLevel": 1 //指定级别主管审批后结束本节点
    },
    // 指定主管审批
    "leader": {
        "level": 1 //发起人指定级别主管
    },
    // 指定角色审批
    "role": [
        {
            "id": "user",
            "name": "普通用户"
        },
        {
            "id": "admin",
            "name": "管理员"
        }
    ], //指定审批人为系统角色
    "refuse": { //驳回设置
        //TO_END 驳回直接结束流程、TO_NODE 驳回到指定节点、TO_BEFORE 驳回到上一级
        "type": "TO_END",
        "target": "" //驳回到指定ID的节点
    },
    "formUser": "" //类型为指定表单联系人时，对应表单组件ID
}
```

#### CONDITION (条件节点)

> 条件选项节点是 CONDITIONS 的子节点，存在于 branchs 子分支内，用来设置条件

```json
{
    "groupsType": "OR", //条件组逻辑关系 OR、AND
    "groups": [
        {
            "groupType": "AND", //条件组内条件关系 OR、AND
            //组内子条件
            "conditions": [
                {
                    "cid": "d78s96fd9s", //组件ID，通过组件ID索引到表单设计器中的组件
                    "compare": ">=", //比较运算符 >大于 <小于 大于等于 小于等于 范围
                    "value": [] //比较值，如果只需要比较一个值，那么只取value[0]
                }
            ]
        }
    ],
    "expression": "(A AND B) OR C" //自定义表达式，灵活构建逻辑关系
}
```

#### CONCURRENT（并行节点）

> CONCURRENT是CONCURRENTS的字节点，无条件流转，多路分支同时并行进入

```
无属性设置
```

#### CC（抄送节点）

> 当到达此节点时，流程状态会被发送给指定的用户

```json
{
    "shouldAdd": false, //允许发起人自选抄送人
    "assignedUser": [] //指定抄送人员
}
```

#### DELAY（延时处理节点）

> 流程到达此节点时，会被阻塞一段时间才被放行

```json
{
    "type": "FIXED", //延时类型 FIXED:到达当前节点后延时固定时长 、AUTO:延时到 dateTime设置的时间
    "time": 30, //延时时间
    "unit": "M", //时间单位 D天 H小时 M分钟
    "dateTime": "18:34:00" //如果当天没有超过设置的此时间点，就延时到这个指定的时间，到了就直接跳过不延时
}
```

#### TRIGGER（触发器节点）

> 流程到达此节点时，会触发一个提前设置好的动作，用来与外部系统对接

```json
{
    "type": "WEBHOOK", //触发的动作类型 WEBHOOK、EMAIL
    "http": {
        "method": "GET", //请求方法 支持GET/POST
        "url": "", //URL地址，可以直接带参数
        "headers": [ //http header
            {
                "name": "",
                "isField": true,
                "value": "" //支持表达式 ${xxx} xxx为表单字段名称
            }
        ],
        "contentType": "FORM", //请求参数类型
        "params": [ //请求参数
            {
                "name": "",
                "isField": true, //是表单字段还是自定义
                "value": "" //支持表达式 ${xxx} xxx为表单字段名称
            }
        ],
        "retry": 1, //重试次数
        "handlerByScript": false, //是否使用脚本处理请求结果
        "success": "function resHandler(res) {\n  return true;\n}",
        "fail": "function resHandler(res) {\n  return true;\n}"
    },
    "email": {
        "subject": "",
        "to": [],
        "content": ""
    }
}
```

#### EMPTY (空节点)

> 空节点时用来在设计器中每一个子分支末尾占位的，辅助UI构建，无设置项，处理时直接忽略

### 表单权限设置

表单权限设置目前只存在于两种节点， `APPROVEL` 和 `ROOT`，都在这俩节点的 `props` 字段内的 `formPerms` 中

🔓 表单权限对应三种类型

- 👀 只读：R （该表单项节点关联人员只能看表单结果，不能修改）
- ✍ 可编辑：E （该表单项节点关联人员可以修改表单内容）
- 😎 隐藏：H （该表单项对该节点关联人员隐藏，不展示）

`formPerms` 的内容如下：

```json
[
    {
        "id": "field3781138962528",  //表单字段ID
        "perm": "R", //权限标识
        "title": "单行文本输入" //表单名称
    }
]
```

❗ 请注意，如果没有手动修改表单权限设置的话， `formPerms` 内是默认为空的

因此约定 `APPROVEL` 节点默认所有表单权限为只读， `ROOT` 节点默认所有权限为可编辑。

### ON THIS PAGE

- [流程节点数据结构](/docs/wflow/process.html#流程节点数据结构)
- [节点数据](/docs/wflow/process.html#节点数据)
- [节点props设置项](/docs/wflow/process.html#节点props设置项)
- [表单权限设置](/docs/wflow/process.html#表单权限设置)

## 公告

### QQ/微信 交流群（📢 尽量加微信群）

- Q群③：854047337 ✔️
- Q群①：156972829 (已满) 🚫
- Q群②：853185510 (已满) 🚫
- 微信群：添加作者 willianfu\_ 拉群
- 🎈🎈pro商业版用户请联系作者加pro专属微信群，备注pro加群，pro暂无QQ群

---

### GitHub/Gitee

- [Gitee码云](https://gitee.com/willianfu/jw-workflow-engine)
- [Github](https://github.com/willianfu/wflow)

---

# 服务端开发指南

willian fu 2022 ~

## 服务端开发指南

目前只开源了前端设计器部分，大家配套后端使用的话，需要自行开发服务端应用，可以参考如下说明。

## 基本要求

### 组织机构选择器

组织机构选择器由于在项目很多地方引用了，如果在不修改组件代码的情况下，建议按如下规则适配。

> 该组件存在于 `src/components/common/organizationPicker.vue`，对应API接口在 `src/api/org.js`，有2个接口，分别如下。

#### 获取组织架构列表

- **方法**： `getOrgTree`
- **URL**： `/oa/org/tree`
- **请求类型**： `GET`
- **参数**：
  - deptId： 当前选中的部门ID
  - type：
    - `user` - 返回部门和部门内的人员
    - `dept` - 只返回部门
    - `role` - 只返回系统角色

- **返回结果**：（部门和人员结构一致，注意部门应当排序在前）

    ```json
    [
        {
            "id": "部门/人员ID",
            "name": "部门/人员名",
            "avatar": "人员的头像的 base64 / url",
            "type": "本数据对象的类型，user/dept"
        }
    ]
    ```

#### 按拼音/姓名搜索人员

> 当在搜索框直接输入拼音/姓名时，将会发起搜索请求，后端应当根据参数内容匹配用户的姓名或者姓名拼音。

- **方法**： `getUserByName`
- **URL**： `oa/org/tree/user/search`
- **请求类型**： `GET`
- **参数**：
  - userName：搜索参数

- **返回结果**：（人员列表）

    ```json
    [
        {
            "id": "人员ID",
            "name": "人员名",
            "avatar": "人员的头像的 base64 / url",
            "type": "本数据对象的类型，user"
        }
    ]
    ```

## 流程解析

流程的数据存储在 vuex 的 `state.design.process` 中，由于序列化后是 JSON 结构，非标准 Bpmn，因此需要在后端做转换，也建议在后端进行转换。

最常用的 Java 开源流程引擎有 `activiti`、`Flowable`，两者同源，差不多用法，都提供了使用代码 API 来构建 XML 的功能。

### wflow节点与Bpmn概念对应关系

#### 发起人

`发起人` 可以映射为 `startEvent`，也可以忽略，发起人主要是为了设置哪些人可以发起该流程，也就是该流程的权限，并且也是默认的候选条件。

#### 审批人

审批人对应 `UserTask`，该节点是需要审批人对发起人提交的表单进行处理，以及如果有需要的话也可以修改提交的表单（要设置表单可编辑权限）。

审批意味着主要是做两件事，`同意/驳回`，同意就进入下一任务，而驳回的策略有两种，默认直接驳回到 `EndEvent`，如果设置了则驳回到指定节点，因为有2种结果分流，所以此处应该设置条件顺序流。

#### 抄送人

抄送人对应 `ServiceTask`，应当调用 Java 指定的功能类，传入参数执行发送通知的功能。

#### 延时处理

延时处理对应 `intermediateCatchEvent`，设置为定时器类型的中间事件，根据配置解析。

#### 触发器

触发器对应 `ServiceTask`，应当调用 Java 指定的功能类，传入参数执行 HTTP 请求或者发邮件，由于触发器支持脚本，因此要完全支持触发器功能的话需要引入 `js执行器`。

**务必设置运行资源限制，防止用户上传恶意脚本搞崩服务器。**

```xml
<!-- https://mvnrepository.com/artifact/org.openjdk.nashorn/nashorn-core -->
<dependency>
    <groupId>org.openjdk.nashorn</groupId>
    <artifactId>nashorn-core</artifactId>
    <version>15.3</version>
</dependency>
```

#### 条件分支

条件节点对应 `exclusive gateway`，这与普通节点有所不同。

![image-20220720145227591](https://pic.rmb.bdstatic.com/bjh/fec7f2894a598c5b50f930b3f64ac93c.png)

如上图所示，实际上外部还有一个看不到的节点对象，将这几个子分支条件 `包裹起来`，这个红色部分才是条件节点，也就是排他网关，绿色条件块应当解析为 **带条件的** `sequenceFlow` 连接线，该节点内设置的条件应该作为 `sequenceFlow` 的条件。

#### 并行分支

并行分支对应 `parallelGateway`，与条件分支一样，外部也有一个对象包裹内部的并行分支，外部这个对象应该解析为 `并行网关`，但是它的分支节点首部应当看做普通顺序流，由于并行网关成对存在，因此需要注意创建合流的分支，将每个子分支末端连接到合流点。

![image-20220720150019350](https://pic.rmb.bdstatic.com/bjh/4ea8259c04daa76b91f8b9863d46a66e.png)

---

### ON THIS PAGE

- [基本要求](/docs/wflow/server.html#基本要求)
- [组织机构选择器](/docs/wflow/server.html#组织机构选择器)
- [流程解析](/docs/wflow/server.html#流程解析)
- [wflow节点与Bpmn概念对应关系](/docs/wflow/server.html#wflow节点与bpmn概念对应关系)

---

### 公告

##### QQ/微信 交流群（📢 尽量加微信群）

- Q群③：854047337 ✔️
- Q群①：156972829 (已满) 🚫
- Q群②：853185510 (已满) 🚫
- 微信群：添加作者 willianfu\_ 拉群
- 🎈🎈 pro商业版用户请联系作者加pro专属微信群，备注pro加群，pro暂无QQ群

---

### GitHub/Gitee

- [Gitee码云](https://gitee.com/willianfu/jw-workflow-engine)
- [Github](https://github.com/willianfu/wflow)