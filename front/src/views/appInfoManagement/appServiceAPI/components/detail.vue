<template>
  <el-dialog title="应用服务API详情"
             :visible="detailDialogVisible"
             width="50%"
             top="0vh"
             :before-close="handleClose">
    <el-form :label-position="labelPosition" ref="form" :model="form" label-width="90px">
      <el-form-item align="middle" prop="name" label="API名称：">
        {{ form.name }}
      </el-form-item>
      <el-form-item align="middle" prop="code" label="API code：">
        {{ form.code }}
      </el-form-item>
      <el-form-item align="middle" prop="URL" label="请求URL：">
        {{ form.URL }}
      </el-form-item>
      <el-form-item align="middle" prop="format" label="请求方式：">
        {{ form.format }}
      </el-form-item>
    </el-form>
    <div>插件API入参定义：</div>
    <el-table
      :data="inputParameterTableData"
      style="width: 100%"
    >
      <el-table-column
        prop="name"
        label="字段名称"
      />
      <el-table-column
        prop="string"
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

    <div>插件API出参定义：</div>
    <el-table
      :data="outputParameterTableData"
      style="width: 100%"
    >
      <el-table-column
        prop="name"
        label="字段名称"
      />
      <el-table-column
        prop="string"
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
    <span slot="footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "Detail",
  props: {
    detailDialogVisible: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      labelPosition: 'right',
      form: {
        name: 'SerpAPI',
        code: 'SerpAPI',
        URL: '/server/web-search',
        format: 'POST'
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
    handleClose(done) {
      this.$emit('update:detailDialogVisible', false)
    },
    cancel() {
      this.$emit('update:detailDialogVisible', false)
    },
    confirm() {
      this.$emit('update:detailDialogVisible', false)
    }
  }
}
</script>

<style scoped>

</style>
