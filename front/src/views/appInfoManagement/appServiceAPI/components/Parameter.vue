<template>
  <div>
    <el-dialog
      :title.sync="dialogTitle"
      :visible="parameterDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form ref="form" label-position="left" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字段名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="字段" prop="field">
          <el-input v-model="form.field" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="区域一" value="shanghai" />
            <el-option label="区域二" value="beijing" />
          </el-select>
        </el-form-item>
        <el-form-item label="枚举范围" prop="range">
          <el-input v-model="form.range" />
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
    }
  },
  data() {
    return {
      form: {
        name: '',
        field: '',
        type: '',
        range: '',
        required: false,
        description: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入字段名称', trigger: 'blur' }

        ],
        field: [
          { required: true, message: '请输入字段', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        required: [
          { required: true, message: '请选择是否必填', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    handleClose() {
      this.$refs.form.resetFields()
      this.$emit('update:parameterDialogVisible', false)
    },
    cancel() {
      console.log(this.$refs.form.model)
      this.$refs.form.resetFields()
      this.$emit('update:parameterDialogVisible', false)
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$message.success('添加成功！')
          this.$refs.form.resetFields()
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
