<template>
  <el-dialog
    title="创建应用服务API"
    :visible="addAPIDialogVisible"
    width="50%"
    top="0vh"
    :before-close="handleClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-form-item prop="name" label="API名称">
        <el-input v-model="form.name" placeholder="请输入API名称"/>
      </el-form-item>
      <el-form-item prop="code" label="API code">
        <el-input v-model="form.code" placeholder="请输入API code"/>
      </el-form-item>
      <el-form-item prop="URL" label="请求URL">
        <el-input v-model="form.URL" placeholder="请输入请求URL"/>
      </el-form-item>
      <el-form-item prop="format" label="请求方式">
        <el-select v-model="form.format" placeholder="请选择请求方式"/>
      </el-form-item>
    </el-form>
    <div>插件API入参定义：</div>
    <el-table
      :data="inputParameterTableData"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="字段名称">
      </el-table-column>
      <el-table-column
        prop="string"
        label="字段">
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型">
      </el-table-column>
      <el-table-column
        prop="required"
        label="是否必填">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作">
      </el-table-column>
    </el-table>
    <el-row type="flex" align="middle" justify="center">
      <el-button type="text">添加入参字段</el-button>
    </el-row>
    <div>插件API出参定义：</div>
    <el-table
      :data="outputParameterTableData"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="字段名称">
      </el-table-column>
      <el-table-column
        prop="string"
        label="字段">
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型">
      </el-table-column>
      <el-table-column
        prop="required"
        label="是否必填">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作">
      </el-table-column>
    </el-table>
    <el-row type="flex" align="middle" justify="center">
      <el-button type="text">添加出参字段</el-button>
    </el-row>
    <span slot="footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button type="primary" @click="confirm">确 定</el-button>
  </span>
  </el-dialog>

</template>

<script>
export default {
  name: 'AddAPI',
  props: {
    addAPIDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      form: {
        name: '',
        code: '',
        URL: '',
        format: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入API名称', trigger: 'blur'}

        ],
        code: [
          {required: true, message: '请输入API code', trigger: 'blur'}
        ],
        URL: [
          {required: true, message: '请输入请求URL', trigger: 'blur'}
        ],
        format: [
          {required: true, message: '请选择请求方式', trigger: 'change'}
        ]
      },
      inputParameterTableData: [
        {
          name: '',
          code: '',
          string: '',
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
          string: ' ',
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
      this.$emit('update:addAPIDialogVisible', false)
    },
    cancel() {
      this.$emit('update:addAPIDialogVisible', false)
    },
    confirm() {
      this.$emit('update:addAPIDialogVisible', false)
    }
  }
}
</script>

<style scoped>

</style>
