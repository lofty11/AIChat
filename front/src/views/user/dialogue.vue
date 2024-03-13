<template>
  <div v-if="dialogVisible" class="chat-messages" style="width: 100%">
    <el-timeline>
      <el-timeline-item v-for="(message, index) in dialogueMessages" :key="index">
        <el-card v-if="message.type === 'user'" class="user-message" :header="message.sender">
          {{ message.text }}
        </el-card>
        <el-card v-else class="bot-message" :header="message.sender">
          {{ message.text }}
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>

</template>

<script>
export default {
  name: 'Dialogue',
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    dialogueMessages: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {}
  }

}
</script>

<style scoped>
.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.user-message {
  background-color: #f0f0f0;
}

.bot-message {
  background-color: #909399;
}

</style>
