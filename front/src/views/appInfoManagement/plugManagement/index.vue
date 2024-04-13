<template>
  <div class="container">
    <el-aside class="left-aside">
      <el-form>
        <el-form-item>
          <el-button class="aside-button" @click="openPlugDialog('新增插件','0')">
            创建插件
          </el-button>
        </el-form-item>

        <el-table
          :data="plugTable"
          style="width: 95%; margin-left: 10px"
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
              <el-button type="text" size="small" @click="openPlugDialog('编辑插件',data.row.id)">编辑</el-button>
              <el-button type="text" size="small" @click="configPlugDialog(data.row.id)">配置</el-button>
              <el-button type="text" size="small" style="color: red" @click="delPlug(data.row.id,data.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-form>

    </el-aside>

    <div class="divider" />

    <el-main class="right-main">
      <el-form ref="funcInfo" inline="true" :model="funcForm" label-width="80px">
        <el-form-item label="函数名称">
          <el-input v-model="searchData" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search(funcForm.name)">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openFuncDialog('新建函数','0')">新建函数</el-button>
        </el-form-item>
        <el-table
          :data="funcTable.slice((currentPage-1)*pageSize,currentPage*pageSize)"
          style="width: 100%"
        >
          <el-table-column
            prop="name"
            label="函数名称"
          />
          <el-table-column
            prop="typeName"
            label="函数类型"
          />
          <el-table-column
            prop="apiName"
            label="服务Api"
          />
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template v-slot="data">
              <el-button type="text" size="small" @click="openFuncDialog('编辑函数',data.row.id)">编辑</el-button>
              <el-button type="text" size="small" @click="configFuncDialog(data.row.id)">渲染</el-button>
              <el-button type="text" size="small" style="color: red" @click="delFunc(data.row.id,data.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-form-item class="block" style="margin-top:15px;">

          <el-pagination
            align="right"
            :current-page="currentPage"
            :page-sizes="[1,5,10,20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="plugTable.length"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-form-item>
      </el-form>

    </el-main>
    <!--    创建、编辑插件-->
    <Plug :plug-id.sync="plugId" :plug-dialog-visible.sync="plugDialogVisible" :dialog-title="plugDialogTitle" @add-item="addItemToList" />
    <!--    创建、编辑插件-->

    <!--    配置插件-->
    <config-plug :plug-id.sync="plugId" :config-plug-dialog-visible.sync="configPlugDialogVisible" />
    <!--    配置插件-->

    <!--    编辑、创建、配置函数-->
    <Func :func-id="funcId" :func-dialog-visible.sync="funcDialogVisible" :dialog-title="funcDialogTitle" />
    <config-func ref="configFunc" :config-func-dialog-visible.sync="configFuncDialogVisible" />
    <!--    编辑、创建、配置函数-->

  </div>
</template>

<script>
import ConfigFunc from '@/views/appInfoManagement/plugManagement/components/configFunc.vue'
import ConfigPlug from '@/views/appInfoManagement/plugManagement/components/configPlug.vue'
import { delFuncById, delPlugById, getAllFunc, getAllPlug, getFuncByName } from '@/api/plug'
import Func from '@/views/appInfoManagement/plugManagement/components/func.vue'
import Plug from '@/views/appInfoManagement/plugManagement/components/plug.vue'

export default {
  components: { Plug, Func, ConfigPlug, ConfigFunc },
  data() {
    return {
      isSearch: false,
      searchData: '',
      plugId: '0',
      funcId: '0',
      funcDialogTitle: '',
      plugDialogTitle: '',
      plugDialogVisible: false,
      configFuncDialogVisible: false,
      funcDialogVisible: false,
      configPlugDialogVisible: false,
      funcTypeList: [],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 5, // 每页的数据条数
      funcForm: {
        name: '',
        enName: '',
        type: '',
        typeName: '',
        api: '',
        apiName: '',
        description: ''
      },
      funcTable:
        [{
          id: 1,
          name: 'test1',
          enName: 'test1',
          type: '0',
          typeName: 'http请求',
          api: '0',
          apiName: 'SerpApi',
          description: 'test1'
        }
        ],
      plugTable: [{
        name: 'plug 1'
      }]
    }
  },
  watch: {
    searchData(newValue) {
      if (newValue === '') {
        getAllFunc().then((response) => {
          this.funcTable = response.data.list
          this.pageSize = response.data.pageSize
        })
        this.isSearch = false
      }
    }
  },
  mounted() {
    getAllPlug().then((response) => {
      this.plugTable = response.data.list
      this.pageSize = response.data.pageSize
    }).catch((error) => {
      console.error('获取插件失败:', error)
    })
    getAllFunc().then((response) => {
      this.funcTable = response.data.list
      this.pageSize = response.data.pageSize
    }).catch((error) => {
      console.error('获取函数失败:', error)
    })
  },
  methods: {
    search() {
      if (this.searchData === '') {
        this.$message.error('请输入函数名称！')
      } else {
        getFuncByName(this.searchData).then(response => {
          if (response.errno === 0) {
            this.funcTable = []
            this.funcTable.push(response.data)
            this.isSearch = true
          } else {
            this.$message.info('未查询到相关函数')
          }
        })
      }
    },
    openPlugDialog(title, id) {
      this.plugDialogTitle = title
      this.plugId = id
      this.plugDialogVisible = true
    },
    openFuncDialog(title, id) {
      this.funcDialogTitle = title
      this.funcId = id
      this.funcDialogVisible = true
    },
    configFuncDialog(data) {
      this.configFuncDialogVisible = true
    },
    delFunc(id, index) {
      this.$confirm('确定删除该函数？').then(() => {
        delFuncById(id).then(response => {
          if (response.errno === 0) {
            this.$message.success('删除函数成功！')
            this.funcTable.splice(index, 1)
          } else {
            this.$message.error('删除函数失败')
          }
        }).catch(error => {
          this.$message.error(error)
        })
      })
    },
    delPlug(id, index) {
      this.$confirm('确定删除该插件？').then(() => {
        delPlugById(id).then(response => {
          if (response.errno === 0) {
            this.$message.success('删除插件成功！')
            this.plugTable.splice(index, 1)
          } else {
            this.$message.error('删除插件失败')
          }
        }).catch(error => {
          this.$message.error(error)
        })
      })
    },
    configPlugDialog(id) {
      this.configPlugDialogVisible = true
      this.plugId = id
    },
    addItemToList(data, type) {
      switch (type) {
        case 'addPlug':
          this.plugTable.push(data)
          break
      }
    },
    // 每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.currentPage = 1
      this.pageSize = val
    },
    // 当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
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
