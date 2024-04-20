<template>
  <div v-if="dialogVisible" ref="messageContainer" class="chat-messages">
    <el-timeline ref="timeline">
      <el-timeline-item v-for="(message, index) in messages" :key="index">

        <el-card v-if="message.type === 0" class="user-message" header="YOU">
          <div v-html=" renderMarkdown(message.content)" />
        </el-card>
        <el-card v-else class="bot-message" header="AI精灵">
          <div v-html=" renderMarkdown(message.content)" />
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>

</template>

<script>
import { getAllMessages } from '@/api/chat'
import { md } from './markdown'

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
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      messages: {},
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
      if (this.messages === undefined) {
        this.messages = newValue
      }
      this.messages.push(newValue)
    }
  },
  methods: {
    renderMarkdown(markdownText) {
      return md.render(markdownText)
    }
  }
}
</script>

<style scoped >
.chat-messages {
  flex: 1;
  overflow-y: auto;

}

.user-message {

pre.hljs {
  font-family: -apple-system, "Noto Sans", "Helvetica Neue", Helvetica,
  "Nimbus Sans L", Arial, "Liberation Sans", "PingFang SC", "Hiragino Sans GB",
  "Noto Sans CJK SC", "Source Han Sans SC", "Source Han Sans CN",
  "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti",
  SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  background: black; /* 修改代码块背景色为黑色 */
  color: white; /* 修改代码块文本颜色为白色 */
  border-radius: 5px; /* 添加代码块圆角 */
  padding: 10px; /* 添加内边距 */
}
background-color: #f0f0f0
}

.bot-message {
  background-color: #909399;
}

</style>
