<template>
  <div>
    <el-dialog :visible="updateFuncDialogVisible" title="编辑函数" @close="close">
      <el-form ref="funcForm" :model="funcForm" :rules="rules" align="right">
        <!-- 表单内容 -->
        <el-form-item prop="name" label="函数名称">
          <el-input v-model="funcForm.name" style="width: 75%" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item prop="enName" label="函数名称（英）">
          <el-input v-model="funcForm.ename" style="width: 75%" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item prop="type" label="函数类型">
          <el-select v-model="funcForm.type" style="width: 75%" placeholder="请选择函数类型">
            <el-option label="类型一" value="1" />
            <el-option label="类型二" value="2" />
            <el-option label="类型三" value="3" />
          </el-select>
        </el-form-item>

        <el-form-item prop="api" label="服务API">
          <el-select v-model="funcForm.api" style="width: 75%" placeholder="请选择服务API">
            <el-option label="api1" value="1" />
            <el-option label="api2" value="2" />
            <el-option label="api3" value="3" />
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
    <Parameter :func-id.sync="funcId" />
  </div>

</template>
<script>

import { getFuncById, modifyFuncById } from '@/api/plug'
export default {

  props: {
    updateFuncDialogVisible: {
      type: Boolean,
      default: false
    },
    funcId: {
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      currentId: null,
      funcForm: {
        id: '',
        name: '',
        ename: '',
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
          enName: '',
          description: ''
        }
      } else {
        // console.log(newValue)
        getFuncById(newValue).then(response => {
          this.funcForm = response.data
          // console.log(response.data)
        })
      }
    }
  },
  methods: {
    confirm() {
      this.$refs.funcForm.validate((valid) => {
        if (valid) {
          modifyFuncById(this.funcId, this.funcForm).then((response) => {
            if (response.errono === 0) {
              this.$message.success('编辑函数成功')
            }
            this.updateFuncDialogVisible = false
          })
        } else {
          this.$message.error('请将函数填写完整')
          return false
        }
      }
      )
    },
    close() {
      this.$emit('update:updateFuncDialogVisible', false)
    }
  }
}
</script>
