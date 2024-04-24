<template>

  <div>
    <el-dialog :visible="userParamDialogVisible" :title="dialogTitle" append-to-body @close="close">
      <el-form ref="paramForm" :model="paramForm" :rules="rules" align="right">
        <!-- 表单内容 -->
        <el-form-item prop="name" label="参数名">
          <el-input v-model="paramForm.name" style="width: 75%" />
        </el-form-item>

        <el-form-item prop="value" label="参数值">
          <el-input v-model="paramForm.value" style="width: 75%" />
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
  </div>

</template>
<script>

import { createUserPara, getUserParaById, modifyUserParaById } from '@/api/plug'

export default {
  props: {
    userParamDialogVisible: {
      type: Boolean,
      default: false
    },
    userParaId: {
      type: String,
      default: '0'
    },
    plugId: {
      type: String,
      default: '0'
    },
    dialogTitle: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      paramForm: {
        name: '',
        value: ''
      },
      rules: {
        name: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
        value: [{ required: true, message: '参数值不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    userParaId(newValue) {
      if (newValue === '0') {
        this.paramForm = { name: '', value: '' }
      } else {
        getUserParaById(newValue).then(response => {
          this.paramForm = response.data
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:userParamDialogVisible', false)
    },
    confirm() {
      this.$refs.paramForm.validate(valid => {
        if (valid) {
          if (this.userParaId === '0') {
            createUserPara(this.plugId, this.paramForm).then(response => {
              if (response.errno === 1) {
                this.$message.success('新增用户参数成功！')
                this.addItem('addUserPara')
              } else {
                this.$message.error('新增用户参数失败')
              }
              this.$emit('update:userParamDialogVisible', false)
            })
          } else {
            modifyUserParaById(this.userParaId, this.paramForm).then((response) => {
              if (response.errno === 0) { this.$message.success('编辑用户参数成功！') } else {
                this.$message.error('编辑用户参数失败')
              }
              this.userParamDialogVisible = false
            })
          }
        } else {
          this.$message.error('请将用户参数填写完整！')
          return false
        }
      })
      this.$emit('update:userParamDialogVisible', false)
    },
    addItem(dataType) {
      this.$emit('add-item', dataType)
    }
  }
}
</script>
