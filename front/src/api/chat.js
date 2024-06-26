import request from '@/utils/request'

export function createChat(data) {
  return request({
    url: '/chat/chat',
    method: 'post',
    data
  })
}
export function deleteChat(chatId) {
  return request({
    url: '/chat/' + chatId,
    method: 'delete'
  })
}
export function modifyChatName(data) {
  return request({
    url: '/chat/chat',
    method: 'put',
    data
  })
}
export function getAllChats() {
  return request({
    url: '/chat/chats',
    method: 'get'
  })
}
export function createMessage(data) {
  return request({
    url: '/chat/message',
    method: 'post',
    data
  })
}
export function createAiMessage(data) {
  return request({
    url: '/chat/aimessage',
    method: 'post',
    data
  })
}
export function createImageMessage(data) {
  return request({
    url: '/chat/imagemessage',
    method: 'post',
    data
  })
}

export function getAllMessages(chatId, data) {
  return request({
    url: '/chat/messages/' + chatId,
    method: 'get',
    params: data
  })
}
