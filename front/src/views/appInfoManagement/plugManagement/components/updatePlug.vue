<template>
  <el-dialog :visible="updatePlugDialogVisible" title="编辑插件" @close="close">
    <el-form ref="updatePlug" :model="plugInfo" :rules="rules" align="left">
      <!-- 表单内容 -->
      <el-form-item prop="name" label="插件名称">
        <el-input v-model="plugInfo.name" style="width:80%" />
      </el-form-item>

      <el-form-item prop="usage" label="插件用途">
        <el-input v-model="plugInfo.usage" style="width:80%" />
      </el-form-item>

      <el-form-item prop="description" label="插件描述">
        <el-input v-model="plugInfo.description" style="width:80%" type="textarea" :rows="4" maxlength="255" show-word-limit />
      </el-form-item>
      <el-form-item>
        <el-alert
          title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
          type="warning"
        />
      </el-form-item>

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
            <el-button type="primary" @click="updatePlugInfo">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>

export default {
  props: {
    updatePlugDialogVisible: {
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
    updatePlugInfo() {
      console.log(this.plugInfo)
      this.$message.success('编辑插件成功')
      this.updatePlugDialogVisible = false
    },
    close() {
      this.$emit('update:updatePlugDialogVisible', false)
    }
  }
}
</script>
