import markdownit from 'markdown-it'
import hljs from 'highlight.js'

export const md = markdownit({
  linkify: true,
  typographer: true,
  breaks: true,
  html: true,
  langPrefix: 'language-',
  // 代码高亮
  highlight(str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return (
          '<pre class="hljs"><code>' +
          hljs.highlight(lang, str, true).value +
          '</code></pre>'
        )
        // eslint-disable-next-line no-empty
      } catch (__) {}
    }
    return ''
  }
})

