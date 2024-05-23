<template>
  <el-dialog
    title="应用服务API详情"
    :visible="detailDialogVisible"
    width="50%"
    top="5vh"
    :before-close="handleClose"
  >
    <el-form ref="form" label-position="left" :model="list" label-width="90px" size="small">
      <el-row type="flex" justify="center" align="middle">
        <el-form-item prop="apiName" label="API名称：">
          {{ list.apiName }}
        </el-form-item>
      </el-row>
      <el-row type="flex" justify="center" align="middle">
        <el-form-item prop="apiCode" label="API code：">
          {{ list.apiCode }}
        </el-form-item>
      </el-row>
      <el-row type="flex" justify="center" align="middle">
        <el-form-item prop="requestUrl" label="请求URL：">
          {{ list.requestUrl }}
        </el-form-item>
      </el-row>
      <el-row type="flex" justify="center" align="middle">
        <el-form-item prop="requestMethod" label="请求方式：">
          {{ RequestMethodDisplay(list.requestMethod) }}
        </el-form-item>
      </el-row>
    </el-form>
    <p><strong>插件API入参定义：</strong></p>
    <el-table
      :data="list.extensionInput"
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
      >
        <template scope="scope">
          <!-- 根据 required 的值显示不同的内容 -->
          {{ scope.row.required === 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
      />

    </el-table>

    <p><strong>插件API出参定义：</strong></p>
    <el-table
      :data="list.extensionOutput"
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
      >
        <template scope="scope">
          <!-- 根据 required 的值显示不同的内容 -->
          {{ scope.row.required === 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
      />

    </el-table>
    <span slot="footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getApiById } from '@/api/api'

export default {
  name: 'Detail',
  props: {
    detailDialogVisible: {
      type: Boolean,
      default: false
    },
    id: {
      type: String
      // default: {
      //   apiName: '',
      //   apiCode: '',
      //   requestUrl: '',
      //   requestMethod: 0,
      //   extensionInput: [],
      //   extensionOutput: []
      // }
    }
  },
  data() {
    return {
      list: []
    }
  },
  watch: {
    detailDialogVisible(newValue) {
      if (newValue !== false && this.id !== 0) {
        getApiById(this.id).then(response => {
          if (response.errno === 0) {
            this.list = response.data
          }
        })
      }
    }

  },
  methods: {
    handleClose() {
      this.$emit('update:detailDialogVisible', false)
    },
    cancel() {
      this.$emit('update:detailDialogVisible', false)
    },
    confirm() {
      this.$emit('update:detailDialogVisible', false)
    },
    RequestMethodDisplay(requestMethod) {
      // 根据 requestMethod 的值返回相应的文字
      if (requestMethod === 1) {
        return 'GET'
      } else if (requestMethod === 2) {
        return 'POST'
      } else if (requestMethod === 3) {
        return 'PUT'
      } else if (requestMethod === 4) {
        return 'DELETE'
      }
      // 其他情况的处理
    }
  }
}
</script>

<style scoped>

</style>
