<template>
  <el-dialog :visible="addFuncDialogVisible" title="创建函数" @close="close">
    <el-form ref="addFunc" :model="funcInfo" :rules="rules" align="right">
      <!-- 表单内容 -->
      <el-form-item prop="name" label="函数名称">
        <el-input v-model="funcInfo.name" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="eName" label="函数名称（英）">
        <el-input v-model="funcInfo.ename" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="type" label="函数类型">
        <el-select v-model="funcInfo.type" style="width: 75%" placeholder="请选择函数类型">
          <el-option v-for="item in typeList" :key="item.id" :label="item.type" :value="item.id" />
        </el-select>
      </el-form-item>

      <el-form-item prop="api" label="服务API">
        <el-select v-model="funcInfo.api" style="width: 75%" placeholder="请选择服务API">
          <el-option v-for="item in apiList" :key="item.id" :label="item.type" :value="item.id" />
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
            <el-button type="primary" @click="confirm">确认</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
import { createFunc, getFuncTypes, getServiceApis } from '@/api/plug'
export default {
  props: {
    addFuncDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 样例数据，后续等数据库完善，使用api提供的
      typeList: [],
      apiList: [],
      funcInfo: {
        name: '',
        ename: '',
        type: '',
        typeName: '',
        api: '',
        apiName: '',
        description: ''
      },
      form: {
        name: '',
        ename: '',
        type: '',
        typeName: '',
        api: '',
        apiName: '',
        description: ''
      },
      rules: {
        name: [{ required: true, message: '函数名称不能为空', trigger: 'blur' }],
        ename: [{ required: true, message: '函数英文名称不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '函数类型不能为空', trigger: 'blur' }],
        api: [{ required: true, message: '服务API不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '函数描述不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    getFuncTypes().then((response) => {
      this.typeList = response.data
    })
    getServiceApis().then((response) => {
      this.apiList = response.data
    })
  },
  methods: {
    addItem(dataType) {
      this.$emit('add-item', JSON.parse(JSON.stringify(this.form)), dataType)
    },
    close() {
      this.$emit('update:addFuncDialogVisible', false)
    },
    trans() {
      this.form.name = this.funcInfo.name
      this.form.ename = this.funcInfo.ename
      this.form.description = this.funcInfo.description
      this.form.type = this.typeList[this.funcInfo.type].id
      this.form.typeName = this.typeList[this.funcInfo.type].type
      this.form.api = this.apiList[this.funcInfo.api].id
      this.form.apiName = this.apiList[this.funcInfo.api].type
    },
    confirm() {
      this.$refs.addFunc.validate((valid) => {
        if (valid) {
          this.trans()
          createFunc(this.form).then(response => {
            if (response.errno === 1) { this.$message.success('新增函数成功！') } else {
              this.$message.error('新增函数失败')
            }
          })
          this.$refs.addFunc.resetFields()
          this.$emit('update:addFuncDialogVisible', false)
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    }
  }
}
</script>
