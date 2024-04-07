<template>
  <!--    新增插件-->
  <el-dialog :visible="addPlugDialogVisible" title="新建插件" append-to-body @close="close">
    <el-form ref="addPlug" :model="plugInfo" :rules="rules">

      <el-form-item prop="name" label="插件名称">
        <el-input v-model="plugInfo.name" style="width:80%" />
      </el-form-item>

      <el-form-item prop="purpose" label="插件用途">
        <el-input v-model="plugInfo.purpose" style="width:80%" />
      </el-form-item>

      <el-form-item prop="description" label="插件描述">
        <el-input v-model="plugInfo.description" style="width:80%" type="textarea" :rows="4" maxlength="255" show-word-limit />
      </el-form-item>

      <el-alert
        title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
        type="warning"
      />
      <el-form-item prop="available" label="是否可用">
        <el-switch v-model="plugInfo.available" />
      </el-form-item>
      <el-form-item prop="open" label="是否公开">
        <el-switch v-model="plugInfo.open" />
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button type="primary" @click="close">关闭</el-button>
            <el-button type="primary" @click="confirm">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
  <!--    新增插件-->
</template>
<script>

import { createPlug } from '@/api/plug'

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
        purpose: '',
        description: '',
        available: false,
        open: false
      },
      form: {
        name: '',
        purpose: '',
        description: '',
        available: 0,
        open: 0
      },
      rules: {
        name: [{ required: true, message: '插件名称不能为空', trigger: 'blur' }],
        purpose: [{ required: true, message: '插件用途不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '插件描述不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:addPlugDialogVisible', false)
    },
    trans() {
      this.$message.success('提交成功！')
      this.form.name = this.plugInfo.name
      this.form.description = this.plugInfo.description
      this.form.purpose = this.plugInfo.purpose
      this.form.open = this.plugInfo.open ? 1 : 0
      this.form.available = this.plugInfo.available ? 1 : 0
    },
    confirm() {
      this.$refs.addPlug.validate((valid) => {
        if (valid) {
          this.trans()
          createPlug(this.form)
          this.$refs.addPlug.resetFields()
          this.$emit('update:addPlugDialogVisible', false)
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    }

  }
}
</script>
