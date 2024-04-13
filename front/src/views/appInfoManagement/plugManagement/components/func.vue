<template>
  <el-dialog ref="func" :visible="funcDialogVisible" :title="dialogTitle" @close="close">
    <el-form ref="funcForm" :model="funcForm" :rules="rules" align="right">
      <!-- 表单内容 -->
      <el-form-item prop="name" label="函数名称">
        <el-input v-model="funcForm.name" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="eName" label="函数名称（英）">
        <el-input v-model="funcForm.ename" style="width: 75%" placeholder="请输入函数名称" />
      </el-form-item>
      <el-form-item prop="type" label="函数类型">
        <el-select v-model="funcForm.type" style="width: 75%" placeholder="请选择函数类型">
          <el-option v-for="item in typeList" :key="item.id" :label="item.type" :value="item.id" />
        </el-select>
      </el-form-item>

      <el-form-item prop="api" label="服务API">
        <el-select v-model="funcForm.api" style="width: 75%" placeholder="请选择服务API">
          <el-option v-for="item in apiList" :key="item.id" :label="item.type" :value="item.id" />
        </el-select>
      </el-form-item>

      <el-form-item prop="description" label="函数描述">
        <el-input
          v-model="funcForm.description"
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
import { createFunc, getFuncById, getFuncTypes, getServiceApis, modifyFuncById } from '@/api/plug'
export default {
  props: {
    funcDialogVisible: {
      type: Boolean,
      default: false
    },
    funcId: {
      type: String,
      default: ''
    },
    dialogTitle: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      // 样例数据，后续等数据库完善，使用api提供的
      typeList: [],
      apiList: [],
      funcForm: {
        id: '0',
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
  watch: {
    funcId(newValue) {
      if (newValue === '0') {
        this.funcForm = {
          name: '',
          ename: '',
          type: '',
          typeName: '',
          api: '',
          apiName: '',
          description: ''
        }
      } else {
        getFuncById(newValue).then(response => {
          this.funcForm = response.data
        })
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
      this.$emit('update:funcDialogVisible', false)
    },
    confirm() {
      console.log('confirm')
      this.$refs.funcForm.validate((valid) => {
        if (valid) {
          if (this.funcId === '0') {
            createFunc(this.funcForm).then(response => {
              if (response.errno === 1) {
                this.$message.success('新增函数成功！')
                this.$emit('update:funcDialogVisible', false)
              } else {
                this.$message.error('新增函数失败')
              }
            })
          } else {
            modifyFuncById(this.funcId, this.funcForm).then((response) => {
              console.log('编辑函数')
              if (response.errno === 0) {
                this.$message.success('编辑函数成功')
                this.$emit('update:funcDialogVisible', false)
              } else {
                this.$message.error('编辑函数失败')
              }
            })
          }
        } else {
          this.$message.error('请将表单填写完整！')
          return false
        }
      })
    }
  }
}
</script>
