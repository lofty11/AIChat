<template>
  <!--    新增插件-->
  <el-dialog :visible="configPlugDialogVisible" title="编辑插件" append-to-body @close="close">
    <el-form ref="configPlug">

      <el-form-item>
        插件参数配置:
        <el-table
          :data="plugParamTable"
          border
          style="width: 100%;background-color:grey"
          empty-text="暂无数据"
        >
          <el-table-column
            prop="name"
            label="参数名"
          />
          <el-table-column
            prop="value"
            label="参数值"
          />
          <el-table-column
            prop="operation"
            label="操作"
          /></el-table>
        <el-form-item />
        <el-form-item>
          <el-row type="flex" align="middle" justify="center">
            <el-button type="text" @click="configPlugParamDialog">添加参数</el-button>
          </el-row>
        </el-form-item>
        用户参数定义：
        <el-form-item>
          <el-table
            :data="userParamTable"
            border
            style="width: 100%;background-color:grey"
            empty-text="暂无数据"
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
              prop="necessary"
              label="是否必填"
            />
            <el-table-column
              prop="description"
              label="描述"
            /><el-table-column
              prop="operation"
              label="操作"
            />
          </el-table>
        </el-form-item>
        <el-form-item>
          <el-row type="flex" align="middle" justify="center">
            <el-button type="text" @click="configUserParamDialog">添加参数</el-button>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col span="10">
              <el-button type="primary" @click="close">关闭</el-button>
              <el-button type="primary" @click="submitPlugInfo">确认</el-button>
            </el-col>
          </el-row>
        </el-form-item></el-form-item></el-form>
    <!--    添加插件参数配置-->
    <config-plug-param ref="configPlugParam" :config-plug-param-dialog-visible.sync="configPlugParamDialogVisible" />
    <!--    添加插件参数配置-->
    <!--    添加用户参数配置-->
    <config-user-param ref="configUserParam" :config-user-param-dialog-visible.sync="configUserParamDialogVisible" />
    <!--    添加用户参数配置-->
  </el-dialog>
  <!--    新增插件-->
</template>
<script>

import ConfigPlugParam from '@/views/appInfoManagement/plugManagement/components/configPlugParam.vue'
import ConfigUserParam from '@/views/appInfoManagement/plugManagement/components/configUserParam.vue'

export default {
  components: { ConfigUserParam, ConfigPlugParam },
  props: {
    configPlugDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      configPlugParamDialogVisible: false,
      configUserParamDialogVisible: false,
      plugParamTable: {
        name: '',
        value: '',
        operation: ''
      },
      userParamTable: {
        name: '',
        field: '',
        type: '',
        necessary: false,
        description: '',
        operation: ''
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:configPlugDialogVisible', false)
    },

    submitPlugInfo() {
      // 在这里处理提交逻辑
      // 可以发送请求或执行其他操作
      this.$message.success('配置插件成功')
      // 提交完成后关闭对话框
      this.configPlugDialogVisible = false
    },
    configPlugParamDialog() {
      this.configPlugParamDialogVisible = true
    },
    configUserParamDialog() {
      this.configUserParamDialogVisible = true
    }

  }
}
</script>
