<template>
  <el-dialog :visible="updateFuncDialogVisible" title="编辑函数" @close="close">
    <el-form ref="updateFunc" :model="funcInfo" :rules="rules" align="right">
      <!-- 表单内容 -->
      <el-form-item prop="name" label="函数名称">
        <el-input v-model="funcInfo.name" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="enName" label="函数名称（英）">
        <el-input v-model="funcInfo.enName" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="type" label="函数类型">
        <el-select v-model="funcInfo.type" style="width: 75%" placeholder="请选择函数类型">
          <el-option label="类型一" value="1" />
          <el-option label="类型二" value="2" />
          <el-option label="类型三" value="3" />
        </el-select>
      </el-form-item>

      <el-form-item prop="api" label="服务API">
        <el-select v-model="funcInfo.api" style="width: 75%" placeholder="请选择服务API">
          <el-option label="api1" value="1" />
          <el-option label="api2" value="2" />
          <el-option label="api3" value="3" />
        </el-select>
      </el-form-item>

      <el-form-item prop="description" label="函数描述">
        <el-input
          v-model="funcInfo.description"
          type="textarea"
          rows="4"
          style="width: 75%"
          maxlength="255"
          show-word-limit
          placeholder="请输入函数描述"
        />
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button type="primary" @click="close">关闭</el-button>
            <el-button type="primary" @click="updateFuncInfo">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>

import { updateFunc } from '@/api/plug'

export default {
  props: {
    updateFuncDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      currentId: null,
      funcInfo: {
        id: '',
        name: '',
        enName: '',
        description: ''
      },
      rules: {
        name: [{ required: true, message: '函数名称不能为空', trigger: 'blur' }],
        enName: [{ required: true, message: '函数英文名称不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '函数类型不能为空', trigger: 'blur' }],
        api: [{ required: true, message: '服务API不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '函数描述不能为空', trigger: 'blur' }]
      }
    }
  },

  methods: {
    updateFuncInfo() {
      console.log(this.funcInfo)
      this.$refs.updateFunc.validate(async isOk => {
        if (isOk) {
          await updateFunc({ ...this.funcInfo })
          this.$emit('updateFuncTable')
          this.$message.success('编辑函数成功')
        }
      })
      this.updateFuncDialogVisible = false
    },
    close() {
      this.$emit('update:updateFuncDialogVisible', false)
    },
    async getFuncDetail(data) {
      // 等以后api能搞定了再换成这个
      // this.funcInfo = await getFuncDetail(this.currentId)
      this.funcInfo = data
    }
  }
}
</script>
