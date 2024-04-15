<template>
  <div>
    <el-dialog :visible="userParamDialogVisible" :title="dialogTitle" append-to-body @close="close">
      <el-form ref="paramForm" :model="paramForm" :rules="rules" align="right">
        <!-- 表单内容 -->
        <el-form-item prop="name" label="字段名称">
          <el-input v-model="paramForm.name" style="width: 75%" />
        </el-form-item>

        <el-form-item prop="field" label="字段">
          <el-input v-model="paramForm.field" style="width: 75%" />
        </el-form-item>
        <el-form-item prop="type" label="类型">
          <el-select v-model="paramForm.type" style="width: 75%" placeholder="请选择类型">
            <el-option label="类型一" value="type01" />
            <el-option label="类型二" value="type02" />
            <el-option label="类型三" value="type03" />
          </el-select>
        </el-form-item>
        <el-form-item prop="enumerationRange" label="枚举范围">
          <el-input v-model="paramForm.enumerationRange" style="width: 75%" />
        </el-form-item>
        <el-form-item prop="necessary" label="是否必填" align="left">
          <el-switch v-model="paramForm.necessary" />
        </el-form-item>
        <el-form-item prop="description" label="描述">
          <el-input v-model="paramForm.description" style="width: 75%" />
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
    dialogTitle: {
      type: String,
      default: ''
    },
    plugId: {
      type: String,
      default: ''
    },
    userParaId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      typeList: [],
      paramForm: {
        name: '',
        field: '',
        type: '',
        enumerationRange: '',
        necessary: false,
        description: ''
      },
      form: {
        name: '',
        field: '',
        type: '',
        enumerationRange: '',
        necessary: 0,
        description: ''
      },
      rules: {
        name: [{ required: true, message: '字段名称不能为空', trigger: 'blur' }],
        field: [{ required: true, message: '字段不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '类型不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    userParaId(newValue) {
      if (newValue === '0') {
        this.paramForm = [
          {
            name: '',
            field: '',
            type: '',
            enumerationRange: '',
            necessary: false,
            description: ''
          }
        ]
      } else {
        getUserParaById(newValue).then(response => {
          this.paramForm = response.data
          console.log(response.data)
          this.$set(this.paramForm, 'necessary', Boolean(response.data.necessary))
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:userParamDialogVisible', false)
    },
    trans() {
      this.form.name = this.paramForm.name
      this.form.field = this.paramForm.field
      this.form.type = this.paramForm.type
      this.form.enumerationRange = this.paramForm.enumerationRange
      this.form.description = this.paramForm.description
      this.form.necessary = this.paramForm.necessary ? 1 : 0
    },
    confirm() {
      this.$refs.paramForm.validate(valid => {
        if (valid) {
          if (this.userParaId === '0') {
            this.trans()
            createUserPara(this.plugId, this.form).then(response => {
              if (response.errno === 1) {
                this.$message.success('新增用户参数成功！')
                this.addItem('addUserPara')
              } else {
                this.$message.error('新增用户参数失败')
              }
              this.$emit('update:userParamDialogVisible', false)
            })
          } else {
            modifyUserParaById(this.userParaId, this.paramForm).then(response => {
              if (response.errno === 0) {
                this.$message.success('编辑用户参数成功！')
              } else {
                this.$message.error('编辑用户参数失败')
              }
              this.userParamDialogVisible = false
            })
          }
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
