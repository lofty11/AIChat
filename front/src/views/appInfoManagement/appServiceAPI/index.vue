<template>
  <div style="padding:30px;">

    <div class="container">
      <el-container>
        <el-header>
          <el-row type="flex" align="middle">
            <el-col :span="2"><span>API名称：</span></el-col>
            <el-col :span="6">
              <el-input id="search" v-model="searchData" size="large" placeholder="请输入API名称" clearable />
            </el-col>
            <el-col :span="2" align="center">
              <el-button type="primary" @click="search">查询</el-button>
            </el-col>
            <el-col :span="1" align="center">
              <el-button type="primary" @click="openAPIDialog('创建应用服务API',true,false,'0')">新增</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
            <el-table-column type="index" label="序号" />
            <el-table-column prop="apiName" label="应用服务API名称" />
            <el-table-column prop="apiCode" label="API code" />
            <el-table-column prop="operation" label="操作">
              <template v-slot="scope">
                <el-button type="text" size="small" @click="openDetailDialog(scope.row.id)">详情</el-button>
                <el-button type="text" size="small" @click="openAPIDialog('编辑应用服务API',true,false,scope.row.id)">编辑</el-button>
                <el-button type="text" size="small" @click="openAPIDialog('配置应用服务API',false,true,scope.row.id)">配置</el-button>
                <el-button type="text" size="small" style="color:red" @click="deleteButton(scope.row.id,scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="block" style="margin-top:15px;">
            <el-pagination
              align="right"
              :current-page="currentPage"
              :page-sizes="[1,5,10]"
              :page-size="pageSize"
              layout="sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-main>
      </el-container>
    </div>
    <API :id.sync="apiId" :api-dialog-visible.sync="apiDialogVisible" :form-visible="formVisible" :parameter-visible="parameterVisible" :title.sync="dialogTitle" />
    <Detail :detail-dialog-visible.sync="detailDialogVisible" :list.sync="detailData" />
  </div>
</template>

<script>
import Detail from '@/views/appInfoManagement/appServiceAPI/components/detail'
import API from '@/views/appInfoManagement/appServiceAPI/components/API'
import { deleteApiById, getAllAPI, getApiById, getApiByName } from '@/api/api'
export default {
  name: 'Index',
  components: { API, Detail },
  data() {
    return {
      isSearch: false,
      searchData: '',
      apiId: '0',
      apiDialogVisible: false,
      detailDialogVisible: false,
      formVisible: false,
      parameterVisible: false,
      dialogTitle: '',
      tableData: [
        {
          name: 'SerpApi',
          code: 'SerpApi'
        }
      ],
      detailData: [
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 5 // 每页的数据条数
    }
  },
  watch: {
    apiDialogVisible(newValue) {
      if (newValue === false && this.isSearch === false) {
      //  console.log(this.isSearch)
        getAllAPI().then((response) => {
          this.tableData = response.data.list
          this.pageSize = response.data.pageSize
        })
      }
    },
    searchData(newValue) {
      if (newValue === '') {
        getAllAPI().then((response) => {
          this.tableData = response.data.list
          this.pageSize = response.data.pageSize
        })
        this.isSearch = false
      }
    }
  },
  created() {
    // 在组件创建后立即发起 HTTP 请求
    getAllAPI({ page: 1, pageSize: 10 }).then((response) => {
      this.tableData = response.data.list
      this.pageSize = response.data.pageSize
    })
  },
  methods: {
    // 打开API窗口
    openAPIDialog(title, formV, parameterV, id) {
      this.apiDialogVisible = true
      this.dialogTitle = title
      this.parameterVisible = parameterV
      this.formVisible = formV
      this.apiId = String(id)
    },
    // 打开详情页
    openDetailDialog(id) {
      this.detailDialogVisible = true
      this.detailData = this.tableData[id - 1]
    },
    // 搜索
    search() {
      // console.log(document.getElementById('search').value)
      if (this.searchData === '') {
        this.$message({
          type: 'error',
          message: '请输入查询信息！'
        })
      } else {
        getApiByName(this.searchData).then(response => {
          if (response.errno === 0) {
            getApiById(response.data).then(resp => {
              this.tableData = []
              this.tableData.push(resp.data)
              console.log(this.tableData)
              this.isSearch = true
            })
          } else {
            this.$message({
              type: 'info',
              message: '未查询到相关API'
            })
          }
        })
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
      if (val * this.pageSize > this.tableData.length) {
        getAllAPI({ page: val, pageSize: this.pageSize }).then(response => {
          if (response.errno === 0) {
            this.tableData.push(...response.data.list)
          }
        })
      }
    },
    deleteButton(id, index) {
      this.$confirm('是否确认删除该应用服务API?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteApiById(id).then(response => {
          if (response.errno === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.tableData.splice(index, 1)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }

}
</script>

