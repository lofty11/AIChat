<template>
  <!--    新增插件-->
  <el-dialog :visible="addPlugDialogVisible" title="新建插件" @close="close">
    <el-form>
      <el-form-item label="插件名称">
        <!--        <el-input v-model="plugInfo.name" />-->
        <el-input style="width:80%" />
      </el-form-item>

      <el-form-item label="插件用途">
        <!--        <el-input v-model="plugInfo.usage" />-->
        <el-input style="width:80%" />

      </el-form-item>

      <el-form-item label="插件描述">
        <!--        <el-input v-model="plugInfo.description" type="textarea" maxlength="255" show-word-limit />-->
        <el-input style="width:80%" type="textarea" :rows="4" maxlength="255" show-word-limit />
      </el-form-item>
      <el-alert
        title="此描述将作为模型识别、规划和调度插件任务的重要参考，请尽量详尽描述此插件各项细节，包括但不限于插件可用功能、应用场景等。"
        type="warning"
      />
      插件参数配置：
      <el-form-item>
        <el-row>
          <el-col span="10">
            <el-button type="primary" @click="submitPlugInfo">关闭</el-button>
            <el-button type="primary" @click="submitPlugInfo">确认</el-button>
          </el-col>
        </el-row>

      </el-form-item>
      <el-form-item />
    </el-form>
  </el-dialog>
  <!--    新增插件-->
</template>
<script>
export default {
  props: {
    addPlugDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    close() {
      this.$emit('update:addPlugDialogVisible', false)
    }
  }
}
</script>
