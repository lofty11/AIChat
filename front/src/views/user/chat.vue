<template>
  <div class="chat-container">
    <el-container style=" width:100% ;height: 100%">
      <el-aside width="20%" style="height: 100%">

        <el-menu
          class="el-menu-vertical-demo"
          background-color="#545c59"
          text-color="#fff"
          active-text-color="#ffd04b"
          style="height: 100%;position: fixed;"
        >
          <el-menu-item>
            <el-button type="text" icon="el-icon-plus" style="color:#fff;" @click="addItem">新建对话</el-button>

          </el-menu-item>
          <el-menu-item v-for="(item, index) in sidebarItems" :key="index" :index="String(index + 1)" @click="selectItem(item.id)">
            <template slot="title">
              <span v-if="editingIndex !== index" style="width: 40%;">{{ truncateText(item.chatName) }}</span>
              <span v-else>
                <el-input v-model="editedItemName" size="mini" style="width: 40%;" autofocus @keyup.enter.native="saveItem(index,item.id)" />
              </span>
              <span class="button-group">
                <el-button type="text" icon="el-icon-edit" @click="editItem(index)" />
                <el-button type="text" icon="el-icon-delete" @click="deleteItem(index,item.id)" />
              </span>

            </template>

          </el-menu-item>

        </el-menu>

      </el-aside>
      <el-container style="width:100%;height: 100%">
        <el-main ref="main" width="100%" height="75%">
          <Prompt :prompt-visible="promptVisible" />
          <Dialog :id="chatId" :loading="loading" :dialog-visible="dialogVisible" :dialog-message="message" />
        </el-main>
        <el-footer height="25%" class="chat-input">
          <el-row type="flex" align="middle">
            <el-col :span="2">
              <strong>插件：</strong>
            </el-col>
            <el-col v-for="(plugin, index) in plugins" :key="index" :span="3">
              <el-tooltip placement="top" effect="light">
                <div slot="content"><strong>{{ plugin.name }}</strong><br>{{ plugin.description }}</div>
                <el-radio v-model="selectedPlugin" :label="plugin.id" :title="plugin.description">{{ plugin.name }}</el-radio>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row :gutter="20" type="flex" align="middle" justify="center">
            <el-col :span="23">
              <el-input
                v-model="userMessage"
                v-loading="loading"
                :rows="3"
                type="textarea"
                placeholder="有问题尽管问我..."
                clearable
                autofocus
              />
            </el-col>
            <el-col :span="1">
              <el-button type="info" plain icon="el-icon-s-promotion" :disabled="send" @click="sendMessage" />
            </el-col>

          </el-row>
          <div class="footer-info">
            <p> <a style="color:#409EFF;" href="https://www.baidu.com">用户协议</a> | <a style="color:#409EFF;" href="https://www.baidu.com">隐私声明</a> | <a href="/network-record">众数（厦门）信息科技有限公司©版权所有</a></p>
            <p>
              <a href="https://beian.miit.gov.cn">闽公网安备案35020302035896号</a> |
              <a href="https://beian.miit.gov.cn"> 闽ICP备2021002838号-2</a>
            </p>
          </div>

        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Prompt from '@/views/user/prompt'
import Dialog from '@/views/user/dialog'
import {
  createAiMessage,
  createChat,
  createImageMessage,
  createMessage,
  deleteChat,
  getAllChats,
  modifyChatName
} from '@/api/chat'
import { getAllPlug } from '@/api/plug'
export default {
  name: 'Chat',
  components: { Dialog, Prompt },
  data() {
    return {
      loading: false,
      plugins: [],
      selectedPlugin: '',
      sidebarItems: [
        { chatName: 'Chat' },
        { chatName: 'Settings' }
      ],
      editingIndex: null,
      activeIndex: '1',
      editedItemName: '',
      userMessage: '',
      message: {},
      send: true,
      dialogVisible: false,
      promptVisible: true,
      chatId: '0'
    }
  },
  watch: {
    userMessage(newValue) {
      this.send = newValue.trim() === ''
    },
    chatId(newValue) {
      if (newValue === '0') {
        this.promptVisible = true
        this.dialogVisible = false
      }
    }
  },
  created() {
    getAllChats().then(response => {
      if (response.errno === 0) {
        this.sidebarItems = response.data.list
      }
    })
    getAllPlug().then(response => {
      if (response.errno === 0) {
        console.log(response.data)
        this.plugins = response.data.list
      }
    })
  },
  methods: {
    createMessages() {
      createMessage({ chatId: this.chatId, content: this.userMessage, type: 0 }).then(response => {
        if (response.errno === 1) {
          this.message = response.data
          this.loading = true
          if (this.selectedPlugin === '') {
            createAiMessage({ chatId: this.chatId, content: response.data.content, type: 0 }).then(response => {
              if (response.errno === 1) {
                this.message = response.data
              }
            }).finally(
              () => {
                this.loading = false// 在请求完成后隐藏加载动画
              }
            )
          } else {
            createImageMessage(this.message).then(response => {
              if (response.errno === 1) {
                this.message = response.data
              }
            }).finally(
              () => {
                this.loading = false// 在请求完成后隐藏加载动画
              }
            )
          }
        }
      })
    },
    async sendMessage() {
      if (this.userMessage.trim() !== '') {
        if (this.promptVisible === true) {
          this.promptVisible = false
          await this.addItem()
        }

        this.dialogVisible = true

        this.createMessages()
        this.userMessage = ''
      }
    },
    selectItem(id) {
      this.chatId = String(id)
      this.promptVisible = false
      this.dialogVisible = true
    },
    editItem(index) {
      this.editingIndex = index
      this.editedItemName = this.sidebarItems[index].chatName
    },
    deleteItem(index, id) {
      console.log('id' + id)
      deleteChat(this.chatId).then(response => {
        if (response.errno === 0) {
          this.sidebarItems.splice(index, 1)
          this.editingIndex = null
          this.chatId = '0'
        }
      })
    },
    addItem() {
      return new Promise((resolve, reject) => {
        createChat({ chatName: '新对话', userId: this.$store.state.user.id }).then(response => {
          if (response.errno === 1) {
            this.sidebarItems.unshift(response.data)
            this.promptVisible = false
            this.dialogVisible = true
            this.messages = {}
            this.chatId = String(response.data.id)
            return String(response.data.id)
          }
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    saveItem(index, id) {
      if (this.editedItemName.trim() !== '') {
        modifyChatName({
          id: id,
          userId: this.$store.state.user.userId,
          chatName: this.editedItemName
        }).then(response => {
          if (response.errno === 0) {
            this.sidebarItems[index].chatName = this.editedItemName
            console.log(this.sidebarItems[index].name)
            this.editingIndex = null
          }
        })
      }
    },
    truncateText(text) {
      const maxLength = 10 // 可以根据需要调整最大长度
      if (text.length > maxLength) {
        return text.substring(0, maxLength) + '...'
      }
      return text
    }

  }
}
</script>

<style scoped lang="scss">
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}

.chat-input {
  padding: 20px;
}
.button-group {
  float: right;
  margin-right: 20px;
}
.footer-info {
  text-align: center;
  margin-top: 10px;
  font-size: 12px;
}
</style>
