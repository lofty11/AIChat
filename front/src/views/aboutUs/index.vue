<template>
  <div class="aboutUs-container">
    <div class="aboutUs-text">
      关于我们
    </div>
    <div>

      <ul>
        <li>本项目旨在通过引入大语言模型的先进技术，构建一个更智能、更全面的问答系统。</li>
        <li>该系统将利用大语言模型的Function Call功能，循环调用各种API、函数及插件，以提供更丰富、更准确、更及时的答案和解决方案。</li>
        <li>通过这种方式，系统将更好地适应用户的需求，实现实时联网搜索，提升用户体验和系统的实用性。</li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'AboutUs',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.aboutUs {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
