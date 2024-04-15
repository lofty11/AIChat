<template>
  <!--    新增插件-->
  <el-dialog :visible="plugDialogVisible" :title="dialogTitle" append-to-body @close="close">
    <el-form ref="plugForm" :model="plugForm" :rules="rules">

      <el-form-item prop="name" label="插件名称">
        <el-input v-model="plugForm.name" style="width:80%" />
      </el-form-item>

      <el-form-item prop="purpose" label="插件用途">
        <el-input v-model="plugForm.purpose" style="width:80%" />
      </el-form-item>

      <el-form-item prop="description" label="插件描述">
        <el-input v-model="plugForm.description" style="width:80%" type="textarea" :rows="4" maxlength="255" show-word-limit />
      </el-form-item>

      <el-alert
        title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
        type="warning"
      />
      <el-form-item prop="available" label="是否可用">
        <el-switch v-model="plugForm.available" />
      </el-form-item>
      <el-form-item prop="open" label="是否公开">
        <el-switch v-model="plugForm.open" />
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

import { createPlug, getPlugById, modifyPlugById } from '@/api/plug'

export default {
  name: 'Plug',
  props: {
    plugDialogVisible: {
      type: Boolean,
      default: false
    },
    dialogTitle: {
      type: String,
      default: ''
    },
    plugId: {
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      plugForm: {
        id: '0',
        name: '',
        purpose: '',
        description: '',
        available: false,
        open: false
      },
      form: {
        id: '0',
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
  watch: {
    plugId(newValue) {
      if (newValue === '0') {
        this.plugForm = {
          name: '',
          purpose: '',
          description: '',
          available: false,
          open: false
        }
      } else {
        getPlugById(newValue).then(response => {
          this.plugForm = response.data
          this.$set(this.plugForm, 'open', Boolean(response.data.open))
          this.$set(this.plugForm, 'available', Boolean(response.data.available))
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:plugDialogVisible', false)
    },
    trans() {
      this.form.id = this.plugForm.id
      this.form.name = this.plugForm.name
      this.form.description = this.plugForm.description
      this.form.purpose = this.plugForm.purpose
      this.form.open = this.plugForm.open ? 1 : 0
      this.form.available = this.plugForm.available ? 1 : 0
    },
    confirm() {
      this.$refs.plugForm.validate((valid) => {
        if (valid) {
          this.trans()
          if (this.plugId === '0') {
            createPlug(this.form).then(response => {
              if (response.errno === 1) {
                this.addItem('addPlug')
                this.$message.success('添加插件成功')
                this.$emit('update:plugDialogVisible', false)
              } else {
                this.$message.error('添加插件失败')
              }
            })
          } else {
            modifyPlugById(this.plugId, this.form).then((response) => {
              if (response.errno === 0) {
                this.$message.success('编辑插件成功！')
                this.$emit('update:plugDialogVisible', false)
              } else {
                this.$message.error('编辑插件失败')
              }
            })
          }
          this.$refs.plugForm.resetFields()
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    },
    addItem(dataType) {
      this.$emit('add-item', dataType)
    }
  }
}
</script>
