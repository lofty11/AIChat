<template>
  <el-dialog :visible="configFuncDialogVisible" title="结果渲染" @close="close">

    <el-form>
      <el-form-item>
        <el-row>
          <el-col>
            内容排布设定：
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="layoutSetting" class="form-item-content">
        <quill-editor ref="myQuillEditor" v-model="result" class="editor" :options="editorOption" />
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col span="10">

            <el-button type="primary" @click="confirm">提交</el-button>
          </el-col>
        </el-row>
      </el-form-item>

    </el-form>

  </el-dialog>
</template>
<script>
const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
  ['blockquote', 'code-block'], // 引用  代码块
  [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表
  [{ script: 'sub' }, { script: 'super' }], // 上标/下标
  [{ indent: '-1' }, { indent: '+1' }], // 缩进
  [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
  [{ font: [] }], // 字体种类
  [{ align: [] }], // 对齐方式
  ['clean'], // 清除文本格式
  ['link'] // 链接、图片
]
export default {
  props: {
    configFuncDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      funcInfo: {
        name: '',
        enName: '',
        type: '',
        api: '',
        description: ''
      },
      result: {
        layoutSetting: ''
      },
      editorOption: { // 编辑框操作事件
        theme: 'snow', // or 'bubble'
        placeholder: '请输入内容.',
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              link: function(value) { // 添加链接
                if (value) {
                  const href = prompt('请输入链接地址：')
                  this.quill.format('link', href)
                } else {
                  this.quill.format('link', false)
                }
              }
            }
          }
        }
      }
    }
  },
  methods: {
    close() {
      this.$emit('update:configFuncDialogVisible', false)
    },
    confirm() {
      this.close()
    }
  }
}
</script>
<style>
.editor {
  line-height: normal !important;
  height: 450px;
  margin-bottom: 30px;
}
.ql-container {
  height: 400px !important;
}
/** 设置富文本的高度 **/

/deep/ .ql-snow .ql-tooltip[data-mode=link]::before {
  content: '请输入链接地址：';
}
/deep/ .ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: '保存';
  padding-right: 0px;
}
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-label::before,
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: '14px';
}
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-label[data-value=small]::before,
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-item[data-value=small]::before {
  content: '10px';
}
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-label[data-value=large]::before,
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-item[data-value=large]::before {
  content: '18px';
}
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-label[data-value=huge]::before,
/deep/ .ql-snow .ql-picker.ql-size .ql-picker-item[data-value=huge]::before {
  content: '32px';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: '文本';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='1']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='1']::before {
  content: '标题1';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='2']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='2']::before {
  content: '标题2';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='3']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='3']::before {
  content: '标题3';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='4']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='4']::before {
  content: '标题4';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='5']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='5']::before {
  content: '标题5';
}
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-label[data-value='6']::before,
/deep/ .ql-snow .ql-picker.ql-header .ql-picker-item[data-value='6']::before {
  content: '标题6';
}
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-label::before,
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: '标准字体';
}
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-label[data-value=serif]::before,
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-item[data-value=serif]::before {
  content: '衬线字体';
}
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-label[data-value=monospace]::before,
/deep/ .ql-snow .ql-picker.ql-font .ql-picker-item[data-value=monospace]::before {
  content: '等宽字体';
}
</style>
