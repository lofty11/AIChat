<template>

  <div>
    <el-dialog :visible="plugParamDialogVisible" :title="dialogTitle" append-to-body @close="close">
      <el-form ref="paramTable" :model="paramTable" :rules="rules" align="right">
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
      paramTable: {
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
        this.paramTable = [{ name: '', value: '' }]
      } else {
        getPlugParaById(newValue).then(response => {
          this.paramTable = response.data
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:plugParamDialogVisible', false)
    },
    confirm() {
      this.$refs.paramTable.validate((valid) => {
        if (valid) {
          if (this.plugParaId === '0') {
            createPlugPara(this.plugId, this.paramTable).then((response) => {
              if (response.errno === 1) { this.$message.success('新增插件参数成功！') }
              this.$emit('update:plugParamDialogVisible', false)
            })
          } else {
            modifyPlugParaById(this.plugParaId, this.paramTable).then((response) => {
              if (response.errno === 0) { this.$message.success('编辑插件参数成功！') }
              this.plugParamDialogVisible = false
            })
          }
        } else {
          this.$message.error('请将插件参数填写完整！')
          return false
        }
      })
      this.$emit('update:plugParamDialogVisible', false)
    }
  }
}
</script>
