<template>
  <div>
    <el-dialog
      :title="title"
      :visible="apiDialogVisible"
      width="50%"
      top="5vh"
      :before-close="handleClose"
    >
      <el-form ref="form" label-position="left" :model="form" :rules="rules" label-width="90px" size="small">
        <el-form-item prop="name" label="API名称">
          <el-input v-model="form.name" placeholder="请输入API名称" />
        </el-form-item>
        <el-form-item prop="code" label="API code">
          <el-input v-model="form.code" placeholder="请输入API code" />
        </el-form-item>
        <el-form-item prop="URL" label="请求URL">
          <el-input v-model="form.URL" placeholder="请输入请求URL" />
        </el-form-item>
        <el-form-item prop="format" label="请求方式">
          <el-select v-model="form.format" placeholder="请选择请求方式" />
        </el-form-item>
      </el-form>
      <div><strong>插件API入参定义：</strong></div>
      <el-table
        :data="inputParameterTableData"
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          label="字段名称"
        />
        <el-table-column
          prop="field"
          label="字段"
        />
        <el-table-column
          prop="type"
          label="类型"
        />
        <el-table-column
          prop="required"
          label="是否必填"
        />
        <el-table-column
          prop="description"
          label="描述"
        />
        <el-table-column
          prop="operation"
          label="操作"
        />
      </el-table>
      <el-row type="flex" align="middle" justify="center">
        <el-button type="text" @click="openParameterDialog('添加插件API入参')">添加入参字段</el-button>
      </el-row>
      <div><strong>插件API出参定义：</strong></div>
      <el-table
        :data="outputParameterTableData"
        :empty-text="emptyText"
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          label="字段名称"
        />
        <el-table-column
          prop="field"
          label="字段"
        />
        <el-table-column
          prop="type"
          label="类型"
        />
        <el-table-column
          prop="required"
          label="是否必填"
        />
        <el-table-column
          prop="description"
          label="描述"
        />
        <el-table-column
          prop="operation"
          label="操作"
        />
      </el-table>
      <el-row type="flex" align="middle" justify="center">
        <el-button type="text" @click="openParameterDialog('添加插件API出参')">添加出参字段</el-button>
      </el-row>
      <span slot="footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
    <Parameter :parameter-dialog-visible.sync="parameterDialogVisible" :dialog-title.sync="dialogTitle" />
  </div>
</template>

<script>
import Parameter from '@/views/appInfoManagement/appServiceAPI/components/Parameter'

export default {
  name: 'API',
  components: { Parameter },
  props: {
    apiDialogVisible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      parameterDialogVisible: false,
      dialogTitle: '',
      emptyText: '暂无数据',
      form: {
        name: '',
        code: '',
        URL: '',
        format: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入API名称', trigger: 'blur' }

        ],
        code: [
          { required: true, message: '请输入API code', trigger: 'blur' }
        ],
        URL: [
          { required: true, message: '请输入请求URL', trigger: 'blur' }
        ],
        format: [
          { required: true, message: '请选择请求方式', trigger: 'change' }
        ]
      },
      inputParameterTableData: [
        {
          name: '',
          code: '',
          field: '',
          type: '',
          required: '',
          description: '',
          operation: ''
        }
      ],
      outputParameterTableData: [
        {
          name: ' ',
          code: ' ',
          field: ' ',
          type: ' ',
          required: ' ',
          description: ' ',
          operation: ' '
        }
      ]
    }
  },
  methods: {
    handleClose() {
      this.$refs.form.resetFields()
      this.$emit('update:apiDialogVisible', false)
    },
    cancel() {
      this.$refs.form.resetFields()
      this.$emit('update:apiDialogVisible', false)
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$message.success('提交成功！')
          this.$refs.form.resetFields()
          this.$emit('update:apiDialogVisible', false)
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    },
    openParameterDialog(title) {
      this.dialogTitle = title
      this.parameterDialogVisible = true
    }
  }
}
</script>

<style scoped>

</style>
