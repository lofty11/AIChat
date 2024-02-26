<template>
  <el-dialog :visible="configPlugParamDialogVisible" title="添加参数" @close="close">
    <el-form ref="configPlugParam" :model="paramTable" :rules="rules" align="right">
      <!-- 表单内容 -->
      <el-form-item prop="name" label="参数名">
        <el-input v-model="paramTable.name" style="width: 75%" />
      </el-form-item>

      <el-form-item prop="value" label="参数值">
        <el-input v-model="paramTable.value" style="width: 75%" />
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button type="primary" @click="close">关闭</el-button>
            <el-button type="primary" @click="submitPlugParam">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
export default {
  props: {
    ConfigPlugParam: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      paramTable: {
        name: '',
        value: ''
      }
    }
  },
  methods: {
    submitPlugParam() {
      console.log('成功添加参数')
    }
  }
}
</script>
