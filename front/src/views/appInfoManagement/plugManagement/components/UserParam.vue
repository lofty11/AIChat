<template>
  <div>
    <el-dialog :visible="userParamDialogVisible" :title="dialogTitle" append-to-body @close="close">
      <el-form ref="configUserParam" :model="paramTable" :rules="rules" align="right">
        <!-- 表单内容 -->
        <el-form-item prop="name" label="字段名称">
          <el-input v-model="paramTable.name" style="width: 75%" />
        </el-form-item>

        <el-form-item prop="field" label="字段">
          <el-input v-model="paramTable.field" style="width: 75%" />
        </el-form-item>
        <el-form-item prop="type" label="类型">
          <el-select v-model="paramTable.type" style="width: 75%" placeholder="请选择类型">
            <el-option label="类型一" value="type01" />
            <el-option label="类型二" value="type02" />
            <el-option label="类型三" value="type03" />
          </el-select>
        </el-form-item>
        <el-form-item prop="enumerationRange" label="枚举范围">
          <el-input v-model="paramTable.enumerationRange" style="width: 75%" />
        </el-form-item>
        <el-form-item prop="necessary" label="是否必填" align="left">
          <el-switch v-model="paramTable.necessary" />
        </el-form-item>
        <el-form-item prop="description" label="描述">
          <el-input v-model="paramTable.description" style="width: 75%" />
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col span="10">
              <el-button type="primary" @click="close">关闭</el-button>
              <el-button type="primary" @click="submitUserParam">确认</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>
<script>

import { getUserParaById } from '@/api/plug'

export default {
  props: {
    userParamDialogVisible: {
      type: Boolean,
      default: false
    },
    dialogTitle: {
      type: String,
      default: ''
    },
    userParaId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      typeList: [],
      paramTable: [{
        name: '',
        field: '',
        type: '',
        enumerationRange: '',
        necessary: false,
        description: ''
      }],
      rules: {
        name: [{ required: true, message: '字段名称不能为空', trigger: 'blur' }],
        field: [{ required: true, message: '字段不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '类型不能为空', trigger: 'blur' }],
        necessary: [{ required: true, message: '类型不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    userParaId(newValue) {
      if (newValue === '0') {
        this.paramTable = [
          {
            name: '',
            field: '',
            type: '',
            range: '',
            necessary: false,
            description: ''
          }
        ]
      } else {
        getUserParaById(newValue).then(response => {
          this.paramTable = response.data
          console.log(response.data)
          this.$set(this.paramTable, 'necessary', Boolean(response.data.necessary))
        })
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:userParamDialogVisible', false)
    },
    submitUserParam() {
      this.$message.success('成功添加用户定义参数')
      this.userParamDialogVisible = false
    }
  }
}
</script>
