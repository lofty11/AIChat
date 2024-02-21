<template>
  <div class="container">
    <el-aside class="left-aside">
      <el-button class="aside-button" @click="openCreatPlugDialog">
        创建插件
      </el-button>
    </el-aside>

    <div class="divider" />

    <el-main class="right-main">
      <el-form ref="funcInfo" inline="true" :model="funcInfo" label-width="80px">
        <el-form-item label="函数名称">
          <el-input v-model="funcInfo.name" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="checkFuncDialog(funcInfo.name)">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createFuncDialog">新建函数</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="funcTable"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          label="函数名称"
        />
        <el-table-column
          prop="type"
          label="函数类型"
        />
        <el-table-column
          prop="api"
          label="服务Api"
        />
        <el-table-column
          fixed="right"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateFuncDialog(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="configFuncDialog(scope.row)">配置</el-button>
            <el-button type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>

    <el-dialog :visible.sync="createPlugVisible" title="新建插件">
      <el-form>
        <!-- 表单内容 -->
        <el-form-item label="插件名称">
          <el-input v-model="plugInfo.name" />
        </el-form-item>

        <el-form-item label="插件用途">
          <el-input v-model="plugInfo.usage" />
        </el-form-item>

        <el-form-item label="插件描述">
          <el-input v-model="plugInfo.description" type="textarea" maxlength="255" show-word-limit />
        </el-form-item>
        <el-alert
          title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
          type="warning"
        />
        插件参数配置：

        <el-form-item>
          <el-button type="primary" @click="submitPlugInfo">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="updateFuncDialogVisible" title="编辑函数">
      <el-form>
        <!-- 表单内容 -->
        <el-form-item label="函数名称">
          <el-input v-model="funcInfo.name" />
        </el-form-item>
        <el-form-item label="函数类型">
          <el-input v-model="funcInfo.type" />
        </el-form-item>
        <el-form-item label="服务API">
          <el-input v-model="funcInfo.api" />
        </el-form-item>
        <el-form-item label="服务API">
          <el-input v-model="funcInfo.description" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitFuncInfo">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="createFuncDialogVisible" title="创建函数">
      <el-form>
        <!-- 表单内容 -->
        <el-form-item label="函数名称">
          <el-input v-model="funcInfo.name" />
        </el-form-item>
        <el-form-item label="函数类型">
          <el-input v-model="funcInfo.type" />
        </el-form-item>
        <el-form-item label="服务API">
          <el-input v-model="funcInfo.api" />
        </el-form-item>
        <el-form-item label="服务API">
          <el-input v-model="funcInfo.description" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitFuncInfo">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      createPlugVisible: false, // 控制对话框的显示/隐藏
      updateFuncDialogVisible: false,
      createFuncDialogVisible: false,
      plugInfo: {
        name: '',
        usage: '',
        description: ''
      },
      funcInfo: {
        name: '',
        type: '',
        api: '',
        description: ''
      },
      funcTable: [{
        name: 'test1',
        type: 'http请求',
        api: 'SerpApi'
      }
      ]
    }
  },
  methods: {
    openCreatPlugDialog() {
      this.createPlugVisible = true // 打开对话框
    },
    submitPlugInfo() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      console.log('提交表单:', this.plugInfo)

      // 提交完成后关闭对话框
      this.createPlugVisible = false
    },
    checkFuncDialog() {
      console.log(this.funcInfo)
    },
    createFuncDialog() {
      this.createFuncDialogVisible = true
    },
    updateFuncDialog() {
      this.updateFuncDialogVisible = true
    },
    submitFuncInfo() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      console.log('提交表单:', this.funcInfo)

      // 提交完成后关闭对话框
      this.updateFuncDialogVisible = false
    },

    configFuncDialog() {

    }
  }
}
</script>

<style>
.container {
  display: flex;
}
.aside-button {
  margin-top: 20px; /* 调整按钮与边框的上边距 */
  margin-left: 20px;
}
.divider {
  width: 1px;
  background-color: #f0f0f0;
  height: 90vh;
  margin: 0 10px; /* 控制线的左右边距 */
}
</style>
