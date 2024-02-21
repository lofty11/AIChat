<template>
  <div class="container">
    <el-aside class="left-aside">
      <el-button class="aside-button" @click="openCreatPlugDialog">
        创建插件
      </el-button>
    </el-aside>

    <div class="divider" />

    <el-main class="right-main">
      右侧内容
    </el-main>

    <el-dialog :visible.sync="dialogVisible" title="新建插件">
      <el-form>
        <!-- 表单内容 -->
        <el-form-item label="参数1">
          <el-input v-model="formData.param1" />
        </el-form-item>

        <el-form-item label="参数2">
          <el-input v-model="formData.param2" />
        </el-form-item>

        <!-- 其他表单项 -->

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false, // 控制对话框的显示/隐藏
      formData: {
        param1: '',
        param2: ''
        // 其他参数
      }
    }
  },
  methods: {
    openCreatPlugDialog() {
      this.dialogVisible = true // 打开对话框
    },
    submitForm() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      console.log('提交表单:', this.formData)

      // 提交完成后关闭对话框
      this.dialogVisible = false
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
