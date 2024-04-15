<template>

  <div>
    <el-dialog :visible="plugParamDialogVisible" :title="dialogTitle" append-to-body @close="close">
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

import { createPlugPara, getPlugParaById, modifyPlugParaById } from '@/api/plug'

export default {
  props: {
    plugParamDialogVisible: {
      type: Boolean,
      default: false
    },
    plugParaId: {
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
    plugParaId(newValue) {
      if (newValue === '0') {
        this.paramForm = { name: '', value: '' }
      } else {
        getPlugParaById(newValue).then(response => {
          this.paramForm = response.data
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:plugParamDialogVisible', false)
    },
    confirm() {
      this.$refs.paramForm.validate(valid => {
        if (valid) {
          if (this.plugParaId === '0') {
            createPlugPara(this.plugId, this.paramForm).then(response => {
              if (response.errno === 1) {
                this.$message.success('新增插件参数成功！')
                this.addItem('addPlugPara')
              } else {
                this.$message.error('新增插件参数失败')
              }
              this.$emit('update:plugParamDialogVisible', false)
            })
          } else {
            modifyPlugParaById(this.plugParaId, this.paramForm).then((response) => {
              if (response.errno === 0) { this.$message.success('编辑插件参数成功！') } else {
                this.$message.error('编辑插件参数失败')
              }
              this.plugParamDialogVisible = false
            })
          }
        } else {
          this.$message.error('请将插件参数填写完整！')
          return false
        }
      })
      this.$emit('update:plugParamDialogVisible', false)
    },
    addItem(dataType) {
      this.$emit('add-item', dataType)
    }
  }
}
</script>
