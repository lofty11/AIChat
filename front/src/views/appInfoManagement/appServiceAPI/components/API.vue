<template>
  <div>
    <el-dialog
      :title="title"
      :visible="apiDialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <div v-if="formVisible">
        <el-form ref="form" label-position="left" :model="form" :rules="rules" label-width="90px" size="small">
          <el-form-item prop="name" label="API名称">
            <el-input v-model="form.apiName" placeholder="请输入API名称" />
          </el-form-item>
          <el-form-item prop="code" label="API code">
            <el-input v-model="form.apiCode" placeholder="请输入API code" />
          </el-form-item>
          <el-form-item prop="URL" label="请求URL">
            <el-input v-model="form.requestUrl" placeholder="请输入请求URL" />
          </el-form-item>
          <el-form-item prop="format" label="请求方式">
            <el-select v-model="form.requestMethod" placeholder="请选择请求方式">
              <el-option
                label="GET"
                :value="1"
              />
              <el-option
                label="POST"
                :value="2"
              />
              <el-option
                label="PUT"
                :value="3"
              />
              <el-option
                label="DELETE"
                :value="4"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="confirm">确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-if="parameterVisible">
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
          >
            <el-button type="text" size="small" @click="openParameterDialog('编辑插件API入参')">编辑</el-button>
            <el-button type="text" size="small" style="color:red" @click="deleteButton">删除</el-button>
          </el-table-column>
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
          >
            <template>
              <el-button type="text" size="small" @click="openParameterDialog('编辑插件API出参')">编辑</el-button>
              <el-button type="text" size="small" style="color:red" @click="deleteButton">删除</el-button>
            </template>
          </el-table-column>

        </el-table>
        <el-row type="flex" align="middle" justify="center">
          <el-button type="text" @click="openParameterDialog('添加插件API出参')">添加出参字段</el-button>
        </el-row>
      </div>

    </el-dialog>
    <Parameter :parameter-dialog-visible.sync="parameterDialogVisible" :dialog-title.sync="dialogTitle" />
  </div>
</template>

<script>
import Parameter from '@/views/appInfoManagement/appServiceAPI/components/parameter'
import { createAPI } from '@/api/api'

export default {
  name: 'API',
  components: { Parameter },
  props: {
    apiDialogVisible: {
      type: Boolean,
      default: false
    },
    parameterVisible: {
      type: Boolean,
      default: false
    },
    formVisible: {
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
        apiName: '',
        apiCode: '',
        requestUrl: '',
        requestMethod: ''
      },
      rules: {
        apiName: [
          { required: true, message: '请输入API名称', trigger: 'blur' }

        ],
        apiCode: [
          { required: true, message: '请输入API code', trigger: 'blur' }
        ],
        requestUrl: [
          { required: true, message: '请输入请求URL', trigger: 'blur' }
        ],
        requestMethod: [
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
      if (this.$refs.form !== undefined) {
        this.$refs.form.resetFields()
      }
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
          createAPI(this.form)

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
    },
    deleteButton() {
      this.$confirm('是否确认删除该参数?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
