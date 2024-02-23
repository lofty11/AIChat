<template>
  <el-dialog :visible="configFuncDialogVisible" title="配置函数" @close="close">
    <el-form>
      <el-steps :active="active" finish-status="success" simple style="margin-top: 20px">
        <el-step title="1.任务基础信息" />
        <el-step title="2.结果渲染" />
      </el-steps>

      <el-form v-if="active==0" ref="updateFunc" :model="funcInfo" :rules="rules" align="right">
        <!-- 表单内容 -->
        <el-form-item prop="name" label="函数名称">
          <el-input v-model="funcInfo.name" style="width: 75%" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item prop="enName" label="函数名称（英）">
          <el-input v-model="funcInfo.enName" style="width: 75%" placeholder="请输入函数名称" />
        </el-form-item>
        <el-form-item prop="typeId" label="函数类型">
          <el-select v-model="funcInfo.typeId" style="width: 75%" placeholder="请选择函数类型">
            <el-option v-for="item in typeList" :key="item.id" :label="item.type" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item prop="apiId" label="服务API">
          <el-select v-model="funcInfo.apiId" style="width: 75%" placeholder="请选择服务API">
            <el-option v-for="item in apiList" :key="item.id" :label="item.api" :value="item.id" />
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
      </el-form>
      <el-form v-if="active==1">
        内容排布设定：

      </el-form>
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button v-if="active==0" type="primary" @click="next">下一步</el-button>
            <el-button v-if="active==1" type="primary" @click="last">上一步</el-button>
            <el-button v-if="active==1" type="primary" @click="submitConfigFuncInfo">提交</el-button>
          </el-col>
        </el-row>
      </el-form-item>

    </el-form>
  </el-dialog>
</template>
<script>
export default {
  props: {
    configFuncDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      active: 0,
      funcInfo: {
        id: '',
        name: '',
        enName: '',
        typeId: '',
        apiId: '',
        description: ''
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:configFuncDialogVisible', false)
    },
    next() {
      if (this.active++ >= 1) this.active = 0
    },
    last() {
      if (this.active-- <= 0) this.active = 0
    },
    async getFuncDetail(data) {
      // 等以后api能搞定了再换成这个
      // this.funcInfo = await getFuncDetail(this.currentId)
      this.funcInfo = data
    },
    submitConfigFuncInfo() {
      this.close()
    }
  }
}
</script>
