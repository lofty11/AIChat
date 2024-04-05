<template>
  <div>
    <el-dialog
      :title.sync="dialogTitle"
      :visible="parameterDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form ref="form" label-position="left" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字段名称" prop="fieldName">
          <el-input v-model="form.fieldName" />
        </el-form-item>
        <el-form-item label="字段" prop="field">
          <el-input v-model="form.field" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="string" value="string" />
            <el-option label="json" value="json" />
            <el-option label="integer" value="integer" />
            <el-option label="array" value="array" />
          </el-select>
        </el-form-item>
        <el-form-item label="枚举范围" prop="enumerationRange">
          <el-input v-model="form.enumerationRange" />
        </el-form-item>
        <el-form-item label="是否必填" prop="required">
          <el-switch v-model="form.required" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  createInputPara,
  createOutputPara, getInputParaById,
  getOutputParaById,
  modifyInputParaById,
  modifyOutputParaById
} from '@/api/api'

export default {
  name: 'Parameter',
  props: {
    parameterDialogVisible: {
      type: Boolean,
      default: false
    },
    dialogTitle: {
      type: String,
      default: ''
    },
    apiId: {
      type: String,
      default: ''
    },
    id: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      form: {
        fieldName: '',
        field: '',
        type: '',
        enumerationRange: '',
        required: 0,
        description: ''
      },
      rules: {
        fieldName: [
          { required: true, message: '请输入字段名称', trigger: 'blur' }

        ],
        field: [
          { required: true, message: '请输入字段', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' }
        ],
        required: [
          { required: true, message: '请选择是否必填', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    id() {
      this.handleChange()
    },
    dialogTitle() {
      this.handleChange()
    },
    parameterDialogVisible(newValue) {
      if (newValue === true) {
        if (this.dialogTitle === '添加插件API出参' || this.dialogTitle === '添加插件API入参') {
          if (this.$refs.form !== undefined) {
            this.$refs.form.resetFields()
          }
        }
      }
    }
  },
  methods: {
    addItem(type) {
      // 在子组件中触发一个自定义事件，并传递需要添加的值
      this.$emit('add-item', JSON.parse(JSON.stringify(this.form)), type)
    },
    handleChange() {
      if (this.dialogTitle === '编辑插件API出参') {
        getOutputParaById(this.id).then(response => {
          if (response.errno === 0) {
            this.form = response.data
            this.form.required = Boolean(this.form.required)
          }
        })
      } else if (this.dialogTitle === '编辑插件API入参') {
        getInputParaById(this.id).then(response => {
          if (response.errno === 0) {
            this.form = response.data
            this.form.required = Boolean(this.form.required)
          }
        })
      } else if (this.id === '0') {
        this.form = {
          fieldName: '',
          field: '',
          type: '',
          enumerationRange: '',
          required: 0,
          description: ''
        }
      }
    },
    handleClose() {
      this.$emit('update:parameterDialogVisible', false)
    },
    cancel() {
      this.$emit('update:parameterDialogVisible', false)
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.form.required = Number(this.form.required)
          if (this.dialogTitle === '添加插件API入参') {
            createInputPara(this.apiId, this.form).then(response => {
              if (response.errno === 1) {
                this.addItem('i')
                this.$message.success('添加成功！')
              }
            })
          } else if (this.dialogTitle === '添加插件API出参') {
            createOutputPara(this.apiId, this.form).then(response => {
              if (response.errno === 1) {
                this.addItem('o')
                this.$message.success('添加成功！')
              }
            })
          } else if (this.dialogTitle === '编辑插件API出参') {
            modifyOutputParaById(this.id, this.form).then(response => {
              if (response.errno === 0) {
                this.addItem('eo')
                this.$message.success('添加成功！')
              }
            })
          } else if (this.dialogTitle === '编辑插件API入参') {
            modifyInputParaById(this.id, this.form).then(response => {
              if (response.errno === 0) {
                this.addItem('ei')
                this.$message.success('添加成功！')
              }
            })
          }
          this.$emit('update:parameterDialogVisible', false)
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
