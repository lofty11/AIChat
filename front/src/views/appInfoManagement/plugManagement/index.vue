<template>
  <div class="container">
    <el-aside class="left-aside">

      <el-form>
        <el-form-item>
          <el-button class="aside-button" @click="openAddPlugDialog">
            创建插件
          </el-button>
        </el-form-item>

        <el-form-item align="center">
          <el-table
            :data="plugTable"
            border
            style="width: 95%"
          >
            <el-table-column
              prop="name"
              label="插件名称"
            />
            <el-table-column
              fixed="right"
              label="操作"
            >
              <template v-slot="data">
                <el-button type="text" size="small" @click="updatePlugDialog(data.row)">编辑</el-button>
                <el-button type="text" size="small" @click="configPlugDialog(data.row)">配置</el-button>
                <el-button type="text" size="small" style="color: red" @click="delPlug(data.row.name)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>

      </el-form>
    </el-aside>

    <div class="divider" />

    <el-main class="right-main">
      <el-form ref="funcInfo" inline="true" :model="funcInfo" label-width="80px">
        <el-form-item label="函数名称">
          <el-input v-model="funcInfo.name" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getFunc(funcInfo.name)">查询</el-button>
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
          <template v-slot="data">
            <el-button type="text" size="small" @click="updateFuncDialog(data.row)">编辑</el-button>
            <el-button type="text" size="small" @click="configFuncDialog(data.row)">配置</el-button>
            <el-button type="text" size="small" style="color: red" @click="delFunc(data.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <!--    创建插件-->
    <add-plug :add-plug-dialog-visible.sync="addPlugDialogVisible" />
    <!--    创建插件-->

    <!--    编辑、配置插件-->
    <update-plug :update-plug-dialog-visible.sync="updatePlugDialogVisible" />
    <config-plug :config-plug-dialog-visible.sync="configPlugDialogVisible" />
    <!--    编辑、配置插件-->

    <!--    编辑、创建、配置函数-->
    <add-func ref="addFunc" :add-func-dialog-visible.sync="addFuncDialogVisible" @updateFuncTable="getFuncList" />
    <update-func ref="updateFunc" :update-func-dialog-visible.sync="updateFuncDialogVisible" :current-id="currentId" />
    <config-func ref="configFunc" :config-func-dialog-visible.sync="configFuncDialogVisible" />
    <!--    编辑、创建、配置函数-->

  </div>
</template>

<script>
import AddPlug from '@/views/appInfoManagement/plugManagement/components/addPlug.vue'
import AddFunc from '@/views/appInfoManagement/plugManagement/components/addFunc.vue'
import UpdateFunc from '@/views/appInfoManagement/plugManagement/components/updateFunc.vue'
import ConfigFunc from '@/views/appInfoManagement/plugManagement/components/configFunc.vue'
import UpdatePlug from '@/views/appInfoManagement/plugManagement/components/updatePlug.vue'
import ConfigPlug from '@/views/appInfoManagement/plugManagement/components/configPlug.vue'
export default {
  components: { ConfigPlug, UpdatePlug, ConfigFunc, UpdateFunc, AddFunc, AddPlug },
  data() {
    return {
      currentId: null,
      addPlugDialogVisible: false,
      updateFuncDialogVisible: false,
      addFuncDialogVisible: false,
      configFuncDialogVisible: false,
      updatePlugDialogVisible: false,
      configPlugDialogVisible: false,
      funcInfo: {
        name: '',
        enName: '',
        typeId: '',
        apiId: '',
        description: ''
      },
      funcTable: [{
        id: 1,
        name: 'test1',
        enName: 'test1',
        type: 'http请求',
        api: 'SerpApi',
        description: 'test1'
      },
      {
        id: 2,
        name: 'test2',
        enName: 'test2',
        type: 'http请求2',
        api: 'SerpApi2',
        description: 'test2'
      }
      ],
      plugTable: [{
        name: 'plug 1'
      },
      {
        name: 'plug 2'
      }]
    }
  },
  methods: {
    openAddPlugDialog() {
      this.addPlugDialogVisible = true // 打开对话框
    },
    // getFunc() {
    //   return this.funcInfo
    // },
    // async getFuncList() {
    //   this.funcTable = await getFuncList()
    // },
    addFuncDialog() {
      this.addFuncDialogVisible = true
    },
    updateFuncDialog(data) {
      this.updateFuncDialogVisible = true
      // this.$nextTick(() => {
      //   this.$refs.updateFunc.getFuncDetail(data)
      // })
    },
    configFuncDialog(data) {
      this.configFuncDialogVisible = true
      // this.$nextTick(() => {
      //   this.$refs.configFunc.getFuncDetail(data)
      // })
    },
    delFunc(id) {
      this.$confirm('确定删除？').then(async() => {
        this.$message.success('删除成功')
        // await delFunc(id)
      })
      // this.$message.success('删除成功')
    },
    delPlug(name) {
      this.$confirm('确定删除？').then(async() => {
        this.$message.success('删除成功')
        // await delPlug(name)
      })
      // this.$message.success('删除成功')
    },
    updatePlugDialog(data) {
      this.updatePlugDialogVisible = true
    },
    configPlugDialog(data) {
      this.configPlugDialogVisible = true
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
