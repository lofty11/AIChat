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
          <el-form-item prop="apiName" label="API名称">
            <el-input v-model="form.apiName" placeholder="请输入API名称" />
          </el-form-item>
          <el-form-item prop="apiCode" label="API code">
            <el-input v-model="form.apiCode" placeholder="请输入API code" />
          </el-form-item>
          <el-form-item prop="requestUrl" label="请求URL">
            <el-input v-model="form.requestUrl" placeholder="请输入请求URL" />
          </el-form-item>
          <el-form-item prop="requestMethod" label="请求方式">
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
          :data="form.extensionInput"
          style="width: 100%"
        >
          <el-table-column
            prop="fieldName"
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
            <template v-slot="scope">
              <el-button type="text" size="small" @click="openParameterDialog('编辑插件API入参',scope.$index,scope.row.id)">编辑</el-button>
              <el-button type="text" size="small" style="color:red" @click="deleteButton('i',scope.$index,scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row type="flex" align="middle" justify="center">
          <el-button type="text" @click="openParameterDialog('添加插件API入参','0')">添加入参字段</el-button>
        </el-row>
        <div><strong>插件API出参定义：</strong></div>
        <el-table
          :data="form.extensionOutput"
          :empty-text="emptyText"
          style="width: 100%"
        >
          <el-table-column
            prop="fieldName"
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
            <template v-slot="scope">
              <el-button type="text" size="small" @click="openParameterDialog('编辑插件API出参',scope.$index,scope.row.id)">编辑</el-button>
              <el-button type="text" size="small" style="color:red" @click="deleteButton('o',scope.$index,scope.row.id)">删除</el-button>
            </template>
          </el-table-column>

        </el-table>
        <el-row type="flex" align="middle" justify="center">
          <el-button type="text" @click="openParameterDialog('添加插件API出参',0,'0')">添加出参字段</el-button>
        </el-row>
      </div>

    </el-dialog>
    <Parameter :id.sync="paraId" :parameter-dialog-visible.sync="parameterDialogVisible" :dialog-title.sync="dialogTitle" :api-id.sync="id" @add-item="addItemToList" />
  </div>
</template>

<script>
import Parameter from '@/views/appInfoManagement/appServiceAPI/components/parameter'
import { createAPI, deleteInputParaById, deleteOutputParaById, getApiById, modifyApiById } from '@/api/api'

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
    },
    id: {
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      paraIndex: 0,
      parameterDialogVisible: false,
      paraId: '0',
      dialogTitle: '',
      emptyText: '暂无数据',
      form: {
        apiName: '',
        apiCode: '',
        requestUrl: '',
        requestMethod: '',
        extensionOutput: [{
          fieldName: '',
          field: '',
          id: '',
          type: '',
          require: '',
          description: '',
          operation: ''
        }],
        extensionInput: [{
          fieldName: '',
          field: '',
          id: '',
          type: '',
          require: '',
          description: '',
          operation: ''
        }]
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
          { required: true, message: '请选择请求方式', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    // 监听 apiData 数据的变化
    id(newValue) {
      if (newValue === '0') {
        this.form = {
          apiName: '',
          apiCode: '',
          requestUrl: '',
          requestMethod: ''
        }
      } else {
        getApiById(newValue).then(response => {
          console.log(response.data)
          this.form = response.data
        })
      }
    },
    apiDialogVisible(newValue) {
      if (newValue === true && this.id === '0') {
        if (this.$refs.form !== undefined) {
          this.$refs.form.resetFields()
        }
      }
    }

  },
  methods: {
    addItemToList(data, type) {
      if (type === 'i') {
        this.form.extensionInput.push(data)
      } else if (type === 'o') {
        this.form.extensionOutput.push(data)
      } else if (type === 'ei') {
        this.form.extensionInput.splice(this.paraIndex, 1, data)
      } else if (type === 'eo') {
        this.form.extensionOutput.splice(this.paraIndex, 1, data)
      }
    },
    handleClose() {
      this.$emit('update:apiDialogVisible', false)
    },
    cancel() {
      this.$emit('update:apiDialogVisible', false)
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.id === '0') {
            createAPI(this.form).then((response) => {
              if (response.errno === 1) { this.$message.success('提交成功！') }
              this.$emit('update:apiDialogVisible', false)
            })
          } else {
            modifyApiById(this.id, this.form).then((response) => {
              if (response.errno === 0) { this.$message.success('修改成功！') }
              this.$emit('update:apiDialogVisible', false)
            })
          }
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    },
    openParameterDialog(title, index, id) {
      this.dialogTitle = title
      this.parameterDialogVisible = true
      this.paraId = id.toString()
      this.paraIndex = index
    },
    deleteButton(type, index, id) {
      this.paraId = id.toString()
      this.$confirm('是否确认删除该参数?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (type === 'i') {
          deleteInputParaById(this.paraId).then(response => {
            if (response.errno === 0) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.form.extensionInput.splice(index, 1)
            }
          })
        } else if (type === 'o') {
          deleteOutputParaById(this.paraId).then(response => {
            if (response.errno === 0) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.form.extensionOutput.slice(index, 1)
            }
          })
        }
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
