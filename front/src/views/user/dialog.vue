<template>
  <div v-if="dialogVisible" ref="messageContainer" class="chat-messages" style="width: 100%" @scroll="handleScroll">
    <el-timeline>
      <el-timeline-item v-for="(message, index) in messages" :key="index">
        <el-card v-if="message.type === 0" class="user-message" header="YOU">
          {{ message.content }}
        </el-card>
        <el-card v-else class="bot-message" header="AI精灵">
          {{ message.content }}
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>

</template>

<script>
import { getAllMessages } from '@/api/chat'
export default {
  name: 'Dialog',
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    dialogMessage: {
      type: Object,
      default: () => ({})
    },
    id: {
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      messages: [],
      pageSize: 10, // 每次加载的项目数量
      page: 1 // 当前页码
    }
  },
  watch: {
    id(newValue) {
      if (newValue !== '0') {
        getAllMessages(this.id, { paga: 1, pageSize: 10 }).then(response => {
          if (response.errno === 0) {
            this.messages = response.data.list
            this.page = response.data.page
            this.pageSize = response.data.pageSize
          }
        })
      }
    },
    dialogMessage(newValue) {
      this.messages.push(newValue)
    }
  },
  mounted() {
    // window.addEventListener('scroll', this.handleScroll, true)
  },
  destroyed() {
    // window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    handleScroll() {
      // 滚动过的距离
      const container = this.$refs.messageContainer

      // 判断是否滚动到了底部
      if (container.scrollTop + container.clientHeight >= container.scrollHeight) {
        console.log('滚动')
        getAllMessages(this.id, { paga: this.page + 1, pageSize: this.pageSize }).then(response => {
          if (response.errno === 0) {
            this.messages.push(...response.data.list)
            this.page = response.page
            this.pageSize = response.pageSize
          }
        })
      }
      // var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      // 当前可视区的高度
      // var clientHeight = document.documentElement.clientHeight
      // 滚动条的长度
      // var scrollHeight = document.documentElement.scrollHeight
      // 当滚动过的距离+可视区的高度>=滚动条长度时，就相当于滚动到了底部
    }
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
