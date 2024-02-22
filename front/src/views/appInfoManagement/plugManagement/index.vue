<template>
  <div class="container">
    <el-aside class="left-aside">
      <el-button class="aside-button" @click="openAddPlugDialog">
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
          <el-button type="primary" @click="getFunc()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addFuncDialog">新建函数</el-button>
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
    <add-plug :add-plug-dialog-visible.sync="addPlugDialogVisible" />
    <!--    编辑、创建、配置函数-->
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

    <el-dialog :visible.sync="configFuncDialogVisible" title="配置函数">
      <el-form>
        <!--      -->
      </el-form>
    </el-dialog>
    <!--    编辑、创建、配置函数-->
  </div>
</template>

<script>
import AddPlug from '@/views/appInfoManagement/plugManagement/components/addPlug.vue'
export default {
  components: { AddPlug },
  data() {
    return {
      addPlugDialogVisible: false,
      updateFuncDialogVisible: false,
      createFuncDialogVisible: false,
      configFuncDialogVisible: false,
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
    openAddPlugDialog() {
      this.addPlugDialogVisible = true // 打开对话框
    },
    getFunc() {
      return this.funcInfo
    },
    addFuncDialog() {
      this.createFuncDialogVisible = true
    },
    updateFuncDialog() {
      this.updateFuncDialogVisible = true
    },
    configFuncDialog() {
      this.configFuncDialogVisible = true
    },

    // submitPlugInfo() {
    //   // 在这里处理提交逻辑
    //   // 可以发送请求或执行其他操作
    //   console.log('提交表单:', this.plugInfo)
    //
    //   // 提交完成后关闭对话框
    //   this.createPlugVisible = false
    // },

    submitFuncInfo() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      console.log('提交表单:', this.funcInfo)

      // 提交完成后关闭对话框
      this.updateFuncDialogVisible = false
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
