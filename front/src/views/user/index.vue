<template>
  <el-container style="height: 100%">
    <el-col :span="1">
      <div class="logo-container">
        <img src="@/icons/png/chatLogo.png" alt="Logo" class="logo">
      </div>
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="height: 84%"
        collapse
      >
        <el-menu-item index="1">
          <i class="el-icon-chat-dot-round" />
          <span slot="title">对话</span>
        </el-menu-item>

      </el-menu>
      <div class="logo-container">
        <el-popover
          v-model="visible"
          placement="top"
          width="160"
        >
          <p><strong>{{ userInfo.name }}</strong></p>
          <div><p>电话：{{ userInfo.tel }}</p></div>
          <div style="text-align: center; margin: 0">
            <el-button type="info" size="mini" @click="visible = false">退出登录</el-button>
          </div>
          <img slot="reference" src="@/icons/png/userLogo.png" alt="Logo" class="logo">
        </el-popover>
      </div>
    </el-col>

    <el-col :span="22">
      <Chat />
    </el-col>

  </el-container>
</template>

<script>
import Chat from '@/views/user/chat'
export default {
  name: 'User',
  components: { Chat },
  data() {
    return {
      userInfo: {
        name: '李天文',
        tel: '15200000000'
      },
      visible: false
    }
  }
}
</script>

<style scoped>
.logo-container {
  height: 8%;
  background-color: #545c64;
  text-align: center;
}
.logo{
  width: 50%;
  height: 50%;
  margin: 20% ;
}
</style>
