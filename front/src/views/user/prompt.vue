<template>
  <div>
    <div v-if="promptVisible" class="prompt-container">
      <div class="logo-container">
        <img src="@/icons/png/chatLogo.png" alt="Company Logo" class="company-logo">
      </div>
      <div class="company-info">
        <h1 class="company-name">寻知AI精灵</h1>
        <p class="company-slogan">聚焦知识智能化·激活无限可能</p>
      </div>
      <div class="spacer" />
      <el-row :gutter="20" type="flex" justify="center" align="middle">
        <el-col :span="8">
          <p><i class="el-icon-sunny" /> 你可以这样向我提问</p>
          <el-card :body-style="{ color:'#606266', height:'100px',padding: '30px',background:'#F2F6FC' }">
            "企业如何配合海关检查"
          </el-card>
        </el-col>
        <el-col :span="8">
          <p><i class="el-icon-sunny" />获得更好的答案</p>
          <el-card :body-style="{ color:'#606266',height:'100px', padding: '30px', background:'#F2F6FC'}">
            "泰国入境建议严查对生鲜水果有什么要求"
          </el-card>
        </el-col>
        <el-col :span="8">
          <p><i class="el-icon-sunny" />提出复杂的问题</p>
          <el-card :body-style="{color:'#606266', height:'100px',padding: '30px',background:'#F2F6FC'}">
            "进出境旅客行李物品若属于应向海关申报的，具体如何申报？"
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Prompt',
  props: {
    promptVisible: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.prompt-container {
  text-align: center;
  margin-top: 10%;

}

.logo-container {
  margin-bottom: 1%;
}

.company-logo {
  width: 5%;
  height: 5%;
}

.company-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.company-name {
  margin: 0;
  font-size: 36px;
}

.company-slogan {
  margin: 1%;
  font-size: 18px;
}
.spacer {
  margin-top: 6%;
}
</style>
