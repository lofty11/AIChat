<template>
  <div style="padding:30px;">

    <div class="container">
      <el-container>
        <el-header>
          <el-row type="flex" align="middle">
            <el-col :span="2"><span>API名称：</span></el-col>
            <el-col :span="6">
              <el-input size="large" placeholder="请输入API名称" clearable/>
            </el-col>
            <el-col :span="2" align="center">
              <el-button type="primary">查询</el-button>
            </el-col>
            <el-col :span="1" align="center">
              <el-button type="primary" @click="openAddAPIDialog">新增</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
            <el-table-column prop="name" label="应用服务API名称"/>
            <el-table-column prop="code" label="API code"/>
            <el-table-column prop="operation" label="操作">
              <template #default>
                <el-button type="text" size="small" @click="openDetailDialog">详情</el-button>
                <el-button type="text" size="small" @click="openEditDialog">编辑</el-button>
                <el-button type="text" size="small" style="color:red" @click="deleteButton">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="block" style="margin-top:15px;">
            <el-pagination
              align='right'
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[1,5,10,20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tableData.length">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </div>

    <add-API :add-a-p-i-dialog-visible.sync="addAPIDialogVisible"/>
    <Detail :detail-dialog-visible.sync="detailDialogVisible"/>
    <Edit :edit-dialog-visible.sync="editDialogVisible"/>
  </div>
</template>

<script>
import AddAPI from "@/views/appInfoManagement/appServiceAPI/components/addAPI";
import Detail from "@/views/appInfoManagement/appServiceAPI/components/detail";
import Edit from "@/views/appInfoManagement/appServiceAPI/components/edit";

export default {
  name: 'Index',
  components: {AddAPI, Detail, Edit},
  data() {
    return {
      addAPIDialogVisible: false,
      detailDialogVisible: false,
      editDialogVisible: false,
      tableData: [
        {
          name: 'SerpApi',
          code: 'SerpApi'
        }
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 2 // 每页的数据条数
    }
  },
  methods: {
    // 打开新增API
    openAddAPIDialog() {
      this.addAPIDialogVisible = true
      // console.log(this.addAPIDialogVisible);
    },
    // 打开详情页
    openDetailDialog() {
      this.detailDialogVisible = true
    },
    openEditDialog() {
      this.editDialogVisible = true
    },
    // 每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.currentPage = 1;
      this.pageSize = val;
    },
    // 当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
    deleteButton() {
      this.$confirm('是否确认删除该应用服务API?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
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


