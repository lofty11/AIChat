<template>
  <!--    新增插件-->
  <el-dialog :visible="addPlugDialogVisible" title="新建插件" append-to-body @close="close">
    <el-form ref="addPlug" :model="plugInfo" :rules="rules">

      <el-form-item prop="name" label="插件名称">
        <el-input v-model="plugInfo.name" style="width:80%" />
      </el-form-item>

      <el-form-item prop="usage" label="插件用途">
        <el-input v-model="plugInfo.usage" style="width:80%" />
      </el-form-item>

      <el-form-item prop="description" label="插件描述">
        <el-input v-model="plugInfo.description" style="width:80%" type="textarea" :rows="4" maxlength="255" show-word-limit />
      </el-form-item>

      <el-alert
        title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
        type="warning"
      />
      <el-form-item label="是否可用">
        <el-switch v-model="plugInfo.necessary" />
      </el-form-item>
      <el-form-item label="是否公开">
        <el-switch v-model="plugInfo.public" />
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button type="primary" @click="close">关闭</el-button>
            <el-button type="primary" @click="submitPlugInfo">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
  <!--    新增插件-->
</template>
<script>

export default {
  props: {
    addPlugDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      plugInfo: {
        name: '',
        usage: '',
        description: '',
        usable: false,
        public: false
      },
      rules: {
        name: [{ required: true, message: '插件名称不能为空', trigger: 'blur' }],
        usage: [{ required: true, message: '插件用途不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '插件描述不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:addPlugDialogVisible', false)
    },

    submitPlugInfo() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      console.log('提交表单:', this.plugInfo)
      this.$message.success('新增插件成功')
      // 提交完成后关闭对话框
      this.addPlugDialogVisible = false
    }

  }
}
</script>
